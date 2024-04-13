

import java.util.ArrayList;
import java.util.List;

/**
 * In this basic version of the
 * RecommendationSystem class, complete the generateRecommendations to take a
 * HealthData object as input and generates recommendations based on the user's heart rate and step count.
 * You can also expand this class to include more health data analysis and generate more specific
 * recommendations based on the user's unique health profile
 * NOTE:
 * To integrate this class into your application, you'll need to pass the HealthData object to the generateRecommendations method
 * and store the generated recommendations in the recommendations table in the database.
 */

public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;
    private static final double BMI_UNDERWEIGHT = 18.5;
    private static final double BMI_OVERWEIGHT = 25.0;
    private static final double BMI_OBSESE = 30.0;

    public List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

        // Analyze heart rate
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
                recommendations.add("Your heart rate is lower than the recommended range. " +
                        "Consider increasing your physical activity to improve your cardiovascular health.");
        } else if (heartRate > MAX_HEART_RATE) {
                recommendations.add("Your heart rate is above the reccomended range." +
                        "If this is your resting heartrate you should consult your physician.");
        }

        // Analyze steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
                recommendations.add("You're not reaching the recommended daily step count. " +
                        "Try to incorporate more walking or other physical activities into your daily routine.");
        }

        // Analyze BMI:
        double bodyMassIndex = healthData.getBMI();
        if (bodyMassIndex > BMI_OVERWEIGHT && bodyMassIndex < BMI_OBSESE) {
                recommendations.add("Your body mass index (BMI) is above the healthy reccomended range (18.5 - 25.0)." +
                        "You should try to incorperate more physical activity into your daily routine.");
        } else if (bodyMassIndex >= BMI_OBSESE) {
                recommendations.add("Your body mass index (BMI) is above the healthy reccomened range (18.5 - 25.0)." +
                        "You should consider consulting your physician");
        } else if (bodyMassIndex <= BMI_UNDERWEIGHT) {
                recommendations.add("Your body mass index (BMI) is bellow the healthy reccomdned range (18.5 - 25.0)." +
                "You should consider a change in diet, your physician would be able to help with that.");
        }

        return recommendations;
    }
}
