/**
 * Represents a Doctor, which is a type of User with additional attributes such as medical license number and specialization.
 * Inherits properties and methods from the User class.
*/
public class Doctor extends User {

    private String medicalLicenseNumber;
    private String specialization;

    /**
     * Constructs a Doctor with the specified attributes.
     * 
     * @param id The unique identifier of the doctor.
     * @param firstName The first name of the doctor.
     * @param lastName The last name of the doctor.
     * @param email The email address of the doctor.
     * @param password The password of the doctor.
     * @param isDoctor Indicates if the user is a doctor or not.
     * @param medicalLicenseNumber The medical license number of the doctor.
     * @param specialization The specialization of the doctor.
    */
    public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password, isDoctor);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    /**
     * Gets the medical license number of the doctor.
     * 
     * @return The medical license number.
    */
    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    /**
     * Gets the specialization of the doctor.
     * 
     * @return The specialization.
    */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the medical license number of the doctor.
     * 
     * @param medicalLicenseNumber The medical license number to set.
    */
    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    /**
     * Sets the specialization of the doctor.
     * 
     * @param specialization The specialization to set.
    */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
