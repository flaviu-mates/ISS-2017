package gui;

import client.ClientImpl;
import common.IGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Create implements Initializable, IGui
{
    @FXML
    private BorderPane root;
    @FXML
    private Button createConf;
    @FXML
    private Button createEdition;
    @FXML
    private Button createSession;

    private ClientImpl clientCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    private void switchToView(String resourcePath)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(resourcePath));

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
    void switchToViewConf(ActionEvent event) throws Exception
    {
        switchToView("createConf.fxml");
    }

    @FXML
    void switchToViewEdit(ActionEvent event)
    {
        switchToView("createEdition.fxml");
    }

    @FXML
    void switchToViewSession(ActionEvent event) throws Exception
    {
        switchToView("createSession.fxml");
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
            switchToView("login.fxml");
        } catch (Exception ignored) {
        }
    }
}