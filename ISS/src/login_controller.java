import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by ciprian on 4/4/2017.
 */
public class login_controller {

    public TextField textField_username;
    public TextField textField_password;

    public void initalize(){

    }


    public void open_register(ActionEvent event) throws Exception {



        Parent window3;
        window3 = (GridPane)FXMLLoader.load(getClass().getResource("register.fxml"));

        Scene newScene;
        newScene = new Scene(window3);

        Stage mainWindow;
        mainWindow = (Stage)  ((Node)event.getSource()).getScene().getWindow();

        mainWindow.setScene(newScene);

    }
}
