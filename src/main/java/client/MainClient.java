package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MainClient extends Application  {
    static SessionFactory sessionFactory;

    static void initialize()
    {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        initialize();

        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(MainClient.class.getResource("../login.fxml"));

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