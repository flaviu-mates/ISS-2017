package gui;

import client.ClientImpl;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Register
{
    @FXML
    TextField textBoxUsername;
    @FXML
    TextField textBoxPassword;
    @FXML
    TextField textBoxFirstName;
    @FXML
    TextField textBoxLastName;
    @FXML
    TextField textBoxEmail;

    @FXML
    ComboBox comboBoxUserTypes;
    @FXML
    Button buttonRegister;

    private ClientImpl clientCtrl;

    public void Register(ClientImpl clientCtrl) {}

    public void setCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    @FXML
    public void initialize()
    {
        this.comboBoxUserTypes.getItems().removeAll();
        this.comboBoxUserTypes.getItems().add(0, "Session Chair");
        this.comboBoxUserTypes.getItems().add(1, "Speaker");
        this.comboBoxUserTypes.getItems().add(2, "Listener");
    }

    public void doRegister() {
        String username = this.textBoxUsername.getText();
        String password = this.textBoxPassword.getText();
        String email = this.textBoxEmail.getText();
        String firstname = this.textBoxFirstName.getText();
        String lastname = this.textBoxLastName.getText();
        String userType = this.comboBoxUserTypes.getSelectionModel().selectedItemProperty().toString();

        User user = new User(username, password, email, firstname, lastname, userType);
        // TODO insert new username
    }

    private void openLogin(ActionEvent event) throws Exception {
        Parent window3 = (GridPane) FXMLLoader.load(getClass().getResource("../login.fxml"));
        Scene newScene = new Scene(window3);
        Stage mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(newScene);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful", ButtonType.OK);
        alert.setTitle("You can now login!");
        alert.setHeaderText("Success");
        alert.showAndWait();
    }
}