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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class CreateConference implements Initializable {
    @FXML
    public TextField confNameField;

    private User loggedUser;

    private ClientImpl clientCtrl;

    private Stage currentStage;

    public void setCtrl(ClientImpl clientCtrl)
    {
        this.clientCtrl = clientCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLoggedUser(User loggedUser)
    {
        this.loggedUser = loggedUser;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void onGotoEdition_clicked(ActionEvent actionEvent) throws IOException {
        switchToView("createEdition.fxml", "Create edition");
    }

    void switchToView(String fxmlPath, String title) {
        switchToView(fxmlPath, title, loggedUser);
    }

    void switchToView(String fxmlPath, String title, User currentUser) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.show();
            currentStage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void warning(String message){
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
            clientCtrl.logout(loggedUser.getUsername());
            switchToView("login.fxml", title, null);
        } catch (Exception ex) {
            warning("User not logged in!");
        }
    }

    public void onCreateConf_clicked(ActionEvent actionEvent) {
        try {
            String name = confNameField.getText();
            clientCtrl.addConference(name);
            warning("Conference " + name + " added.");
        } catch (Exception e) {
            warning(e.getMessage());
        }
    }

    @FXML
    public void backBtnHandler(){
        try{
            switchToView("create.fxml","Session chair"+loggedUser.getUsername());
        }catch(Exception ex){
            warning(ex.getMessage());
        }
    }
}
