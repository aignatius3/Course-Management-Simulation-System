package fxapp;

import model.*;
import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */
public class Main extends Application {
    private Stage window;
    private static Scratchpad scratchpad;
    private static final File scratchpadFile = new File("ScratchpadSaveState.dat");

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.scratchpad = new Scratchpad();
        window = primaryStage;
        load(new File("/view/Initialize.fxml"));
    }

    public void load(File file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/" + file.getName()));
            AnchorPane sceneLayout = loader.load();

            Controller controller = loader.getController();
            controller.setMainApp(this);

            window.setTitle("Course Management Simulation");
            window.setScene(new Scene(sceneLayout));
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Scratchpad getScratchpad() {
        return scratchpad;
    }

    public static void saveScratchpadToBinary() {
        Persistence p = new Persistence(scratchpad);
        p.saveToBinary(scratchpadFile);
    }

    public static void loadScratchpadFromBinary() {
        Persistence p = new Persistence(scratchpad);
        p.loadFromBinary(scratchpadFile);
        scratchpad = (Scratchpad) p.getBacking();
    }

    public Stage getWindow() {
        return window;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
