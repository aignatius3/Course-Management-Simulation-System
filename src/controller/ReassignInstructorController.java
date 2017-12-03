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
import model.Course;
import model.Request;

import java.io.File;

/**
 * Created by Ashwin Ignatius on 12/3/2017.
 */
public class ReassignInstructorController extends Controller {


    class CourseTaught {
        private int courseID;

        private String courseName;

        private int instructorID;

        private String instructorName;

        public CourseTaught() {

        }

        public int getCourseID() {
            return courseID;
        }

        public void setCourseID(int courseID) {
            this.courseID = courseID;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getInstructorID() {
            return instructorID;
        }

        public void setInstructorID(int instructorID) {
            this.instructorID = instructorID;
        }

        public String getInstructorName() {
            return instructorName;
        }

        public void setInstructorName(String instructorName) {
            this.instructorName = instructorName;
        }
    }

    class CourseRequested {
        private int courseID;

        private String courseName;

        private int numRequests;

        public CourseRequested() {

        }

        public int getCourseID() {
            return courseID;
        }

        public void setCourseID(int courseID) {
            this.courseID = courseID;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getNumRequests() {
            return numRequests;
        }

        public void setNumRequests(int numRequests) {
            this.numRequests = numRequests;
        }
    }

    @FXML
    private Label termLabel;

    @FXML
    private TableView<ReassignInstructorController.CourseTaught> coursesTaughtTable;

    private TableColumn courseNumCol;

    private TableColumn courseNameCol;

    private TableColumn instructorNumCol;

    private TableColumn instructorNameCol;

    private ObservableList<ReassignInstructorController.CourseTaught> coursesTaughtData = FXCollections.observableArrayList();

    @FXML
    private TableView<ReassignInstructorController.CourseRequested> coursesRequestedTable;

    private TableColumn courseNumCol2;

    private TableColumn courseNameCol2;

    private TableColumn numRequestsCol;

    private ObservableList<ReassignInstructorController.CourseRequested> coursesRequestedData = FXCollections.observableArrayList();

    @FXML
    private TextField reassignText;


    public void initialize() {
        this.myScratchpad = Main.getScratchpad();
        this.cycle = myScratchpad.getCycle();
        termLabel.setText(myScratchpad.getTerms()[myScratchpad.getTerm()] + " " + Integer.toString(myScratchpad.getYear()));

        courseNumCol = new TableColumn("Course ID");
        courseNameCol = new TableColumn("Course Name");
        instructorNumCol = new TableColumn("Instructor ID");
        instructorNameCol = new TableColumn("Instructor Name");

        courseNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseTaught courseTaught =
                                (ReassignInstructorController.CourseTaught) dataFeatures.getValue();
                        return new SimpleIntegerProperty(courseTaught.getCourseID());
                    }
                }
        );

        courseNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseTaught courseTaught =
                                (ReassignInstructorController.CourseTaught) dataFeatures.getValue();
                        return new SimpleStringProperty(courseTaught.getCourseName());
                    }
                }
        );

        instructorNumCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseTaught courseTaught =
                                (ReassignInstructorController.CourseTaught) dataFeatures.getValue();
                        return new SimpleIntegerProperty(courseTaught.getInstructorID());
                    }
                }
        );

        instructorNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseTaught courseTaught =
                                (ReassignInstructorController.CourseTaught) dataFeatures.getValue();
                        return new SimpleStringProperty(courseTaught.getInstructorName());
                    }
                }
        );

        coursesTaughtData = FXCollections.observableArrayList();
        ReassignInstructorController.CourseTaught temp;
        for (Object key : myScratchpad.getCoursesTaught().keySet()) {
            temp = new ReassignInstructorController.CourseTaught();
            temp.setCourseID(Integer.valueOf((String) key));
            temp.setCourseName(myScratchpad.getCourses().get(key).getShortName());
            temp.setInstructorID(Integer.parseInt(myScratchpad.getCoursesTaught().get(key)));
            temp.setInstructorName(myScratchpad.getInstructors().get(
                    myScratchpad.getCoursesTaught().get(key)).getName());
            coursesTaughtData.add(temp);
        }
        coursesTaughtTable.setItems(coursesTaughtData);
        coursesTaughtTable.getColumns().addAll(courseNumCol, courseNameCol, instructorNumCol, instructorNameCol);

        courseNumCol2 = new TableColumn("Course ID");
        courseNameCol2 = new TableColumn("Course Name");
        numRequestsCol = new TableColumn("Number of Requests");

        courseNumCol2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseRequested courseRequested =
                                (ReassignInstructorController.CourseRequested) dataFeatures.getValue();
                        return new SimpleIntegerProperty(courseRequested.getCourseID());
                    }
                }
        );

        courseNameCol2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseRequested courseRequested =
                                (ReassignInstructorController.CourseRequested) dataFeatures.getValue();
                        return new SimpleStringProperty(courseRequested.getCourseName());
                    }
                }
        );

        numRequestsCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures dataFeatures) {
                        ReassignInstructorController.CourseRequested courseRequested =
                                (ReassignInstructorController.CourseRequested) dataFeatures.getValue();
                        return new SimpleIntegerProperty(courseRequested.getNumRequests());
                    }
                }
        );

        coursesRequestedData = FXCollections.observableArrayList();
        ReassignInstructorController.CourseRequested temp2;
        for (Object key : myScratchpad.getCoursesRequested().keySet()) {
            temp2 = new ReassignInstructorController.CourseRequested();
            temp2.setCourseID(Integer.parseInt((String) key));
            temp2.setCourseName(myScratchpad.getCourses().get(key).getShortName());
            temp2.setNumRequests(myScratchpad.getCoursesRequested().get(key));
            coursesRequestedData.add(temp2);
        }
        coursesRequestedTable.setItems(coursesRequestedData);
        coursesRequestedTable.getColumns().addAll(courseNumCol2, courseNameCol2, numRequestsCol);
    }

    @FXML
    public void handleReassignPressed() {
        String s = reassignText.getText();
        if (!s.equals("")) {
            String[] tokens = s.split(",");
            if (!myScratchpad.getCourses().keySet().contains(tokens[0]) ||
                    !myScratchpad.getInstructors().keySet().contains(tokens[1])) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("An Error Occured!");
                alert.setContentText("Enter valid Course ID and Instructor ID");
                alert.showAndWait();
                return;
            } else {
                myScratchpad.reassignInstructor(s);
                myApp.load(new File("../view/RequestResults.fxml"));
            }
        }
    }

    @FXML
    public void handleNoReassignPressed() {
        myScratchpad.processRequests();
        myApp.load(new File("../view/RequestResults.fxml"));
    }

}
