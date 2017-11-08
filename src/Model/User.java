package Model;

/**
 * Created by Ashwin Ignatius on 11/8/2017.
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String emailAddress;

    public User(String username, String password, String name, String emailAddress) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
