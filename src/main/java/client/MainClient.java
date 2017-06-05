package client;

import common.IServerController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient extends Application  {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            String name = "Triathlon";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IServerController server = (IServerController) registry.lookup(name);
            System.out.println("Successfully obtained a reference to remote server");
            ClientImpl ctrl = new ClientImpl(server);

//            Login logWin = new Login("CMS", ctrl);
//            logWin.setSize(200, 200);
//            logWin.setLocation(600, 250);
//            logWin.setVisible(true);
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}