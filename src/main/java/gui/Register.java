package gui;

import client.ClientImpl;
import common.IGui;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class Register implements IGui
{
    @FXML
    private AnchorPane root;

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

    public Register() {}

    public Register(ClientImpl clientCtrl) {}

    public void setCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    @FXML
    public void initialize()
    {
        this.comboBoxUserTypes.getItems().removeAll();
        this.comboBoxUserTypes.getItems().add(0, "Author");
        this.comboBoxUserTypes.getItems().add(1, "Participant");
        this.comboBoxUserTypes.getItems().add(2, "Reviewer");
        this.comboBoxUserTypes.getItems().add(3, "Session Chair");
    }

    protected void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    public void registerUser(ActionEvent event) throws Exception {

        try {
            String username = this.textBoxUsername.getText();
            String password = this.textBoxPassword.getText();
            String email = this.textBoxEmail.getText();
            String firstname = this.textBoxFirstName.getText();
            String lastname = this.textBoxLastName.getText();
            String userType = this.comboBoxUserTypes.getSelectionModel().getSelectedItem().toString();

            if (Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(email, "") || Objects
                    .equals(firstname, "") || Objects.equals(lastname, "") || Objects.equals(userType, "")) {
                throw new Exception();
            }
            User user = new User(username, password, email, firstname, lastname, userType);
            this.clientCtrl.addUser(user);
            this.redirectToLogin();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful", ButtonType.OK);
            alert.setTitle("Success");
            alert.setHeaderText("You can now login!");
            alert.showAndWait();
        } catch (Exception e) {
            this.warning("Please complete every field!");
        }
    }

    public void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Login.class.getResource("../login.fxml"));

            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            Login login = loader.getController();
            login.setClientCtrl(this.clientCtrl);

            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }
}