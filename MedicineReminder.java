/**
 * Represents a medicine reminder for a user.
*/
public class MedicineReminder {
    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private String startDate;
    private String endDate;

    /**
     * Constructs a MedicineReminder object with the specified attributes.
     *
     * @param id           The unique identifier of the medicine reminder.
     * @param userId       The ID of the user to whom the reminder belongs.
     * @param medicineName The name of the medicine.
     * @param dosage       The dosage information for the medicine.
     * @param schedule     The schedule for taking the medicine.
     * @param startDate    The start date for the reminder.
     * @param endDate      The end date for the reminder.
    */
    public MedicineReminder(int id, int userId, String medicineName, String dosage, String schedule, String startDate, String endDate) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Retrieves the unique identifier of the medicine reminder.
     *
     * @return The ID of the medicine reminder.
    */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the ID of the user to whom the reminder belongs.
     *
     * @return The user ID.
    */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the name of the medicine.
     *
     * @return The medicine name.
    */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Retrieves the dosage information for the medicine.
     *
     * @return The dosage information.
    */
    public String getDosage() {
        return dosage;
    }

    /**
     * Retrieves the schedule for taking the medicine.
     *
     * @return The medicine schedule.
    */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Retrieves the start date for the reminder.
     *
     * @return The start date.
    */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Retrieves the end date for the reminder.
     *
     * @return The end date.
    */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the unique identifier of the medicine reminder.
     *
     * @param id The medicine reminder ID.
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the ID of the user to whom the reminder belongs.
     *
     * @param userId The user ID.
    */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param medicineName The medicine name.
    */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Sets the dosage information for the medicine.
     *
     * @param dosage The dosage information.
    */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Sets the schedule for taking the medicine.
     *
     * @param schedule The medicine schedule.
    */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Sets the start date for the reminder.
     *
     * @param startDate The start date.
    */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date for the reminder.
     *
     * @param endDate The end date.
    */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
