import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Manages user-related operations in the database.
*/
public class UserDao {
    private Connection connection;

    /**
     * Constructs a UserDao object with the specified database connection.
     *
     * @param connection The database connection to use.
    */
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new user in the database.
     *
     * @param user The User object representing the user to be created.
     * @return True if the user creation is successful, false otherwise.
    */
    public boolean createUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sql = "INSERT INTO users (first_name, last_name, email, password, is_doctor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a user by their ID from the database.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object representing the retrieved user, or null if not found.
    */
    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves a user by their email from the database.
     *
     * @param email The email of the user to retrieve.
     * @return The User object representing the retrieved user, or null if not found.
    */
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates user information in the database.
     *
     * @param user The User object representing the updated user information.
     * @return True if the user update is successful, false otherwise.
    */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, is_doctor = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setBoolean(4, user.isDoctor());
            statement.setInt(5, user.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param id The ID of the user to delete.
     * @return True if the user deletion is successful, false otherwise.
    */
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies whether the given password matches the stored password for the specified email.
     *
     * @param email    The email of the user whose password is to be verified.
     * @param password The password to verify.
     * @return True if the password is verified, false otherwise.
    */
    public boolean verifyPassword(String email, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Associates a doctor with a patient in the database.
     *
     * @param doctorId  The ID of the doctor.
     * @param patientId The ID of the patient.
     * @return True if the association is successful, false otherwise.
    */
    public boolean addDoctorPatientRelation(int doctorId, int patientId) {
        String sql = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Removes the association between a doctor and a patient from the database.
     *
     * @param doctorId  The ID of the doctor.
     * @param patientId The ID of the patient.
     * @return True if the association removal is successful, false otherwise.
    */
    public boolean removeDoctorPatientRelation(int doctorId, int patientId) {
        String sql = "DELETE FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if a doctor-patient relation exists in the database.
     *
     * @param doctorId  The ID of the doctor.
     * @param patientId The ID of the patient.
     * @return True if the relation exists, false otherwise.
    */
    public boolean hasDoctorPatientRelation(int doctorId, int patientId) {
        String sql = "SELECT * FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves the doctors associated with a specific patient from the database.
     *
     * @param patientId The ID of the patient.
     * @return A list of User objects representing the doctors associated with the patient.
    */
    public List<User> getDoctorsForPatient(int patientId) {
        List<User> doctors = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE id IN (SELECT doctor_id FROM doctor_patient WHERE patient_id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User doctor = extractUserFromResultSet(resultSet);
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
    
    /**
     * Retrieves the patients associated with a specific doctor from the database.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of User objects representing the patients associated with the doctor.
    */
    public List<User> getPatientsForDoctor(int doctorId) {
        List<User> patients = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE id IN (SELECT patient_id FROM doctor_patient WHERE doctor_id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User patient = extractUserFromResultSet(resultSet);
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    /**
     * Helper method to convert a ResultSet row into a User object.
     *
     * @param resultSet The ResultSet containing the user information.
     * @return A User object created from the ResultSet data.
     * @throws SQLException If an SQL exception occurs while accessing the ResultSet.
    */
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getBoolean("is_doctor")
        );
    }
}
