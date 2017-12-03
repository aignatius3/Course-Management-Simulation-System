package model;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Request {
    private String studentID;
    private String courseID;
    private String result;

    public Request(String studentID, String courseID) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.result = "";
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
