package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Course {
    private int courseID;
    private String shortName;
    private int cost;
    private List<String> prereqs;

    public Course(int courseID, String shortName, int cost) {
        this.courseID = courseID;
        this.shortName = shortName;
        this.cost = cost;
        this.prereqs = new ArrayList<>();
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<String> getPrereqs() {
        return prereqs;
    }

    public void setPrereqs(List<String> prereqs) {
        this.prereqs = prereqs;
    }

    public void addPrereq(String courseID) {
        prereqs.add(courseID);
    }
}
