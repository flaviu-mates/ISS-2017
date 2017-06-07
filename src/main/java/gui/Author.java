package gui;

import client.ClientImpl;
import common.IGui;
import domain.*;
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
import org.hibernate.service.spi.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Author implements Initializable, IGui
{
    @FXML
    private BorderPane root;
    @FXML
    private TextField titleText;
    @FXML
    private Button uploadBtn;
    @FXML
    private TableView<Session> sessionTable;
    @FXML
    private TableColumn<Session, String> sessionNameColumn;
    @FXML
    private ObservableList<Session> sessions;
    @FXML
    private ComboBox<String> topicComboBox;
    @FXML
    private Button logOutBtn;

    private ClientImpl clientCtrl;

    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
        this.initializeSessionsTable();
    }

    private void initializeSessionsTable()
    {
        try {
            this.sessions = FXCollections.observableArrayList(this.clientCtrl.getAllSessions());
            this.sessionTable.setItems(this.sessions);
        } catch (RemoteException exc) {
            this.warning("Cannot initialize!");
        }
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        this.sessionNameColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.sessions = FXCollections.observableArrayList();
        this.sessionTable.setItems(sessions);

        this.topicComboBox.getItems().add("IT");
        this.topicComboBox.getItems().add("Math");
        this.topicComboBox.getItems().add("Chemistry");
        this.topicComboBox.getItems().add("Biology");
        this.topicComboBox.getItems().add("Literature");
        this.topicComboBox.getItems().add("History");
    }

    private void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    private void success(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    void submitPaper()
    {
        Session session = this.sessionTable.getSelectionModel().getSelectedItem();
        if (session == null) {
            this.warning("No Session selected!");
            return;
        }

        String topic = this.topicComboBox.getSelectionModel().getSelectedItem();
        if (topic == null || Objects.equals(topic, "")) {
            this.warning("No topic selected!");
            return;
        }

        String title = this.titleText.getText();
        if (Objects.equals(title, "")) {
            this.warning("No title entered!");
            return;
        }

        try {
            User author = this.clientCtrl.getUserById(this.clientCtrl.getLoggedUser().getId());
            for (Paper p : this.clientCtrl.getAllPapers()) {
                if (p.getTitle().equals(title) && p.getUser().getUsername().equals(this.clientCtrl.getLoggedUser().getUsername())) {
                    Paper paper = new Paper(PaperStatus.InReview, title, topic, session, this.clientCtrl.getLoggedUser());
                    p.setTopic(topic);
                    this.clientCtrl.updatePaper(p);
                    this.success("Paper successfully updated!");
                    return;
                }
            }

            Paper paper = new Paper(PaperStatus.InReview, title, topic, session, clientCtrl.getLoggedUser());
            this.clientCtrl.addPaper(paper);
            this.success("Paper successfully added!");
        } catch (ServiceException exception) {
            this.success("Paper could not be added!");
        } catch (Exception e) {
            this.warning("Cannot submit paper!");
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
}