package gui;

import client.ClientImpl;
import common.IGui;
import domain.Edition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateSession implements Initializable, IGui
{
    @FXML
    private BorderPane root;
    @FXML
    public DatePicker date;
    @FXML
    public TextField sessionNameField;
    @FXML
    public TableColumn<Edition, String> editionNameColumn;
    @FXML
    public TableView<Edition> editionsTable;

    private ClientImpl clientCtrl;

    private ObservableList<Edition> editions;

    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
        this.initializeEditionsTable();
    }

    private void initializeEditionsTable()
    {
        try {
            this.editions = FXCollections.observableArrayList(this.clientCtrl.getAllEdition());
            this.editionsTable.setItems(this.editions);
        } catch (RemoteException exc) {
            this.warning("Cannot initialize!");
        }
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        this.editionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.editions = FXCollections.observableArrayList();
        this.editionsTable.setItems(this.editions);
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
    public void onCreateBtn_clicked(ActionEvent actionEvent)
    {
        try {
            Edition selectedEdition = this.editionsTable.getSelectionModel().getSelectedItem();
            if (selectedEdition == null) {
                warning("No edition selected!");
                return;
            }

            String name = this.sessionNameField.getText();
            if (Objects.equals(name, "")) {
                warning("No name entered!");
                return;
            }

            LocalDate date = this.date.getValue();
            if (date == null) {
                this.warning("No date entered!");
                return;
            }

            this.clientCtrl.addSession(date, name, selectedEdition);
            this.success("Edition " + name + " for conference " + selectedEdition.getName() + " was created.");
        } catch (Exception e) {
            this.warning(e.getMessage());
        }
    }

    void switchToView(String fxmlPath, String title)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));

            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(this.clientCtrl);

            stage.show();
        } catch (IOException e) {
            this.warning("Cannot redirect");
        }
    }

    @FXML
    public void backBtnHandler()
    {
        try {
            this.switchToView("create.fxml", "Session chair: " + this.clientCtrl.getLoggedUser().getUsername());
        } catch (Exception ex) {
            this.warning(ex.getMessage());
        }
    }
}