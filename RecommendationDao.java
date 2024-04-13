import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Data Access Object (DAO) for managing recommendations in the database.
*/
public class RecommendationDao {
    private Connection connection;

    /**
     * Constructs a RecommendationDao with the specified database connection.
     *
     * @param connection The database connection.
    */
    public RecommendationDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves recommendations for a user in the database.
     *
     * @param userId         The ID of the user.
     * @param recommendations The list of recommendations to save.
    */
    public void saveRecommendations(int userId, List<String> recommendations) {
        String sql = "INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String recommendation : recommendations) {
                statement.setInt(1, userId);
                statement.setString(2, recommendation);
                statement.setDate(3, DateUtils.stringToSqlDate(LocalDate.now().toString()));
                statement.executeUpdate();
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all recommendations for a user from the database.
     *
     * @param userId The ID of the user.
     * @return A list of recommendations for the user.
    */
    public List<String> getRecommendationsByUserId(int userId) {
        List<String> recommendations = new ArrayList<>();
        String sql = "SELECT recommendation_text FROM recommendations WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recommendations.add(resultSet.getString("recommendation_text"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendations;
    }

    /**
     * Deletes all recommendations for a user from the database.
     *
     * @param userId The ID of the user.
     * @return true if the recommendations were successfully deleted, otherwise false.
    */
    public boolean deleteRecommendationsByUserId(int userId) {
        String sql = "DELETE FROM recommendations WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
