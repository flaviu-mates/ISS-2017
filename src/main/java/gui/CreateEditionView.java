package gui;

import domain.Conference;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Created by ciprian on 6/3/2017.
 */
public class CreateEditionView {
    @FXML
    public DatePicker submissionDeadlineField;
    @FXML
    public DatePicker finalDeadlineField;
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public TextField editionNameField;
    @FXML
    public TableColumn<Conference, String> confNameColumn;
    @FXML
    public TableView<Conference> conferencesTable;

    private ObservableList<Conference> conferences;
}
