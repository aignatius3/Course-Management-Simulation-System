package Model;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class Student extends User {

    private String ID;
    private String address;
    private String phoneNumber;
    private String programID;

    public Student(String username, String password, String name, String emailAddress,
                      String ID, String address, String phoneNumber, String programID) {
        super(username, password, name, emailAddress);
        this.ID = ID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.programID = programID;
    }
}
