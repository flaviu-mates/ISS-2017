package GUI;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class MyPaperView {
    @FXML
    private TableView<Paper> table;
    @FXML
    private TableColumn<Paper, User> userColumn;
    @FXML
    private TableColumn<Paper, String> paperNameColumn;
    @FXML
    private TableColumn<Paper, String> topicColumn;
    @FXML
    private TableColumn<Paper, PaperStatus> statusColumn;
    @FXML
    private TableColumn<Paper, Session> sessionColumn;
    @FXML
    private ObservableList<Paper> model;

    public void initialize(URL location, ResourceBundle resources) {
        paperNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        sessionColumn.setCellValueFactory(new PropertyValueFactory<Paper, Session>("session"));
        model = FXCollections.observableArrayList();
        table.setItems(model);
    }

}
