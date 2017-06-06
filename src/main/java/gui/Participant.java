package gui;

import client.ClientImpl;
import common.IGui;
import domain.Edition;
import domain.Registration;
import domain.User;
import domain.UserEdition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class Participant implements IGui
{
    @FXML
    private BorderPane root;
    @FXML
    private TableColumn<Edition, Date> endingDateColumn;
    @FXML
    private TableColumn<Edition, Date> beginningDateColumn;
    @FXML
    private TableColumn<Edition, String> editionNameColumn;
    @FXML
    private ObservableList<Edition> editions;
    @FXML
    private TableView<Edition> editionTable;

    private ClientImpl clientCtrl;

    public Participant() {}

    public void initialize() {
        editionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        endingDateColumn.setCellValueFactory(new PropertyValueFactory<>("begin"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

        editions = FXCollections.observableArrayList();
        editions.clear();
    }

    private void initializeEditions() {
        try {
            editions = FXCollections.observableArrayList(clientCtrl.getAllEdition());
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
        editionTable.setItems(editions);
    }

    public void setCtrl(ClientImpl ctrl) {
        this.clientCtrl = ctrl;
        initializeEditions();
    }

    @FXML
    public void logOutHandler() throws Exception
    {
        try {
            String title = "Conference Management System";
            clientCtrl.logout(clientCtrl.getLoggedUser().getUsername());
            switchToView("login.fxml", title, null);
        } catch (Exception ex) {
        }
    }

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    private void success(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    public void onParticipate(ActionEvent actionEvent) {
        try {
            Edition edition = this.editionTable.getSelectionModel().getSelectedItem();
            Registration registration = new Registration();

            UserEdition userEdition = new UserEdition();
            userEdition.setUser(this.clientCtrl.getLoggedUser());
            userEdition.setEdition(edition);

            registration.setUserEdition(userEdition);
            registration.setAcquitted(false);

            this.clientCtrl.addRegistration(registration);
            this.success("Registration for user: " + this.clientCtrl.getLoggedUser().getUsername() +
                    "to edition: " + edition.getName() +  " added.");
        } catch (Exception e) {
            this.warning(e.getMessage());
        }
    }

    void switchToView(String fxmlPath, String title)
    {
        switchToView(fxmlPath, title, clientCtrl.getLoggedUser());
    }

    void switchToView(String fxmlPath, String title, User currentUser)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(clientCtrl);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
