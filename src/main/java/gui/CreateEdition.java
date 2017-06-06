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
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateEdition implements Initializable, IGui
{
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
        initializeConferencesTable();
    }

    private void initializeConferencesTable() {
        try {
            conferences = FXCollections.observableArrayList(clientCtrl.getAllConferences());
            conferencesTable.setItems(conferences);
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        confNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        conferences = FXCollections.observableArrayList();
        conferencesTable.setItems(conferences);
    }

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    public void onCreateBtn_clicked(ActionEvent actionEvent)
    {
        try {
            Conference selectedConference = conferencesTable.getSelectionModel().getSelectedItem();
            String name = editionNameField.getText().toString();
            if (name == null) {
                warning("No name selected");
                return;
            }
            LocalDate startDate = startDateField.getValue();
            LocalDate endDate = endDateField.getValue();
            LocalDate submissionDeadline = submissionDeadlineField.getValue();
            LocalDate finalDeadline = finalDeadlineField.getValue();

            if (startDate == null || endDate == null || submissionDeadline == null || finalDeadline == null) {
                warning("No enter date");
                return;
            }

            clientCtrl.addEdition(selectedConference, startDate, endDate, name, submissionDeadline, finalDeadline);
            warning("Edition %s for conference %s, was created.");
        } catch (Exception e) {
            warning(e.getMessage());
        }
    }

    @FXML
    public void onBackBtn_clicked(ActionEvent actionEvent)
    {
        switchToView("createView.fxml", "Session chair");
    }


    void switchToView(String fxmlPath, String title)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
