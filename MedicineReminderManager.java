import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Manages medicine reminders stored in the database.
*/
public class MedicineReminderManager {
    private Connection connection;

    /**
     * Constructs a MedicineReminderManager with the specified database connection.
     *
     * @param connection The database connection to use.
    */
    public MedicineReminderManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new medicine reminder to the database.
     *
     * @param reminder The MedicineReminder object to be added.
    */
    public void addMedicineReminder(MedicineReminder reminder) {
        String sql = "INSERT INTO medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reminder.getUserId());
            statement.setString(2, reminder.getMedicineName());
            statement.setString(3, reminder.getDosage());
            statement.setString(4, reminder.getSchedule());
            statement.setDate(5, Date.valueOf(reminder.getStartDate()));
            statement.setDate(6, Date.valueOf(reminder.getEndDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all medicine reminders associated with a specific user from the database.
     *
     * @param userId The ID of the user whose medicine reminders are to be retrieved.
     * @return A list of MedicineReminder objects associated with the user.
    */
    public List<MedicineReminder> getMedicineRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        String sql = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userReminders.add(extractMedicineReminderFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userReminders;
    }

    /**
     * Retrieves medicine reminders that are due for a specific user from the database.
     *
     * @param userId The ID of the user for whom due medicine reminders are to be retrieved.
     * @return A list of MedicineReminder objects that are due for the user.
    */
    public List<MedicineReminder> getDueMedicineReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDate now = LocalDate.now();
        String sql = "SELECT * FROM medicine_reminders WHERE user_id = ? AND start_date <= ? AND end_date >= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(now));
            statement.setDate(3, Date.valueOf(now));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dueReminders.add(extractMedicineReminderFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dueReminders;
    }

    /**
     * Helper method to convert a ResultSet row into a MedicineReminder object.
     *
     * @param resultSet The ResultSet containing the medicine reminder information.
     * @return A MedicineReminder object created from the ResultSet data.
     * @throws SQLException If an SQL exception occurs while accessing the ResultSet.
    */
    private MedicineReminder extractMedicineReminderFromResultSet(ResultSet resultSet) throws SQLException {
        return new MedicineReminder(
            resultSet.getInt("id"),
            resultSet.getInt("user_id"),
            resultSet.getString("medicine_name"),
            resultSet.getString("dosage"),
            resultSet.getString("schedule"),
            DateUtils.sqlDateToString(resultSet.getDate("start_date")),
            DateUtils.sqlDateToString(resultSet.getDate("end_date"))
        );
    }
}
