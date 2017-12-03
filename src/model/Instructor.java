package model;

import java.io.Serializable;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Instructor implements Serializable {

    private String ID;
    private String name;
    private String officeHours;
    private String email;
    private String courses;

    public Instructor(String ID, String name, String officeHours, String email, String courses) {
        this.ID = ID;
        this.name = name;
        this.officeHours = officeHours;
        this.email = email;
        this.courses = courses;
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

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}
