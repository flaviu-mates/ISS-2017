package gui;

import domain.Paper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Udisteanu Elisei on 03/06/2017.
 */
public class ReviewerController implements Initializable {
    @FXML
    TableView<Paper> availablePapers;

    @FXML
    private TableView<Paper> chosenPapers;

    private ObservableList<Paper> availablePapersModel;
    private ObservableList<Paper> chosenPapersModel;

    @FXML
    private TableColumn namePaperColumnAvailable;
    @FXML
    private TableColumn topicPaperColumnAvailable;
    @FXML
    private TableColumn namePaperColumnChosen;
    @FXML
    private TableColumn topicPaperColumnChosen;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namePaperColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicPaperColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("topic"));
        namePaperColumnChosen.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicPaperColumnChosen.setCellValueFactory(new PropertyValueFactory<>("topic"));

        availablePapersModel = FXCollections.observableArrayList();
        availablePapers.setItems(availablePapersModel);

        chosenPapersModel = FXCollections.observableArrayList();
        chosenPapers.setItems(chosenPapersModel);
    }
}
