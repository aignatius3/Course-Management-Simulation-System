package model;

import fxapp.Main;
import javafx.scene.control.Alert;

import java.io.*;

/**
 * Created by Ashwin Ignatius on 12/3/2017.
 */
public class Persistence {
    private Object backing;

    public Persistence(Object backing) {
        this.backing = backing;
    }

    public void saveToBinary(File f) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(backing);
            oos.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("Failed to Save File");
            alert.showAndWait();
            return;
        }
    }

    public void loadFromBinary(File f) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            backing = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("Failed to Load File");
            alert.showAndWait();
            return;
        }
    }

    public Object getBacking() {
        return this.backing;
    }
}
