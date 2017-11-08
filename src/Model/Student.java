package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Student extends User {

    private String ID;
    private String address;
    private String phoneNumber;
    private String programID;
    private List<HashMap<Course, String>> records;

    public Student(String username, String password, String name, String emailAddress,
                      String ID, String address, String phoneNumber, String programID) {
        super(username, password, name, emailAddress);
        this.ID = ID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.programID = programID;
        this.records = new ArrayList<HashMap<Course, String>>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public List<HashMap<Course, String>> getRecords() {
        return records;
    }

    public void setRecords(List<HashMap<Course, String>> records) {
        this.records = records;
    }

    public void addCourseRecord(Course course, String grade, int cycle) {
        records.get(cycle).put(course, grade);
    }
}
