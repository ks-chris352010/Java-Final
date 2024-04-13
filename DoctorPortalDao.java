import java.util.List;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    // Constructor
    public DoctorPortalDao() {
        // Assuming you have a valid connection object named 'connection' available
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDao = new UserDao(databaseConnection.getCon());
        healthDataDao = new HealthDataDao(databaseConnection.getCon());
    }

    // Method to get a doctor by ID
    public Doctor getDoctorById(int doctorId) {
        User user = userDao.getUserById(doctorId);
        if (user != null && user.isDoctor()) {
            // Assuming Doctor extends User and has additional fields like medical license number and specialization
            return new Doctor(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), true, "licenseNumber", "specialization");
        }
        return null;
    }

    // Method to get patients by doctor ID
    public List<User> getPatientsByDoctorId(int doctorId) {
        return userDao.getPatientsForDoctor(doctorId);
    }

    // Method to get health data by patient ID
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }

    

    // Add more methods for other doctor-specific tasks
}
