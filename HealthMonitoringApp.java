import java.util.ArrayList;
import java.util.List;

public class HealthMonitoringApp {
    private static UserDao userDao;
    private static MedicineReminderManager medicineReminderManager;
    private static HealthDataDao healthDataDao;
    private static DoctorPortalDao doctorPortalDao;

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDao = new UserDao(databaseConnection.getCon());
        medicineReminderManager = new MedicineReminderManager(databaseConnection.getCon());
        healthDataDao = new HealthDataDao(databaseConnection.getCon());
        doctorPortalDao = new DoctorPortalDao();

        // Test register a new user:
        testRegisterUser(); // Commented out because data is already in the database as of writing this comment.

        // Test login user:
        testLoginUser("qmalik@gmail.com", "SuperSecurePassword");

        // Test add health data:
        testAddHealthData();

        // Test generate recommendations:
        testGenerateRecommendations(2);
        testGenerateRecommendations(3);
        testGenerateRecommendations(4);
        testGenerateRecommendations(6);
        testGenerateRecommendations(7);
        testGenerateRecommendations(8);
        testGenerateRecommendations(9); 

        // Test add medicine reminder:
        testAddMedicineReminder(8, "Panacea", "500kg", "Once a day", "1967-01-04", "2024-04-13");
        testAddMedicineReminder(2, "Placebo", "500mg", "Twice a day (take with food)", "2024-01-11", "2024-04-12");
        testAddMedicineReminder(3, "Beta Blockers", "20mg", "Twice a day (take with food)", "2024-01-02", "2024-04-10");

        // Test get reminders for a specific user:
        testGetRemindersForUser(2);
        testGetRemindersForUser(3);
        testGetRemindersForUser(8);

        // Test get due reminders for a specific user:
        testGetDueRemindersForUser(3);

        // Test doctor portal:
        testDoctorPortal(1);
    }

    public static void testLoginUser(String userEmail, String userPassword) {
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
        userList.add(new User(1, "Qurrat-Ul-Ain", "Malik", "qmalik@gmail.com", "SuperSecurePassword", true));
        userList.add(new User(2, "Chris", "Cormier", "chrisloveshismommy@gmail.com", "theStrongestPassword", false));
        userList.add(new User(3, "Kallie", "White", "k_white@yahoo.ca", "ThePinacaleOfPasswordSecurity", false));
        userList.add(new User(4, "Mason", "Brumsey", "mb_thegreat@hotmail.com", "BlueDabodedaboda", false));
        userList.add(new User(5, "James", "Atwood", "j_a@gmail.com", "DoctorDoctor", true));
        userList.add(new User(6, "Chris", "TheSecond", "ks.chris352010@gmail.com", "EvenBetterTheSecondTime", false));
        userList.add(new User(7, "Kendra", "King", "k_king@walmart.ca", "IAmTheStarLord", false));
        userList.add(new User(8, "Star", "King", "star_king@homehardware.org", "StarKing_KingOfStars", false));
        userList.add(new User(9, "Tsukuomi", "Midoriya", "MoonSlayer@crunchyroll.ca", "OmaeWaMouShindeiru", false));


        for (User user : userList) {
            userDao.createUser(user);
        }
        userDao.addDoctorPatientRelation(1,2);
        userDao.addDoctorPatientRelation(1,4);
        userDao.addDoctorPatientRelation(1,3);
        userDao.addDoctorPatientRelation(1,6);
        userDao.addDoctorPatientRelation(5,7);
        userDao.addDoctorPatientRelation(5,8);
        userDao.addDoctorPatientRelation(5,9);
    }

    public static void testAddHealthData() {
        ArrayList<HealthData> healthDataList = new ArrayList<>();
        healthDataList.add(new HealthData(1, 2, 145, 1.8, 7000, 120, "2024-01-11"));
        healthDataList.add(new HealthData(2, 2, 115, 1.8, 14000, 76, "2024-04-12"));
        healthDataList.add(new HealthData(3, 3, 90, 1.67, 11008, 180, "2024-01-02"));
        healthDataList.add(new HealthData(4, 4, 89, 1.76, 21000, 70, "2023-06-01"));
        healthDataList.add(new HealthData(5, 6, 100, 1.9, 10001, 53, "2024-04-12"));
        healthDataList.add(new HealthData(6, 7, 120, 1.9, 10001, 53, "2024-04-12"));
        healthDataList.add(new HealthData(7, 8, 195, 4.3, 143001, 76, "1957-01-04"));

        for (HealthData healthData : healthDataList) {
            boolean success = healthDataDao.createHealthData(healthData);
            if (success) {
                System.out.println("Health data added successfully: " + healthData);
            } else {
                System.out.println("Failed to add health data: " + healthData);
            }
        }
    }

    public static void testGenerateRecommendations(int userId) {
        HealthData latestHealthData = healthDataDao.getLatestHealthDataForUser(userId);
        if (latestHealthData != null) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            RecommendationDao recommendationDao = new RecommendationDao(databaseConnection.getCon());
            RecommendationSystem recommendationSystem = new RecommendationSystem(recommendationDao);
            List<String> recommendations = recommendationSystem.generateRecommendations(latestHealthData);
            System.out.println("Recommendations based on latest health data:");
            for (String recommendation : recommendations) {
                System.out.println(recommendation);
            }
        } else {
            System.out.println("No health data found for user.");
        }
    }

    public static void testAddMedicineReminder(int userId, String medicineName, String dosage, String schedule, String startDate, String endDate) {
        MedicineReminder reminder = new MedicineReminder(1, userId, medicineName, dosage, schedule, startDate, endDate);
        medicineReminderManager.addMedicineReminder(reminder);
        System.out.println("Medicine reminder added successfully.");
    }

    public static void testGetRemindersForUser(int userId) {
        List<MedicineReminder> reminders = medicineReminderManager.getMedicineRemindersForUser(userId);
        System.out.println("Medicine reminders for user:");
        for (MedicineReminder reminder : reminders) {
            System.out.println(reminder.getMedicineName() + " - " + reminder.getDosage() + " - " + reminder.getSchedule());
        }
    }

    public static void testGetDueRemindersForUser(int userId) {
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueMedicineReminders(userId);
        System.out.println("Due medicine reminders for user:");
        for (MedicineReminder reminder : dueReminders) {
            System.out.println(reminder.getMedicineName() + " - " + reminder.getDosage() + " - " + reminder.getSchedule());
        }
    }

    public static void testDoctorPortal(int doctorId) {
        // Test get doctor by ID
        Doctor doctor = doctorPortalDao.getDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
        } else {
            System.out.println("Doctor not found.");
        }

        // Test get patients by doctor ID
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
        System.out.println("Patients for doctor:");
        for (User patient : patients) {
            System.out.println(patient.getFirstName() + " " + patient.getLastName());
        }

        // Test get health data by patient ID
        if (!patients.isEmpty()) {
            int patientId = patients.get(0).getId(); 
            List<HealthData> healthDataList = doctorPortalDao.getHealthDataByPatientId(patientId);
            System.out.println("Health data for patient:");
            for (HealthData healthData : healthDataList) {
                System.out.println("Date: " + healthData.getDate() + ", Weight: " + healthData.getWeight() + ", Heart Rate: " + healthData.getHeartRate());
            }
        } else {
            System.out.println("No patients found for doctor.");
        }
    }
}
