/**
 * Represents health data records for a user, including weight, height, steps, heart rate, and date.
*/
public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private String date;

    /**
     * Constructs a HealthData object with the specified parameters.
     *
     * @param id        The unique identifier for the health data record.
     * @param userId    The identifier of the user associated with this health data record.
     * @param weight    The weight of the user at the time of recording.
     * @param height    The height of the user at the time of recording.
     * @param steps     The number of steps taken by the user.
     * @param heartRate The heart rate of the user.
     * @param date      The date of the health data recording.
    */
    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, String date) {
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    /**
     * Retrieves the unique identifier for the health data record.
     *
     * @return The identifier of the health data record.
    */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the identifier of the user associated with this health data record.
     *
     * @return The user identifier.
    */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the weight of the user at the time of recording.
     *
     * @return The weight of the user.
    */
    public double getWeight() {
        return weight;
    }

    /**
     * Retrieves the height of the user at the time of recording.
     *
     * @return The height of the user.
    */
    public double getHeight() {
        return height;
    }

    /**
     * Retrieves the number of steps taken by the user.
     *
     * @return The number of steps.
    */
    public int getSteps() {
        return steps;
    }

    /**
     * Retrieves the heart rate of the user.
     *
     * @return The heart rate.
    */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * Retrieves the date of the health data recording.
     *
     * @return The date of the recording.
    */
    public String getDate() {
        return date;
    }

    /**
     * Calculates the Body Mass Index (BMI) based on the weight and height.
     *
     * @return The calculated BMI.
    */
    public double getBMI() {
        return weight / height;
    }

    /**
     * Sets the unique identifier for the health data record.
     *
     * @param id The identifier to set.
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the identifier of the user associated with this health data record.
     *
     * @param userId The user identifier to set.
    */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the weight of the user at the time of recording.
     *
     * @param weight The weight to set.
    */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Sets the height of the user at the time of recording.
     *
     * @param height The height to set.
    */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets the number of steps taken by the user.
     *
     * @param steps The number of steps to set.
    */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * Sets the heart rate of the user.
     *
     * @param heartRate The heart rate to set.
    */
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    /**
     * Sets the date of the health data recording.
     *
     * @param date The date to set.
    */
    public void setDate(String date) {
        this.date = date;
    }
}
