package gui;

import client.ClientImpl;
import common.IGui;
import domain.Conference;
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

public class CreateEdition implements Initializable, IGui
{
    @FXML
    private BorderPane root;
    @FXML
    public DatePicker submissionDeadlineField;
    @FXML
    public DatePicker finalDeadlineField;
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public TextField editionNameField;
    @FXML
    public TableColumn<Conference, String> confNameColumn;
    @FXML
    public TableView<Conference> conferencesTable;

    private ClientImpl clientCtrl;

    private ObservableList<Conference> conferences;

    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
        this.initializeConferencesTable();
    }

    private void initializeConferencesTable()
    {
        try {
            this.conferences = FXCollections.observableArrayList(this.clientCtrl.getAllConferences());
            this.conferencesTable.setItems(this.conferences);
        } catch (RemoteException exc) {
            this.warning("Cannot initialize!");
        }
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        this.confNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.conferences = FXCollections.observableArrayList();
        this.conferencesTable.setItems(this.conferences);
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
            Conference selectedConference = this.conferencesTable.getSelectionModel().getSelectedItem();
            if (selectedConference == null) {
                warning("No conference selected!");
                return;
            }

            String name = this.editionNameField.getText();
            if (Objects.equals(name, "")) {
                warning("No name entered!");
                return;
            }

            LocalDate startDate = this.startDateField.getValue();
            LocalDate endDate = this.endDateField.getValue();
            LocalDate submissionDeadline = this.submissionDeadlineField.getValue();
            LocalDate finalDeadline = this.finalDeadlineField.getValue();

            if (startDate == null || endDate == null || submissionDeadline == null || finalDeadline == null) {
                this.warning("No date entered!");
                return;
            }

            this.clientCtrl.addEdition(selectedConference, startDate, endDate, name, submissionDeadline, finalDeadline);
            this.success("Edition " + name + " for conference " + selectedConference.getName() + " was created.");
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