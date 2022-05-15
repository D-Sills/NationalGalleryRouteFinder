import Model.Room;
import Utilities.CreateRooms;
import application.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateRoomTest {

    private Room correctRoom;

    @BeforeEach
    void setUp() {
        //Validation: roomNumber(AnyNumber), roomDescription(to describe room), image (Any image for floor).
        correctRoom = new Room(2, "floor Gala", Main.class.getResource("/images/roomImages/2.png"));
    }

    @AfterEach
    void tearDown() {
        correctRoom = null;
    }

    @Nested
    class Getters {

        @Test
        void getNumber()
        {
            assertEquals(2, correctRoom.getNumber());
        }

        @Test
        void getDescription()
        {
            assertEquals("floor Gala", correctRoom.getDescription());
        }

        @Test
        void getImage()
        {
            assertEquals(Main.class.getResource("/images/roomImages/2.png"), correctRoom.getImage());
        }

    }

    @Nested
    class Setters {

        @Test
        void setNumber()
        {
            assertEquals(2, correctRoom.getNumber());
            correctRoom.setNumber(2);
            assertEquals(2, correctRoom.getNumber());
        }

        @Test
        void setDescription()
        {
            assertEquals("floor Gala", correctRoom.getDescription());
            correctRoom.setDescription("floor Gala");
            assertEquals("floor Gala", correctRoom.getDescription());
        }

        @Test
        void setImage()
        {
            assertEquals(Main.class.getResource("/images/roomImages/2.png"), correctRoom.getImage());
            correctRoom.setImage(Main.class.getResource("/images/roomImages/2.png"));
            assertEquals(Main.class.getResource("/images/roomImages/2.png"), correctRoom.getImage());
        }

        @Test
        void toStringReturnsCorrectString() {
            Room room = new Room(2,"floor Gala",Main.class.getResource("/images/roomImages/2.png"));
            String stringContents = room.toString();

            assertTrue(stringContents.contains("room Number: " + room.getNumber()));
            assertTrue(stringContents.contains(" - " + room.getDescription()));
        }

    }






}
