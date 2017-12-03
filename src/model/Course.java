package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Course {
    private String courseID;
    private String shortName;
    private String cost;
    private List<String> prereqs;


    public Course(String courseID, String shortName, String cost) {
        this.courseID = courseID;
        this.shortName = shortName;
        this.cost = cost;
        this.prereqs = new ArrayList<>();
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<String> getPrereqs() {
        return prereqs;
    }

    public void addPrereq(String courseID) {
        prereqs.add(courseID);
    }
}
