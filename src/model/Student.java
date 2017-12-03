package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Student {

    private String ID;
    private String name;
    private String address;
    private String phoneNumber;
    private String programID;
    private List<HashMap<String, String>> records;

    public Student(String ID, String name, String address, String phoneNumber,
                   String programID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.programID = programID;
        this.records = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public List<HashMap<String, String>> getRecords() {
        return records;
    }

    public void addCourseRecord(String courseId, String grade, int index) {
        records.get(index).put(courseId, grade);
    }
}
