package gui;

import domain.Paper;
import domain.ReviewStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Udisteanu Elisei on 03/06/2017.
 */
public class ReviewerAssignment implements Initializable{
    public ObservableList<Paper> model;
    @FXML
    private ComboBox<String> reviewCombo;
    @FXML
    private TableView<Paper> tabel;
    @FXML
    private TableColumn namePaperColumn;
    @FXML
    private TableColumn topicPaperColumn;
    @FXML
    private TextArea commentText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (ReviewStatus status : ReviewStatus.values()) {
                reviewCombo.getItems().add(status.toString());
        }
        namePaperColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicPaperColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));
        model = FXCollections.observableArrayList();
        tabel.setItems(model);
    }
}
