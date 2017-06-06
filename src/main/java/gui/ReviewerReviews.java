package gui;

import domain.Paper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewerReviews implements Initializable
{

    @FXML
    TableView tabel;
    @FXML
    TableColumn paperName;
    @FXML
    TableColumn topic;
    @FXML
    TableColumn author;
    @FXML
    TextArea textArea;
    @FXML
    ObservableList<Paper> model;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        paperName.setCellValueFactory(new PropertyValueFactory<Paper, String>("title"));
        topic.setCellValueFactory(new PropertyValueFactory<Paper, String>("topic"));
        author.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Paper, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Paper, String> c)
            {
                return new SimpleStringProperty(c.getValue().getUser().getUsername());
            }
        });

        model = FXCollections.observableArrayList();
        tabel.setItems(model);
        tabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Paper>()
        {
            @Override
            public void changed(ObservableValue<? extends Paper> observable, Paper oldValue, Paper newValue)
            {

            }
        });
    }
}
