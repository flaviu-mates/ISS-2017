package gui;

import domain.Edition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class Author
{
    @FXML
    private TextField titleText;
    @FXML
    private Button uploadBtn;
    @FXML
    private TableView<Edition> table;
    @FXML
    private TableColumn<Edition, String> nameColumn;
    @FXML
    private TableColumn<Edition, java.util.Date> submissionDeadlineColumn;
    @FXML
    private TableColumn<Edition, java.util.Date> beginingDateColumn;
    @FXML
    private TableColumn<Edition, java.util.Date> endingDateColumn;
    @FXML
    private ObservableList<Edition> model;
    @FXML
    private ComboBox<String> topicComboBox;
    @FXML
    private Button logOutBtn;

    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        submissionDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("paperDeadline"));
        beginingDateColumn.setCellValueFactory(new PropertyValueFactory<>("begin"));
        endingDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        model = FXCollections.observableArrayList();
        table.setItems(model);
    }
}
