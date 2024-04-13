import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    // Constructor to initialize the connection:
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new user:
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

    // Method to get a user by ID:
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

    // Method to get a user by email:
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

    // Method to update a user:
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

    // Method to delete a user:
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

    // Method to verify password:
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

        // Method to associate a doctor with a patient:
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
    
        // Method to remove the association between a doctor and a patient:
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
    
        // Method to check if a doctor-patient relation exists:
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
    
        // Method to get a patient's doctors:
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
    
        // Method to get a doctor's patients:
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

    // Method to convert a user entry form the database into user object:
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
