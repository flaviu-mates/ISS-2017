package gui;

import client.ClientImpl;
import common.IGui;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class Admin implements Initializable, IGui
{
    @FXML
    private BorderPane root;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> tagColumn;
    @FXML
    private ObservableList<User> model;

    private ClientImpl clientCtrl;

    public void setCtrl(ClientImpl clientCtrl)
    {
        this.clientCtrl = clientCtrl;
        this.initializeUserTable();
    }

    private void initializeUserTable() {
        try {
            this.model = FXCollections.observableArrayList(this.clientCtrl.getAllUsers());
            this.table.setItems(this.model);
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        this.model = FXCollections.observableArrayList();
        this.table.setItems(this.model);
    }

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    private void update()
    {
        try {
            this.model.clear();
            this.model.setAll(this.clientCtrl.getAllUsers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void switchToView(String fxmlPath, String title)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(fxmlPath));

            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);

            // inject client controller
            IGui object = loader.getController();
            object.setCtrl(this.clientCtrl);

            stage.show();
        } catch (IOException e) {
            this.warning("Cannot redirect!");
        }
    }

    @FXML
    public void logOutHandler() throws Exception
    {
        try {
            String title = "Conference Management System";
            this.clientCtrl.logout(this.clientCtrl.getLoggedUser().getUsername());
            this.switchToView("login.fxml", title);
        } catch (Exception ex) {
            this.warning("Cannot logout!");
        }
    }
}