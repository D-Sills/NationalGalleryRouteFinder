package Model;

import javafx.scene.image.Image;


/**
 * @author Darren Sills
 * Patient class used to store all data related to each patient, as well as the list of vaccination records belonging to each person.
 */
public class Room {
    private int number;
    private String description;
    private Image image;

    public Room(int number, String description, Image image) {
        this.number = number;
        this.description = description;
        this.image = image;
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return  getNumber() + " - " + getDescription() + "\n";
    }
}
