package gui;

import domain.Edition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by ciprian on 6/3/2017.
 */
public class Participant
{
    @FXML
    private TableColumn<Edition, Date> endingDateColumn;
    @FXML
    private TableColumn<Edition, Date> beginningDateColumn;
    @FXML
    private TableColumn<Edition, String> editionNameColumn;
    @FXML
    private ObservableList<Edition> editions;
    @FXML
    private TableView<Edition> editionTable;

    public void initialize(URL location, ResourceBundle resources) {
        editionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        endingDateColumn.setCellValueFactory(new PropertyValueFactory<>("begin"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));


        editions = FXCollections.observableArrayList();
        editions.clear();
        editionTable.setItems(editions);
    }
}
