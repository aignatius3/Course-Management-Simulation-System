package Model;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Instructor extends User {

    private String ID;
    private String officeHours;
    private int coursesTeaching;

    public Instructor(String username, String password, String name, String emailAddress,
                      String ID, String OfficeHours, int coursesTeaching) {
        super(username, password, name, emailAddress);
        this.ID = ID;
        this.officeHours = officeHours;
        this.coursesTeaching = coursesTeaching;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public int getCoursesTeaching() {
        return coursesTeaching;
    }

    public void setCoursesTeaching(int coursesTeaching) {
        this.coursesTeaching = coursesTeaching;
    }
}
