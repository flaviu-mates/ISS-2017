import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by ciprian on 4/4/2017.
 */
public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml=new FXMLLoader();
        fxml.setLocation(main.class.getResource("login.fxml"));

        Stage stage = new Stage();
        GridPane pane=fxml.load();

        stage.setScene(new Scene(pane));



        stage.show();

    }
}
