package controller;

import fxapp.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.Instructor;
import model.Request;

import java.io.File;

/**
 * Created by Ashwin Ignatius on 12/3/2017.
 */
public class RequestResultsController extends Controller {
    @FXML
    private Label termLabel;

    @FXML
    private TableView<Request> requestResultsTable;

    private TableColumn courseNumCol;

    private TableColumn studentNumCol;

    private TableColumn resultCol;

    private ObservableList<Request> requestsData = FXCollections.observableArrayList();

    public void initialize() {
        this.myScratchpad = Main.getScratchpad();
        this.cycle = myScratchpad.getCycle();
        termLabel.setText(myScratchpad.getTerms()[myScratchpad.getTerm()] + " " + Integer.toString(myScratchpad.getYear()));

        courseNumCol = new TableColumn("Course ID");
        studentNumCol = new TableColumn("Student ID");
        resultCol = new TableColumn("Result");

        courseNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Request request = (Request) dataFeatures.getValue();
                        return new SimpleIntegerProperty(Integer.parseInt(request.getCourseID()));
                    }
                }
        );

        studentNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Request request = (Request) dataFeatures.getValue();
                        return new SimpleIntegerProperty(Integer.parseInt(request.getStudentID()));
                    }
                }
        );

        resultCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Request request = (Request) dataFeatures.getValue();
                        return new SimpleStringProperty(request.getResult());
                    }
                }
        );

        requestsData = FXCollections.observableArrayList();
        for (Request request : myScratchpad.getRequests()) {
            requestsData.add(request);
        }
        requestResultsTable.setItems(requestsData);
        requestResultsTable.getColumns().addAll(courseNumCol, studentNumCol, resultCol);
    }

    @FXML
    public void handlePauseClicked() {
        myScratchpad.nextTerm();
        Main.saveScratchpadToBinary();
        myApp.load(new File("../view/Initialize.fxml"));

    }

    @FXML
    public void handleContinueClicked() {
        myScratchpad.nextTerm();
        Main.saveScratchpadToBinary();
        myApp.load(new File("../view/AssignInstructors.fxml"));

    }
}
