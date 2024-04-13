import java.util.ArrayList;
import java.util.List;

/**
 * A recommendation system that generates health recommendations based on provided health data.
 */
public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;
    private static final double BMI_UNDERWEIGHT = 18.5;
    private static final double BMI_OVERWEIGHT = 25.0;
    private static final double BMI_OBESE = 30.0;

    private RecommendationDao recommendationDao;

    /**
     * Constructs a RecommendationSystem with the specified RecommendationDao.
     *
     * @param recommendationDao The RecommendationDao to use for storing recommendations.
    */
    public RecommendationSystem(RecommendationDao recommendationDao) {
        this.recommendationDao = recommendationDao;
    }

    /**
     * Generates health recommendations based on the provided health data.
     *
     * @param healthData The health data used to generate recommendations.
     * @return A list of generated recommendations.
    */
    public List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

        // Analyze heart rate:
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        } else if (heartRate > MAX_HEART_RATE) {
            recommendations.add("Your heart rate is above the recommended range. " +
                    "If this is your resting heart rate you should consult your physician.");
        }

        // Analyze steps:
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }

        // Analyze BMI:
        double bodyMassIndex = healthData.getBMI();
        if (bodyMassIndex > BMI_OVERWEIGHT && bodyMassIndex < BMI_OBESE) {
            recommendations.add("Your body mass index (BMI) is above the healthy recommended range (18.5 - 25.0). " +
                    "You should try to incorporate more physical activity into your daily routine.");
        } else if (bodyMassIndex >= BMI_OBESE) {
            recommendations.add("Your body mass index (BMI) is above the healthy recommended range (18.5 - 25.0). " +
                    "You should consider consulting your physician.");
        } else if (bodyMassIndex <= BMI_UNDERWEIGHT) {
            recommendations.add("Your body mass index (BMI) is below the healthy recommended range (18.5 - 25.0). " +
                    "You should consider a change in diet; your physician would be able to help with that.");
        }

        // Store recommendations in the database:
        recommendationDao.saveRecommendations(healthData.getUserId(), recommendations);

        return recommendations;
    }
}
