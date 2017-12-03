package controller;

import javafx.fxml.FXML;

import java.io.File;
import javafx.scene.control.Button;

/**
 * Created by Ashwin Ignatius on 12/2/2017.
 */
public class InitializeController extends Controller {

    @FXML
    private Button resumeSim;

    @FXML
    private Button restartSim;

    @FXML
    public void handleAdminPressed() {
        resumeSim.setOpacity(1.0);
        restartSim.setOpacity(1.0);
    }

    @FXML
    public void handleRestartSimPressed() {
        String[] managementSystemFiles = {"./TestCases/test_case1/courses.csv", "./TestCases/test_case1/instructors.csv",
                "./TestCases/test_case1/students.csv", "./TestCases/test_case1/programs.csv",
                "./TestCases/test_case1/listings.csv", "./TestCases/test_case1/prereqs.csv"};
        for (String nextFileName : managementSystemFiles) {
            myApp.getScratchpad().uploadFileContents(nextFileName);
        }

        myApp.load(new File("../view/AssignInstructors.fxml"));
    }
}
