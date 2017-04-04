import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 4/4/2017.
 */
public class register_controller {



    public void open_login(ActionEvent event) throws Exception{



        Parent window3;
        window3 = (GridPane) FXMLLoader.load(getClass().getResource("login.fxml"));

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
    @FXML
    public ComboBox myCombobox;

    @FXML
    public void initialize() {
        myCombobox.getItems().removeAll();
        myCombobox.getItems().add(0, "Session Chair");
        myCombobox.getItems().add(1, "Speaker");
        myCombobox.getItems().add(2, "Listener");
    }
}
