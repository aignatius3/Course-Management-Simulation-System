package controller;

import fxapp.Main;
import javafx.fxml.FXML;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import model.Scratchpad;

/**
 * Created by Ashwin Ignatius on 12/2/2017.
 */
public class InitializeController extends Controller {

    @FXML
    private Button resumeSim;

    @FXML
    private Button restartSim;

    @FXML
    public void handleRestartSimPressed() {
        String[] necessaryFiles = {"courses.csv", "instructors.csv", "students.csv", "programs.csv",
            "listings.csv", "prereqs.csv", "requests0.csv"};
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory =
                directoryChooser.showDialog(myApp.getWindow());
        if (selectedDirectory != null) {
            String filesMissing = "";
            File[] listOfFiles = selectedDirectory.listFiles();
            ArrayList<String> fileNames = new ArrayList<>();
            for (int i = 0; i < listOfFiles.length; i++) {
                fileNames.add(listOfFiles[i].getName());
            }
            for (int i = 0; i < necessaryFiles.length; i++) {
                if (!fileNames.contains(necessaryFiles[i])) {
                    filesMissing = filesMissing + necessaryFiles[i] + "\n";
                }
            }
            if (!filesMissing.equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("The Directory is Missing the Following Files:" +
                         "\n" + filesMissing);
                alert.showAndWait();
                return;
            }
            Main.getScratchpad().restartSimulation();
            myScratchpad.setTestCaseDirectory(selectedDirectory.getAbsolutePath());
            String[] managementSystemFiles = {"/courses.csv", "/instructors.csv",
                    "/students.csv", "/programs.csv",
                    "/listings.csv", "/prereqs.csv"};
            for (String nextFileName : managementSystemFiles) {
                myApp.getScratchpad().uploadFileContents(myScratchpad.getTestCaseDirectory().replaceAll("\\\\", "/")
                        + nextFileName);
            }

            myScratchpad.designateTerm();
            myApp.load(new File("/view/AssignInstructors.fxml"));
        }

    }

    @FXML
    public void handleResumePressed() {
        Main.loadScratchpadFromBinary();
        myApp.load(new File("/view/AssignInstructors.fxml"));
    }
}
