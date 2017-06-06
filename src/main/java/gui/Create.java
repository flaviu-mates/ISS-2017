package gui;

import client.ClientImpl;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
public class Create implements Initializable
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

    public void setCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCreateConf_clicked(ActionEvent actionEvent) {
        switchToViewConf("createConf.fxml", "Create conference");
    }

    public void onCreateEdition_clicked(ActionEvent actionEvent) {
        switchToViewEdit("createEdition.fxml", "Create edition");
    }

    void switchToView(String fxmlPath, String title) {
        switchToView(fxmlPath, title);
    }

    private void warning(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    void switchToViewConf(String fxmlPath, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            CreateConference createConference = loader.getController();
            createConference.setCtrl(this.clientCtrl);

            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }

    void switchToViewEdit(String fxmlPath, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            CreateEdition createEdition = loader.getController();
            createEdition.setCtrl(this.clientCtrl);

            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }


}
