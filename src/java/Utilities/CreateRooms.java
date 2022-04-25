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

			Room room1 = new Room(1, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room2 = new Room(2, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room3 = new Room(3, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room4 = new Room(4, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room5 = new Room(5, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room6 = new Room(6, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room7 = new Room(7, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room8 = new Room(8, "The Credit Suisse Exhibition: Raphael", Main.class.getResource("/images/roomImages/2.png"));
			Room room9 = new Room(9, "Venice 1530-1600", Main.class.getResource("/images/roomImages/9.png"));
			Room room10 = new Room(10, "The sacred and profane in sixteenth-century Italian art", Main.class.getResource("/images/roomImages/10.png"));
			Room room11 = new Room(11, "Veronese's 'Four Allegories of Love'", Main.class.getResource("/images/roomImages/11.png"));
			Room room12 = new Room(12, "Northern Italian Portraiture 1510–1580", Main.class.getResource("/images/roomImages/12.png"));
			Room room13 = new Room(13, "Gussy", Main.class.getResource("/images/roomImages/12.png"));
			Room room14 = new Room(14, "The Netherlands", Main.class.getResource("/images/roomImages/14.png"));
			Room room15 = new Room(15, "", Main.class.getResource("/images/roomImages/15.png"));
			Room room16 = new Room(16, "Cranach the Elder, Huber and Seisenegger", Main.class.getResource("/images/roomImages/16.png"));
			Room room17 = new Room(17, "Altdorfer, Rottenhammer, Wertinger", Main.class.getResource("/images/roomImages/17.png"));
			Room room17a = new Room(172, "Pastel in the 18th and 19th centuries", Main.class.getResource("/images/roomImages/17a.png"));
			Room room18 = new Room(18, "Rubens", Main.class.getResource("/images/roomImages/Room18.png"));
			Room room19 = new Room(19, "Dutch landscapes", Main.class.getResource("/images/roomImages/Room19.png"));
			Room room20 = new Room(20, "A different view of Flanders", Main.class.getResource("/images/roomImages/Room20.png"));
			Room room21 = new Room(21, "Van Dyck", Main.class.getResource("/images/roomImages/Room21.png"));
			Room room22 = new Room(22, "Rembrandt", Main.class.getResource("/images/roomImages/Room22.png"));
			Room room23 = new Room(23, "Dutch portraiture", Main.class.getResource("/images/roomImages/23_img.png"));
			Room room24 = new Room(24, "International ambitions", Main.class.getResource("/images/roomImages/24.png"));
			Room room25 = new Room(25, "A New Art For A New Nation (2)", Main.class.getResource("/images/roomImages/25_img.png"));
			Room room26 = new Room(26, "Brueghel the Elder, van der Heyden, van de Velde", Main.class.getResource("/images/roomImages/26.png"));
			Room room27 = new Room(27, "de Hooch, Saenredam, Vermeer", Main.class.getResource("/images/roomImages/27.png"));
			Room room28 = new Room(28, "A New Art For A New Nation (1)", Main.class.getResource("/images/roomImages/28.png"));
			Room room29 = new Room(29, "French Painting 1600–1700", Main.class.getResource("/images/roomImages/29_img.png"));
			Room room30 = new Room(30, "Spain", Main.class.getResource("/images/roomImages/30.png"));
			Room room31 = new Room(31, "Room closed", Main.class.getResource("/images/roomImages/31.png"));
			Room room32 = new Room(32, "17th-century Italian paintings", Main.class.getResource("/images/roomImages/32.png"));
			Room room33 = new Room(33, "David, Fragonard and Vernet", Main.class.getResource("/images/roomImages/33.png"));
			Room room34 = new Room(34, "Constable, Turner and Stubbs", Main.class.getResource("/images/roomImages/34.png"));
			Room room35 = new Room(35, "Hogarth and British Painting", Main.class.getResource("/images/roomImages/35.png"));
			Room room36 = new Room(36, "Claude and Turner", Main.class.getResource("/images/roomImages/36.png"));
			Room room37 = new Room(37, "The Grand Tour", Main.class.getResource("/images/roomImages/37.png"));
			Room room38 = new Room(38, "Canaletto and Guardi", Main.class.getResource("/images/roomImages/38.png"));
			Room room39 = new Room(39, "Canaletto, Pittoni, and Tiepolo", Main.class.getResource("/images/roomImages/39.png"));
			Room room40 = new Room(40, "18th-century painting in Europe", Main.class.getResource("/images/roomImages/40.png"));
			Room room41 = new Room(41, "Cézanne, Monet, Renoir", Main.class.getResource("/images/roomImages/41.png"));
			Room room42 = new Room(42, "Room closed", Main.class.getResource("/images/roomImages/42.png"));
			Room room43 = new Room(43, "Gauguin, Seurat, Van Gogh", Main.class.getResource("/images/roomImages/43.png"));
			Room room44 = new Room(44, "Cézanne, Degas, Monet", Main.class.getResource("/images/roomImages/44.png"));
			Room room45 = new Room(45, "Constable, Corot and Ingres", Main.class.getResource("/images/roomImages/45.png"));
			Room room46 = new Room(46, "Gainsborough's Blue Boy", Main.class.getResource("/images/roomImages/46.png"));
			Room room47 = new Room(47, "Bussy", Main.class.getResource("/images/roomImages/46.png"));
			Room room48 = new Room(48, "Bussy", Main.class.getResource("/images/roomImages/46.png"));
			Room room49 = new Room(49, "Bussy", Main.class.getResource("/images/roomImages/46.png"));
			Room room50 = new Room(50, "Bussy", Main.class.getResource("/images/roomImages/46.png"));
			Room room51 = new Room(51, "Italy, 1250–1350", Main.class.getResource("/images/roomImages/51.png"));
			Room room51a = new Room(512, "Fra Angelico, friar-painter of San Domenico", Main.class.getResource("/images/roomImages/51a.png"));
			Room room52 = new Room(52, "Siena, 1300–1450", Main.class.getResource("/images/roomImages/52.png"));
			Room room53 = new Room(53, "Florence and Beyond, 1440–1470", Main.class.getResource("/images/roomImages/53.png"));
			Room room54 = new Room(54, "Mantegna and Crivelli", Main.class.getResource("/images/roomImages/54.png"));
			Room room55 = new Room(55, "Venice and the Veneto, 1460–1510", Main.class.getResource("/images/roomImages/55.png"));
			Room room56 = new Room(56, "Venice after 1500", Main.class.getResource("/images/roomImages/56.png"));
			Room room57 = new Room(57, "Ferrara and Bologna", Main.class.getResource("/images/roomImages/57.png"));
			Room room58 = new Room(58, "Botticelli and Filippino Lippi", Main.class.getResource("/images/roomImages/58.png"));
			Room room59 = new Room(59, "Florence, 1460–1500", Main.class.getResource("/images/roomImages/59.png"));
			Room room60 = new Room(60, "Florentine Altarpieces about 1350 – about 1460", Main.class.getResource("/images/roomImages/60.png"));
			Room room61 = new Room(61, "Central Italy, 1480–1520", Main.class.getResource("/images/roomImages/61.png"));
			Room room62 = new Room(62, "Cologne and north-western Germany", Main.class.getResource("/images/roomImages/62.png"));
			Room room63 = new Room(63, "15th-century Netherlandish painting", Main.class.getResource("/images/roomImages/63.png"));
			Room room64 = new Room(64, "Beyond the Netherlands", Main.class.getResource("/images/roomImages/64.png"));
			Room room65 = new Room(65, "Southern Germany and Austria", Main.class.getResource("/images/roomImages/65.png"));
			Room room66 = new Room(66, "Leonardo da Vinci at the National Gallery", Main.class.getResource("/images/roomImages/66.png"));
			Room room68 = new Room(68, "Room Closed", Main.class.getResource("/images/roomImages/sunleyroom.png"));
			Room room69 = new Room(69, "Central Hall", Main.class.getResource("/images/roomImages/CentralHall.png"));
			Room room70 = new Room(70, "Main Vestibule", Main.class.getResource("/images/roomImages/MainVesti.png"));


			rooms.add(room1);
			rooms.add(room2);
			rooms.add(room3);
			rooms.add(room4);
			rooms.add(room5);
			rooms.add(room6);
			rooms.add(room7);
			rooms.add(room8);
			rooms.add(room9);
			rooms.add(room10);
			rooms.add(room11);
			rooms.add(room12);
			rooms.add(room13);
			rooms.add(room14);
			rooms.add(room15);
			rooms.add(room16);
			rooms.add(room17);
			rooms.add(room17a);
			rooms.add(room18);
			rooms.add(room19);
			rooms.add(room20);
			rooms.add(room21);
			rooms.add(room22);
			rooms.add(room23);
			rooms.add(room24);
			rooms.add(room25);
			rooms.add(room26);
			rooms.add(room27);
			rooms.add(room28);
			rooms.add(room29);
			rooms.add(room30);
			rooms.add(room31);
			rooms.add(room32);
			rooms.add(room33);
			rooms.add(room34);
			rooms.add(room35);
			rooms.add(room36);
			rooms.add(room37);
			rooms.add(room38);
			rooms.add(room39);
			rooms.add(room40);
			rooms.add(room41);
			rooms.add(room42);
			rooms.add(room43);
			rooms.add(room44);
			rooms.add(room45);
			rooms.add(room46);
			rooms.add(room47);
			rooms.add(room48);
			rooms.add(room49);
			rooms.add(room50);
			rooms.add(room51);
			rooms.add(room51a);
			rooms.add(room52);
			rooms.add(room53);
			rooms.add(room54);
			rooms.add(room55);
			rooms.add(room56);
			rooms.add(room57);
			rooms.add(room58);
			rooms.add(room59);
			rooms.add(room60);
			rooms.add(room61);
			rooms.add(room62);
			rooms.add(room63);
			rooms.add(room64);
			rooms.add(room65);
			rooms.add(room66);
			rooms.add(room68);
			rooms.add(room69);
			rooms.add(room70);
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
