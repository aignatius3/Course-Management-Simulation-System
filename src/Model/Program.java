package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Program {
    private String ID;
    private String name;
    private List<Course> listings;

    public Program(String ID, String name) {
        this.ID = ID;
        this.name = name;
        this.listings = new ArrayList<>();
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

    public void addListing(Course course) {
        this.listings.add(course);
    }
}
