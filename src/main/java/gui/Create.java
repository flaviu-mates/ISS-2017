package gui;

import client.ClientImpl;
import common.IGui;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class Create implements Initializable, IGui
{
    @FXML
    private BorderPane root;

    //@FXML
    //private BorderPane root2;

    @FXML
    private Button createConf;
    @FXML
    private Button createEdition;

    private ClientImpl clientCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void warning(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    void switchToViewConf(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("../createConf.fxml"));
        try {
            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(clientCtrl);

            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }

    @FXML
    void switchToViewEdit(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("../createEdition.fxml"));
        try {
            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(clientCtrl);

            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }

    @Override
    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
    }

    @FXML
    public void logOutHandler() throws Exception
    {
        try {
            String title = "Conference Management System";
            clientCtrl.logout(clientCtrl.getLoggedUser().getUsername());
            switchToView("login.fxml", title, null);
        } catch (Exception ex) {
        }
    }

    void switchToView(String fxmlPath, String title)
    {
        switchToView(fxmlPath, title, clientCtrl.getLoggedUser());
    }

    void switchToView(String fxmlPath, String title, User currentUser)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(clientCtrl);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
