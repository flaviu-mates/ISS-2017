package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterController
{
    @FXML
    public ComboBox types;

    @FXML
    public void initialize()
    {
        types.getItems().removeAll();
        types.getItems().add(0, "Session Chair");
        types.getItems().add(1, "Speaker");
        types.getItems().add(2, "Listener");
    }

    public void open_login(ActionEvent event) throws Exception {
        Parent window3;
        window3 = (GridPane) FXMLLoader.load(getClass().getResource("../login.fxml"));

        Scene newScene;
        newScene = new Scene(window3);

        Stage mainWindow;
        mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();

        mainWindow.setScene(newScene);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Successful", ButtonType.OK);
        alert.setTitle("Success!");
        alert.setHeaderText("");
        alert.showAndWait();
    }
}