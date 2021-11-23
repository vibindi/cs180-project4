/**
 * User Class
 * class to create users
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
public abstract class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
        UpdateFiles.writeToFiles();
    }

    public void setPassword(String password) {
        this.password = password;
        UpdateFiles.writeToFiles();
    }

    @Override
    public String toString() {
        return "Username: " + username + "|Password: " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            return u.getUsername().equals(this.username)
                    && u.getPassword().equals(this.password);
        }
        return false;
    }

}
