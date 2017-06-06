package gui;

import client.ClientImpl;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by Udisteanu Elisei on 03/06/2017.
 */
public class UserController implements Initializable {
    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> tagColumn;

    @FXML
    private ComboBox<String> tagComboBox;
    @FXML
    private Button updateButton;

    @FXML
    private ObservableList<User> model;

    private ClientImpl clientCtrl;

    public void setCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        tagComboBox.getItems().add("Admin");
        tagComboBox.getItems().add("Reviewer");
        tagComboBox.getItems().add("Author");
        tagComboBox.getItems().add("Session Chair");
        tagComboBox.getItems().add("Participant");
        model = FXCollections.observableArrayList();
        table.setItems(model);
    }

    private void warning(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    private void update(){
        model.clear();
        try {
            model.setAll(clientCtrl.getAllUsers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateButtonHandler(){
        boolean userSelected = table.getSelectionModel().getSelectedItem() != null;
        boolean tagSelected = tagComboBox.getSelectionModel().getSelectedItem() != null;
        if(userSelected && tagSelected){
            User user = table.getSelectionModel().getSelectedItem();
            try {

                user.setTag(tagComboBox.getSelectionModel().getSelectedItem());
                clientCtrl.updateUser(user);
                update();
            }
            catch (Exception exception) {
                warning(exception.getMessage());
            }
        } else {
            warning("There is no User or no Tag selected");
        }
    }

    public void logoutButtonHandler(){
        //
    }
}
