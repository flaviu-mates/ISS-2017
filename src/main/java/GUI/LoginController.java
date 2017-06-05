package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController
{
    public TextField textField_username;
    public TextField textField_password;

    public void initialize() {}

    public void open_register(ActionEvent event) throws Exception {
        Parent window3;
        window3 = (GridPane)FXMLLoader.load(getClass().getResource("../register.fxml"));

        Scene newScene;
        newScene = new Scene(window3);

        Stage mainWindow;
        mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();

        mainWindow.setScene(newScene);
    }
}