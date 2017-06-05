package gui;

import domain.Paper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Udisteanu Elisei on 03/06/2017.
 */
public class SessionAssignController implements Initializable {
    @FXML
    ComboBox<String> combo;
    @FXML
    private TableView<Paper> table;
    @FXML
    private TableColumn<Paper, String> paperNameColumn;
    @FXML
    private TableColumn<Paper, String> paperTopicColumn;


    @FXML
    private ObservableList<Paper> model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paperNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        paperTopicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));



        model = FXCollections.observableArrayList();
        table.setItems(model);
    }
}
