package client;

import common.IServerController;
import gui.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.ServerImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient extends Application  {
    @Override
    public void start(Stage stage) throws Exception {
//        try {
            String name = "CMS";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IServerController server = (IServerController) registry.lookup(name);
            System.out.println("Successfully obtained a reference to remote server");
            ClientImpl clientCtrl = new ClientImpl(server);

            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClient.class.getResource("../login.fxml"));

            Pane pane = loader.load();
            // inject the client controller
            Login loginController = loader.getController();
            loginController.setClientCtrl(clientCtrl);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
//        } catch (Exception e) {
//            System.err.println("Client exception: " + e);
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}