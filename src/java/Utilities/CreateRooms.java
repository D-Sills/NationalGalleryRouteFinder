package Utilities;

import Model.Room;
import application.Main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateRooms {
		public static List<Room> rooms = new ArrayList<>();

		public static void Create() {
				Room room23 = new Room(23, "Dutch portraiture", Main.class.getResource("/images/roomImages/23_img.png"));
				Room room25 = new Room(25, "A New Art For A New Nation (2)", Main.class.getResource("/images/roomImages/25_img.png"));
				Room room29 = new Room(29, "French Painting 1600–1700", Main.class.getResource("/images/roomImages/29_img.png"));
				rooms.add(room23);
				rooms.add(room25);
				rooms.add(room29);
				//onActionSave();
		}

		public static void setRooms(List<Room> rooms) {
				CreateRooms.rooms = rooms;
		}

		/**
		 * Uses the XStream library to save the system data to a .xml file in the project
		 */
		public static void onActionSave() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation popup
				alert.setHeaderText("Are you sure you want to save all data to VaccinationSystem.xml?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.orElse(null ) == ButtonType.OK) { //if ok is selected continue, else do nothing
						XStream xstream = new XStream(new DomDriver()); //initialise the xstream object
						ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("RoomData.xml")); //initialise an objectoutputsteam to a specific file
						out.writeObject(rooms); //write out the objects you want saved
						out.close();

						Alerts.genericInfo("Saved to VaccinationSystem.xml successful!");
				}
		}

		/**
		 * Uses the XStream library to load the system data from a .xml file in the project
		 */
		@SuppressWarnings("unchecked")
		public void onActionLoad() throws Exception {
				File xml = new File("VaccinationSystem.xml");
				if(xml.isFile()) {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setHeaderText("Are you sure you want to load all data from VaccinationSystem.xml?");
						Optional<ButtonType> result = alert.showAndWait();
						if(result.orElse(null ) == ButtonType.OK) {
								XStream xstream = new XStream(new DomDriver());
								ObjectInputStream is = xstream.createObjectInputStream(new FileReader("RoomData.xml"));  //initialise an objectoutputsteam from a specific file
								setRooms((List<Room>) is.readObject()); //tell it what object to assign values to
								is.close();

								Alerts.genericInfo("Loaded from VaccinationSystem.xml successful!");
						}
				} else {
						Alerts.genericInfo("Please save some data first!");
				}

		}


		/**
		 * Completely resets all data in the System
		 */
		public void onActionReset() {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Are you sure you want to reset all data in the system?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.orElse(null ) == ButtonType.OK) {
						rooms.clear(); //clear the two main lists to wipe all data

						Alerts.genericInfo("System reset successful!");
				}
		}
}
