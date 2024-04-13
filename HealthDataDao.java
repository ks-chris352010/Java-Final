import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object (DAO) for managing health data records in the database.
*/
public class HealthDataDao {
    private Connection connection;

    /**
     * Constructs a HealthDataDao with the specified database connection.
     *
     * @param connection The database connection to use.
    */
    public HealthDataDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new health data record in the database.
     *
     * @param healthData The HealthData object to be inserted.
     * @return True if the operation was successful, false otherwise.
    */
    public boolean createHealthData(HealthData healthData) {
        String sql = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setDate(6, DateUtils.stringToSqlDate(healthData.getDate()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a health data record from the database by its ID.
     *
     * @param id The ID of the health data record to retrieve.
     * @return The HealthData object corresponding to the ID, or null if not found.
    */
    public HealthData getHealthDataById(int id) {
        HealthData healthData = null;
        String sql = "SELECT * FROM health_data WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    healthData = extractHealthDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthData;
    }

    /**
     * Retrieves all health data records associated with a specific user from the database.
     *
     * @param userId The ID of the user whose health data is to be retrieved.
     * @return A list of HealthData objects associated with the user.
    */
    public List<HealthData> getHealthDataByUserId(int userId) {
        List<HealthData> healthDataList = new ArrayList<>();
        String sql = "SELECT * FROM health_data WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    healthDataList.add(extractHealthDataFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthDataList;
    }

    /**
     * Retrieves the latest health data record for a specific user from the database.
     *
     * @param userId The ID of the user whose latest health data is to be retrieved.
     * @return The latest HealthData object associated with the user, or null if not found.
    */
    public HealthData getLatestHealthDataForUser(int userId) {
        HealthData latestHealthData = null;
        String sql = "SELECT * FROM health_data WHERE user_id = ? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    latestHealthData = extractHealthDataFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return latestHealthData;
    }


    /**
     * Updates an existing health data record in the database.
     *
     * @param healthData The HealthData object containing the updated information.
     * @return True if the operation was successful, false otherwise.
    */
    public boolean updateHealthData(HealthData healthData) {
        String sql = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, healthData.getWeight());
            statement.setDouble(2, healthData.getHeight());
            statement.setInt(3, healthData.getSteps());
            statement.setInt(4, healthData.getHeartRate());
            statement.setDate(5, DateUtils.stringToSqlDate(healthData.getDate()));
            statement.setInt(6, healthData.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a health data record from the database.
     *
     * @param id The ID of the health data record to be deleted.
     * @return True if the operation was successful, false otherwise.
    */
    public boolean deleteHealthData(int id) {
        String sql = "DELETE FROM health_data WHERE id = ?";
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
     * Helper method to convert a ResultSet row into a HealthData object.
     *
     * @param resultSet The ResultSet containing the health data information.
     * @return A HealthData object created from the ResultSet data.
     * @throws SQLException If an SQL exception occurs while accessing the ResultSet.
    */
    private HealthData extractHealthDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new HealthData(
            resultSet.getInt("id"),
            resultSet.getInt("user_id"),
            resultSet.getDouble("weight"),
            resultSet.getDouble("height"),
            resultSet.getInt("steps"),
            resultSet.getInt("heart_rate"),
            DateUtils.dateToString(resultSet.getDate("date"))
        );
    }
}
