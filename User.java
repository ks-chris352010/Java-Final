/**
 * Represents a user in the system.
*/
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isDoctor;

    /**
     * Constructs a User object with the specified attributes.
     *
     * @param id        The unique identifier of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password of the user.
     * @param isDoctor  Indicates whether the user is a doctor.
    */
    public User(int id, String firstName, String lastName, String email, String password, boolean isDoctor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's ID.
    */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The user's ID to set.
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return The user's first name.
    */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The user's first name to set.
    */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return The user's last name.
    */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The user's last name to set.
    */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The user's email address.
    */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The user's email address to set.
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The user's password.
    */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The user's password to set.
    */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is a doctor.
     *
     * @return True if the user is a doctor, false otherwise.
    */
    public boolean isDoctor() {
        return isDoctor;
    }

    /**
     * Sets whether the user is a doctor.
     *
     * @param doctor True if the user is a doctor, false otherwise.
    */
    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }
}
