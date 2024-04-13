import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HealthDataDao {
    private Connection connection;

    // Constructor to initialize the connection:
    public HealthDataDao(Connection connection) {
        this.connection = connection;
    }

    // Method to create health data:
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

    // Method to get health data by ID:
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

    // Method to get health data by User ID:
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

    // Method to get the latest health data for a user:
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


    // Method to update health data:
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

    // Method to delete health data:
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

    // Method to convert a health_data entry form the database into healthData object:
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
