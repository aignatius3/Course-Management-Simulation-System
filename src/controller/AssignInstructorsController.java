package controller;

import fxapp.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Course;
import model.Instructor;
import model.Scratchpad;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 12/3/2017.
 */
public class AssignInstructorsController extends Controller {
    @FXML
    private Label termLabel;

    @FXML
    private TableView<Course> courseTable;

    private TableColumn courseNumCol;

    private TableColumn courseNameCol;

    private TableColumn courseCostCol;

    private ObservableList<Course> courseData = FXCollections.observableArrayList();

    @FXML
    private TableView<Instructor> instructorTable;

    private TableColumn instructorNumCol;

    private TableColumn instructorNameCol;

    private TableColumn instructorOHCol;

    private TableColumn instructorEmailCol;

    private ObservableList<Instructor> instructorData = FXCollections.observableArrayList();

    public void initialize() {
        this.myScratchpad = Main.getScratchpad();
        this.cycle = Main.getCycle();
        termLabel.setText(myScratchpad.getTerms()[myScratchpad.getTerm()] + " " + Integer.toString(myScratchpad.getYear()));

        courseNumCol = new TableColumn("ID");
        courseNameCol = new TableColumn("Name");
        courseCostCol = new TableColumn("Cost");

        courseNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Course course = (Course) dataFeatures.getValue();
                        return new SimpleIntegerProperty(Integer.parseInt(course.getCourseID()));
                    }
                }
        );

        courseNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Course course = (Course) dataFeatures.getValue();
                        return new SimpleStringProperty(course.getShortName());
                    }
                }
        );

        courseCostCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Course course = (Course) dataFeatures.getValue();
                        return new SimpleStringProperty(course.getCost());
                    }
                }
        );

        courseData = FXCollections.observableArrayList();
        for (Course course : myScratchpad.getCourses().values()) {
            courseData.add(course);
        }
        courseTable.setItems(courseData);
        courseTable.getColumns().addAll(courseNumCol, courseNameCol, courseCostCol);


        instructorNumCol = new TableColumn("ID");
        instructorNameCol = new TableColumn("Name");
        instructorOHCol = new TableColumn("Office Hours");
        instructorEmailCol = new TableColumn("Email");

        instructorNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Instructor instructor = (Instructor) dataFeatures.getValue();
                        return new SimpleIntegerProperty(Integer.parseInt(instructor.getID()));
                    }
                }
        );

        instructorNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Instructor instructor = (Instructor) dataFeatures.getValue();
                        return new SimpleStringProperty(instructor.getName());
                    }
                }
        );

        instructorOHCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Instructor instructor = (Instructor) dataFeatures.getValue();
                        return new SimpleStringProperty(instructor.getOfficeHours());
                    }
                }
        );

        instructorEmailCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        Instructor instructor = (Instructor) dataFeatures.getValue();
                        return new SimpleStringProperty(instructor.getEmail());
                    }
                }
        );

        instructorData = FXCollections.observableArrayList();
        for (Instructor instructor : myScratchpad.getInstructors().values()) {
            instructorData.add(instructor);
        }
        instructorTable.setItems(instructorData);
        instructorTable.getColumns().addAll(instructorNumCol,instructorNameCol, instructorOHCol, instructorEmailCol);
    }

    @FXML
    public void handleAssignPressed() {
        Course course = courseTable.getSelectionModel().getSelectedItem();
        Instructor instructor = instructorTable.getSelectionModel().getSelectedItem();

        if (course == null || instructor == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occured!");
            alert.setContentText("Please select a Course AND an Instructor");
            alert.showAndWait();
            return;
        }

        myScratchpad.assignInstructors(course, instructor);
        courseData.remove(course);
        instructorData.remove(instructor);
    }

    @FXML
    public void handleDonePressed() {
        myScratchpad.loadRequests();
        myApp.load(new File("../view/ReassignInstructor.fxml"));
    }

}
