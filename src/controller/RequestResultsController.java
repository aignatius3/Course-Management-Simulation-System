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
import model.Weka;

import java.io.File;

import static jdk.nashorn.internal.objects.NativeMath.round;

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

    @FXML
    private Label numRequests;

    @FXML
    private Label grantedRequests;

    @FXML
    private Label requestsDenied1;

    @FXML
    private Label requestsDenied2;

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

        int totalRequests = myScratchpad.getRequests().size();
        int requestsGranted  = 0;
        int deniedRequests1 = 0;
        int deniedRequests2 = 0;
        for (Request request : myScratchpad.getRequests()) {
            if (request.getResult().equals("Granted")) {
                requestsGranted++;
            } else if (request.getResult().equals("Denied - Instructor not assigned to course")) {
                deniedRequests1++;
            } else {
                deniedRequests2++;
            }
        }

        numRequests.setText(numRequests.getText() + " : " + Integer.toString(totalRequests));
        grantedRequests.setText(grantedRequests.getText() + " : " +
                Integer.toString(requestsGranted) + " (" +
                Double.toString(Math.round((double) requestsGranted * 100 / totalRequests * 100) / 100)
                + "%)");
        requestsDenied1.setText(requestsDenied1.getText() + " : " +
                Integer.toString(deniedRequests1) + " (" +
                Double.toString(Math.round((double) deniedRequests1 * 100 / totalRequests * 100) / 100)
                + "%)");
        requestsDenied2.setText(requestsDenied2.getText() + " : " +
                Integer.toString(deniedRequests2) + " (" +
                Double.toString(Math.round((double) deniedRequests2 * 100 / totalRequests * 100) / 100)
                + "%)");
    }

    @FXML
    public void handlePauseClicked() {
        try {
            Weka weka = new Weka();
            String s = weka.generateApropri();
            System.out.println(s);
        } catch (Exception e) {
        }

        myScratchpad.nextTerm();
        Main.saveScratchpadToBinary();
        myApp.load(new File("/view/Initialize.fxml"));

    }

    @FXML
    public void handleContinueClicked() {
        try {
            Weka weka = new Weka();
            String s = weka.generateApropri();
            System.out.println(s);
        } catch (Exception e) {
        }

        myScratchpad.nextTerm();
        Main.saveScratchpadToBinary();
        myApp.load(new File("/view/AssignInstructors.fxml"));

    }
}
