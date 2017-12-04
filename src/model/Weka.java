package model;

import fxapp.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class Weka {
    Main myApp;
    Scratchpad s;
    public void setMainApp(Main myApp) {
        this.myApp = myApp;
        this.s = Main.getScratchpad();
    }
    public String generateApropri() {
        List<Student> students = new ArrayList<>(s.getStudents().values());
        List<Course> courses = new ArrayList<>(s.getCourses().values());
        List<HashMap<String, Boolean>> data = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            HashMap<String, String> records = new HashMap<>();
            for (int j = 0; j < students.get(i).getRecords().size(); j++) {
                records.putAll(students.get(i).getRecords().get(j));
            }
            Set<String> takenClasses = records.keySet();
            HashMap<String, Boolean> classes = new HashMap<>();

            for (int j = 0; j < courses.size(); j++) {
                boolean temp = takenClasses.contains(courses.get(j).getCourseID());
                classes.put(courses.get(j).getCourseID(),temp );
            }
            data.add(classes);
            //System.out.println(i);
        }
        System.out.println(data);
        File file = new File("data.arff");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("@RELATION classes\n");
            writer.write("@ATTRIBUTE " + "2" + " {true,false}\n");
            writer.write("@ATTRIBUTE " + "5" + " {true,false}\n");
            writer.write("@ATTRIBUTE " + "6" + " {true,false}\n");
            writer.write("@ATTRIBUTE " + "8" + " {true,false}\n");

            /*for(int i = 0; i < courses.size(); i++) {
                writer.write("@ATTRIBUTE " + courses.get(i).getCourseID() + " {true,false}\n");
            }*/
            writer.write("@DATA\n");    
            for (int i = 0; i < students.size(); i++) {
                /*for (int j = 0; j < courses.size() - 1; j++) {
                    writer.write(data.get(i).get(courses.get(j).getCourseID()) + ",");
                }
                    writer.write(data.get(i).get(courses.get(courses.size() - 1).getCourseID()) + "\n");*/
                writer.write(data.get(i).get("2") + ",");
                writer.write(data.get(i).get("5") + ",");
                writer.write(data.get(i).get("6") + ",");
                writer.write(data.get(i).get("8") + "\n");
            }
            writer.flush();
            writer.close();

        } catch (java.io.IOException e) {

        }
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("data.arff");
            Instances d = source.getDataSet();

            Apriori apriori = new Apriori();
            apriori.buildAssociations(d);
            return apriori.toString();
        } catch (Exception e) {

        }

    }
}
