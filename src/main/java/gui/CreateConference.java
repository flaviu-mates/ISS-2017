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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateConference implements Initializable, IGui
{
    @FXML
    public TextField confNameField;
    @FXML
    private BorderPane root;

    private ClientImpl clientCtrl;

    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public void onGotoEdition_clicked(ActionEvent actionEvent) throws IOException
    {
        switchToView("createEdition.fxml", "Create edition");
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

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    public void logOutHandler() throws Exception
    {
        try {
            String title = "Conference Management System";
            clientCtrl.logout(this.clientCtrl.getLoggedUser().getUsername());
            switchToView("login.fxml", title, null);
        } catch (Exception ex) {
            warning("User not logged in!");
        }
    }

    public void onCreateConf_clicked(ActionEvent actionEvent)
    {
        try {
            String name = confNameField.getText();
            clientCtrl.addConference(name);
            warning("Conference " + name + " added.");
        } catch (Exception e) {
            warning(e.getMessage());
        }
    }

    @FXML
    public void backBtnHandler()
    {
        try {
            switchToView("create.fxml", "Session chair: " + this.clientCtrl.getLoggedUser().getUsername());
        } catch (Exception ex) {
            warning(ex.getMessage());
        }
    }
}
