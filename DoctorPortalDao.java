import java.util.List;

/**
 * The DAO (Data Access Object) class for managing doctor-related operations in the portal.
 * This class facilitates interactions with the database for retrieving doctor, patient, and health data information.
*/
public class DoctorPortalDao {

    private UserDao userDao;
    private HealthDataDao healthDataDao;

    /**
     * Constructs a DoctorPortalDao object and initializes UserDao and HealthDataDao.
     * Assumes a valid database connection is available.
    */
    public DoctorPortalDao() {
        // Assuming you have a valid connection object named 'connection' available
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDao = new UserDao(databaseConnection.getCon());
        healthDataDao = new HealthDataDao(databaseConnection.getCon());
    }

    /**
     * Retrieves a doctor by their unique identifier.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return The Doctor object if found, or null if not found or not a doctor.
    */
    public Doctor getDoctorById(int doctorId) {
        User user = userDao.getUserById(doctorId);
        if (user != null && user.isDoctor()) {
            // Assuming Doctor extends User and has additional fields like medical license number and specialization
            return new Doctor(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), true, "licenseNumber", "specialization");
        }
        return null;
    }

    /**
     * Retrieves a list of patients associated with a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of User objects representing patients, or an empty list if none found.
    */
    public List<User> getPatientsByDoctorId(int doctorId) {
        return userDao.getPatientsForDoctor(doctorId);
    }

    /**
     * Retrieves health data associated with a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of HealthData objects representing health data records, or an empty list if none found.
    */
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }
}

