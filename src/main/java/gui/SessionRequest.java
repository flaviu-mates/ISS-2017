package gui;

import domain.Review;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SessionRequest implements Initializable
{
    @FXML
    private TableView<Review> table;

    @FXML
    private TableColumn<Review, String> paperColumn;
    @FXML
    private TableColumn<Review, String> userColumn;

    @FXML
    private ObservableList<Review> model;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }
}
