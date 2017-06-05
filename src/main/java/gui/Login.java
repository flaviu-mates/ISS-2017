package gui;

import client.ClientImpl;
import domain.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Login
{
    @FXML
    private AnchorPane root;

    public TextField textBoxUsername;
    public TextField textBoxPassword;

    private ClientImpl clientCtrl;

    public Login() {}

    public Login(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    public void initialize() {}

    public void setClientCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    protected void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    void renderView(String resourcePath, String title, User loggedUser)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(resourcePath));

        try {
            Stage stage = (Stage) this.root.getScene().getWindow();
            Parent registerWindow = (BorderPane) FXMLLoader.load(getClass().getResource(resourcePath));
            Scene newScene = new Scene(registerWindow);

            Pane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setOnCloseRequest(
                    event2 -> Platform.runLater(() -> this.clientCtrl.logout(this.clientCtrl.getLoggedUser()
                                                                                     .getUsername())));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            this.warning("Cannot redirect!");
        }
    }

    public void doLogin() {
        String username = this.textBoxUsername.getText();
        String password = this.textBoxPassword.getText();

        try {
            User loggedUser = this.clientCtrl.login(username, password);
            // TODO Redirect
//            switch (loggedUser.getTag()) {
//                case "Admin":
//                    renderView("UserView.fxml", "Admin", loggedUser);
//                    break;
//                case "Author":
//                    String title = "Author: " + loggedUser.getUsername();
//                    renderView("author.fxml", title, loggedUser);
//                    break;
//                case "Reviewer":
//                    renderView("reviewer_main.fxml", "Reviewer: " + loggedUser.getUsername(), loggedUser);
//                    break;
//                case "Participant":
//                    renderView("../participant.fxml", "Participant: " + loggedUser.getUsername(), loggedUser);
//                    break;
//                case "SessionChair":
//                    String chairTitle = "Session chair:" + loggedUser.getUsername();
//                    renderView("create.fxml", chairTitle, loggedUser);
//                    break;
//                default:
//                    this.warning("Invalid user tag");
//            }
        } catch (Exception e) {
            this.warning("Invalid user information");
            return;
        }
    }

    public void openRegister(ActionEvent event) throws Exception {
        Parent registerWindow = (GridPane) FXMLLoader.load(getClass().getResource("../register.fxml"));
        Scene newScene = new Scene(registerWindow);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Login.class.getResource("../register.fxml"));

        Pane pane = loader.load();
        // inject the client controller
        Register register = loader.getController();
        register.setCtrl(this.clientCtrl);

        Scene scene = new Scene(pane);
        stage.setOnCloseRequest(event2 -> Platform.runLater(() -> this.clientCtrl.logout(this.clientCtrl.getLoggedUser().getUsername())));
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }
}