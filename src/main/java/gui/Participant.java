package gui;

import client.ClientImpl;
import common.IGui;
import domain.Edition;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class Participant implements IGui
{
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

    @FXML
    private BorderPane root;

    public void initialize(URL location, ResourceBundle resources) {
        editionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        endingDateColumn.setCellValueFactory(new PropertyValueFactory<>("begin"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));


        editions = FXCollections.observableArrayList();
        editions.clear();
        editionTable.setItems(editions);
    }

    public void setCtrl(ClientImpl ctrl) {
        this.clientCtrl = ctrl;
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
