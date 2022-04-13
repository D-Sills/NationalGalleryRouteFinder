package Model;

import java.time.LocalDate;

/**
 * @author Darren Sills
 * Patient class used to store all data related to each patient, as well as the list of vaccination records belonging to each person.
 */
public class Exhibit {
    private String name;
    private String patientIdentifier;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String accessibility;
    //public CustomList<VaccinationRecord> vaccinationRecords;

    /**
     * Constructor for objects of class Patient
     * @param name Patient's name
     * @param patientIdentifier Patient's PPSN
     * @param dateOfBirth Patient's date of birth
     * @param address Patient's address
     * @param phone Patient's phone number
     * @param email Patient's email
     * @param accessibility Patient's accessibility information
     */
    public Exhibit(String name, String patientIdentifier, LocalDate dateOfBirth, String address, String phone, String email, String accessibility) {
        this.name = name;
        this.patientIdentifier = patientIdentifier;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.accessibility = accessibility;
        //vaccinationRecords = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the name of the Patient
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the PPSN of the Patient
     */
    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    /**
     * Returns the birthdate of the Patient
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns the address of the Patient
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the phone number of the Patient
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the email of the Patient
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the accessibility information of the Patient
     */
    public String getAccessibility() {
        return accessibility;
    }

    /**
     * Returns the list of Vaccination Records for the Patient
     */
   // public CustomList<VaccinationRecord> getVaccinationRecords() {
    //    return vaccinationRecords;
   // }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Patient's name to the value passed
     * @param name Patient's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the Patient's PPSN to the value passed
     * @param patientIdentifier Patient's PPSN
     */
    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    /**
     * Updates the Patient's birthdate to the value passed
     * @param dateOfBirth Patient's date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Updates the Patient's address to the value passed
     * @param address Patient's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Updates the Patient's phone number to the value passed
     * @param phone Patient's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Updates the Patient's email to the value passed
     * @param email Patient's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Updates the Patient's accessibility information to the value passed
     * @param accessibility Patient's accessibility information
     */
    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * Updates the list of Vaccination Records for the Patient to the value passed
     * @param vaccinationRecords The list of Vaccination Records for the Patient
     */
    //public void setVaccinationRecords(CustomList<VaccinationRecord> vaccinationRecords) {
    //    this.vaccinationRecords = vaccinationRecords;
   // }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return  getName();
    }
}
