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
import java.util.Date;
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
    private TableView<Session> table;
    @FXML
    private TableColumn<Session, Integer> idColumn;
    @FXML
    private TableColumn<Session, Date> dateColumn;
    @FXML
    private TableColumn<Session, String> locationColumn;
    @FXML
    private TableColumn<Session, Edition> editionColumn;
    @FXML
    private ObservableList<Session> model;
    @FXML
    private ComboBox<String> topicComboBox;
    @FXML
    private Button logOutBtn;

    private ClientImpl clientCtrl;

    public void setCtrl(ClientImpl ctrl)
    {
        this.clientCtrl = ctrl;
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.editionColumn.setCellValueFactory(new PropertyValueFactory<>("edition"));
        this.model = FXCollections.observableArrayList();
        this.table.setItems(model);

        this.topicComboBox.getItems().add("IT");
        this.topicComboBox.getItems().add("MATH");
        this.topicComboBox.getItems().add("CHEMISTRY");
        this.topicComboBox.getItems().add("BIOLOGY");
        this.topicComboBox.getItems().add("LITERATURE");
        this.topicComboBox.getItems().add("HISTORY");
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
        if (this.table.getSelectionModel().getSelectedItem() == null) {
            this.warning("No selected Edition!");
            return;
        }
        if (this.topicComboBox.getSelectionModel().getSelectedItem() == null) {
            this.warning("No topic selected!");
            return;
        }

        if (this.titleText.getText() == null) {
            this.warning("No title selected!");
            return;
        }

        try {
            String title = this.titleText.getText();
            String topic = this.topicComboBox.getSelectionModel().getSelectedItem();
            Session session = this.table.getSelectionModel().getSelectedItem();
            User author = this.clientCtrl.getUserById(this.clientCtrl.getLoggedUser().getId());
            for (Paper p : this.clientCtrl.getAllPapers()) {
                if (p.getTitle().equals(title) && p.getUser().getUsername().equals(this.clientCtrl.getLoggedUser()
                                                                                           .getUsername())) {
                    Paper paper = new Paper(PaperStatus.InReview, title, topic, session, this.clientCtrl
                            .getLoggedUser());
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
