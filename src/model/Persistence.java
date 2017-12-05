package model;

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
            System.out.println("Failed to save file");
            e.printStackTrace();
        }
    }

    public void loadFromBinary(File f) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            backing = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("No Previous Simulation, Start a New Simulation");
            alert.showAndWait();
            return;
        }
    }

    public Object getBacking() {
        return this.backing;
    }
}
