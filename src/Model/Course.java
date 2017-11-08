package Model;

import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Course {
    private int courseID;
    private String name;
    private int cost;
    private List<Course> prereqs;

    public Course(int courseID, String name, int cost, List<Course> prereqs) {
        this.courseID = courseID;
        this.name = name;
        this.cost = cost;
        this.prereqs = prereqs;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
