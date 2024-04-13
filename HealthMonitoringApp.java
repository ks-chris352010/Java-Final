import java.util.ArrayList;
import java.util.List;

public class HealthMonitoringApp {
    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */
    private static UserDao userDao;

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDao = new UserDao(databaseConnection.getCon());

        // Test register a new user:
        testRegisterUser();

        // Test login user:
        testLoginUser();

    }

    public static void testLoginUser() {
        String userEmail = "john@example.com";
        String userPassword = "password";

        boolean loginSuccess = loginUser(userEmail, userPassword);

        if (loginSuccess) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Incorrect email or password. Please try again.");
        }
    }

    public static boolean loginUser(String email, String password) {
        return userDao.verifyPassword(email, password);
    }

    public static void testRegisterUser() {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "Qurrat-Ul-Ain", "Malik", "qmalik@gmail.com", "SuperSecurePassword", true);
        User user2 = new User(2, "Chris", "Cormier", "chrisloveshismommy@gmail.com", "theStrongestPassword", false);
        userList.add(user1);
        userList.add(user2);

        for (User user : userList) {
            userDao.createUser(user);
        }
    }

}
