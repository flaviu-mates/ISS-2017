/**
 * Created by Andrei Bodea on 10.05.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by ciprian on 4/4/2017.
 */
public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(Main.class.getResource("login.fxml"));

        Stage stage = new Stage();
        GridPane pane = fxml.load();

        stage.setScene(new Scene(pane));

        stage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

