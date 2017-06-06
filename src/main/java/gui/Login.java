package gui;

import client.ClientImpl;
import common.IClientController;
import common.IGui;
import domain.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login implements IGui
{
    @FXML
    private AnchorPane root;

    public TextField textBoxUsername;
    public TextField textBoxPassword;

    private ClientImpl clientCtrl;

    public Login()
    {
    }

    public Login(ClientImpl clientCtrl)
    {
        this.clientCtrl = clientCtrl;
    }

    public void initialize()
    {
    }

    public void setClientCtrl(ClientImpl clientCtrl)
    {
        this.clientCtrl = clientCtrl;
    }

    protected void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    void renderView(String resourcePath, String title, User loggedUser) throws Exception
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Login.class.getResource(resourcePath));

            Pane pane = loader.load();

            IGui object = loader.getController();
            object.setCtrl(this.clientCtrl);

            Scene scene = new Scene(pane);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setOnCloseRequest(
                    event -> Platform.runLater(() -> this.clientCtrl.logout(this.clientCtrl.getLoggedUser()
                                                                                    .getUsername())));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }

    public void doLogin() throws Exception
    {
        String username = this.textBoxUsername.getText();
        String password = this.textBoxPassword.getText();

        try {
            User loggedUser = this.clientCtrl.login(username, password);
            switch (loggedUser.getTag()) {
                case "Admin":
                    renderView("../UserView.fxml", "Admin", loggedUser);
                    break;
                case "Author":
                    renderView("../author.fxml", "Author: " + loggedUser.getUsername(), loggedUser);
                    break;
                case "Reviewer":
                    renderView("../ReviewerView.fxml", "Reviewer: " + loggedUser.getUsername(), loggedUser);
                    break;
                case "Participant":
                    renderView("../participant.fxml", "Participant: " + loggedUser.getUsername(), loggedUser);
                    break;
                case "Session Chair":
                    renderView("../createView.fxml", "SessionChair:" + loggedUser.getUsername(), loggedUser);
                    break;
                default:
                    this.warning("Invalid user tag");
            }
        } catch (Exception e) {
            this.warning("Invalid user information");
        }
    }

    public void openRegister(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Login.class.getResource("../register.fxml"));

        Pane pane = loader.load();
        // inject the client controller
        Register register = loader.getController();
        register.setCtrl(this.clientCtrl);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }

    @Override
    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
    }
}