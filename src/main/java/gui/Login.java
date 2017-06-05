package gui;

import client.ClientImpl;
import domain.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login
{
    public TextField textBoxUsername;
    public TextField textBoxPassword;

    private ClientImpl clientCtrl;

    public Login() {}

    public Login(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    public void initialize() {}

    public void setClientCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    protected void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void doLogin() {
        String username = this.textBoxUsername.getText();
        String password = this.textBoxPassword.getText();

        try {
            this.clientCtrl.login(username, password);
        } catch (Exception e) {
            // TODO be more specific
            this.warning("Invalid user information");
        }
    }

    public void openRegister(ActionEvent event) throws Exception {
        // TODO what's here?
        Parent registerWindow = (GridPane) FXMLLoader.load(getClass().getResource("../register.fxml"));
        Scene newScene = new Scene(registerWindow);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Login.class.getResource("../register.fxml"));

        Pane pane = loader.load();
        // inject the client controller
        Register register = loader.getController();
        register.setCtrl(this.clientCtrl);

        Scene scene = new Scene(pane);
        // TODO logout
//        stage.setOnCloseRequest(event2 -> Platform.runLater(() -> this.clientCtrl.logout(""/* TODO give username */)));
        stage.setScene(scene);
        stage.show();
    }
}