package GUI;

import client.ClientImpl;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import org.hibernate.service.spi.ServiceException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class AuthorView implements Initializable {
    @FXML
    private TextField titleText;
    @FXML
    private Button uploadBtn;
    @FXML
    private TableView<Session> table;
    @FXML
    private TableColumn<Session, java.util.Date> dateTableColumn;
    @FXML
    private TableColumn<Session, String> locationTableColumn;
    @FXML
    private TableColumn<Session, Edition> editionTableColumn;
    @FXML
    private ObservableList<Session> model;
    @FXML
    private ComboBox<String> topicComboBox;
    @FXML
    private Button logOutBtn;

    private ClientImpl clientCtrl;

    private User loggedUser;

    public void setCtrl(ClientImpl clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    public void initialize(URL location, ResourceBundle resources) {
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        editionTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        model = FXCollections.observableArrayList();
        table.setItems(model);

        topicComboBox.getItems().add("IT");
        topicComboBox.getItems().add("MATH");
        topicComboBox.getItems().add("CHEMISTRY");
        topicComboBox.getItems().add("BIOLOGY");
        topicComboBox.getItems().add("LITERATURE");
        topicComboBox.getItems().add("HISTORY");
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    private void warning(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    void submitPaper() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            warning("There is no selected Edition");
            return;
        }
        if (topicComboBox.getSelectionModel().getSelectedItem() == null) {
            warning("No topic selected");
            return;
        }

        if (titleText.getText() == null) {
            warning("No title selected");
            return;
        }

        try {
            String title = titleText.getText();
            String topic = topicComboBox.getSelectionModel().getSelectedItem();
            Session session = table.getSelectionModel().getSelectedItem();
            //User author = clientCtrl.getUserById(loggedUser.getId());
            for (Paper p : clientCtrl.getAllPapers()) {
                if (p.getTitle().equals(title) && p.getUser().getUsername().equals(loggedUser.getUsername())) {
                    //System.out.println("intra la update paper");
                    Paper paper = new Paper(PaperStatus.InReview, title, topic, session, loggedUser);
                    p.setTopic(topic);
                    clientCtrl.updatePaper(p);
                    warning("Paper Succesfully updated");
                    return;
                }
            }

            Paper paper = new Paper(PaperStatus.InReview, title, topic, session, loggedUser);
            clientCtrl.addPaper(paper);
            warning("Paper Succesfully added");
        } catch (ServiceException exception) {
            warning("Paper could not be added");
        }
        catch (Exception e) {
            warning(e.getMessage());
        }

    }
}

