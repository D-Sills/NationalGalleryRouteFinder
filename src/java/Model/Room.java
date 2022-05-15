package Model;

import java.net.URL;

/**
 * @author Darren Sills & Gedvydas Jucius
 * Room class used to store all data related to each room
 */
public class Room {
    private int number;
    private String description;
    private URL image;

    public Room(int number, String description, URL image) {
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

    public URL getImage() {
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

    public void setImage(URL image) {
        this.image = image;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return "room Number: " +  getNumber() + " - " + getDescription() + "\n";
    }
}
