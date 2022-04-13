package controller;

import Model.Room;
import Utilities.Alerts;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * @author Darren Sills
 * MainMenu class which shows the MainMenu.Fxml screen.
 * Responsible for swapping between the main scenes and also for saving and loading data via xstream.
 */
public class GalleryViewerController implements Initializable {
    @FXML
    private ImageView mapImage;
    @FXML
    private TreeView<String> roomTree;
    TreeItem<String> root = new TreeItem<>();
    TreeItem<String> favoured = new TreeItem<>();
    TreeItem<String> ignored = new TreeItem<>();
    @FXML
    private VBox roomLabel;
    @FXML
    private ImageView roomImage;
    @FXML
    private Label roomNo;
    @FXML
    private Label roomName;
    @FXML
    private Pane rectangleContainer;


    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return true;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }
    private List<Node> rectangles = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
    SingleSelectionModel<Tab> selectionModel;
    private Color[] colors;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    int[][] pixels;
    FileChooser fileChooser = new FileChooser();
    Image chosenImage;
    Stage stage;
    double h ;
    double h2 ;
    double x;
    double y;
    Point2D point2D;
    boolean once = false;
    int colourMode = 1;
    TextInputDialog td;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        Room room = new Room(0, "hi", null);
        Room room1 = new Room(1, "hi2", null);
        Room room2 = new Room(2, "bye", null);
        rooms.add(room);
        rooms.add(room1);
        rooms.add(room2);

        roomLabel.setVisible(false);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.jpeg")
        );
        try {
            onActionLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
        roomTree.setShowRoot(false);
        roomTree.setRoot(root);
        root.getChildren().add(favoured);
        root.getChildren().add(ignored);
        favoured.setValue("Favoured Rooms");
        ignored.setValue("Ignored Rooms");
        for (Room room3: rooms) {
            TreeItem<String> whatever = new TreeItem<>();
            whatever.setValue(room3.getDescription());
            favoured.getChildren().add(whatever);
        }
        rectangles.addAll(rectangleContainer.getChildren());
        for (Node node : rectangles) {
            addLisenters(node);
        }
        addLisenters(rectangleContainer);
        rectangleContainer.setOnMouseMoved(mouseEvent -> {
            point2D = new Point2D(mouseEvent.getX(),mouseEvent.getY());
            x = mouseEvent.getX() - 363;
            y = mouseEvent.getY() - 170;
            roomLabel.setTranslateY(y-100);
            roomLabel.setTranslateX(x);
        });
        System.out.println(rectangles.get(0).getId());
        createGraph();
    }

    private void createGraph() {
        Image map = mapImage.getImage();
        WritableImage writableImage = new WritableImage(map.getPixelReader(), (int) map.getWidth(), (int) map.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        pixels = new int[(int) (int) map.getWidth()][(int) map.getHeight()];
        //new graph
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Color c = pixelReader.getColor(x, y);
                if (c.getBrightness()==0) {
                    pixels[x][y] = 1;

                } else {
                    pixels[x][y] = 0;

                }
            }
        }
    }

    private void addLisenters(Node rectangle) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() >= 2) {
                if (Objects.equals(rectangle.getId(), "room29")) {
                    favoured.getChildren().removeIf(treeItem -> treeItem.getValue().equals("room29"));
                    ignored.getChildren().removeIf(treeItem -> treeItem.getValue().equals("room29"));
                }
            }
            if(mouseEvent.getClickCount()  == 1 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (Objects.equals(rectangle.getId(), "room29")) {
                    TreeItem<String> treeItem = new TreeItem<>();
                    treeItem.setValue("room29");
                    for (TreeItem<String> treeItem1 : favoured.getChildren()) {
                        if (treeItem1.getValue().equals("room29")) //add warning?
                            return;
                    }
                    favoured.getChildren().add(treeItem);
                }
            }
            if(mouseEvent.getClickCount()  == 1 && mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (Objects.equals(rectangle.getId(), "room29")) {
                    TreeItem<String> treeItem = new TreeItem<>();
                    treeItem.setValue("room29");
                    for (TreeItem<String> treeItem1 : ignored.getChildren()) {
                        if (treeItem1.getValue().equals("room29"))
                            return;
                    }
                    ignored.getChildren().add(treeItem);
                }
            }
        });

        rectangle.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (Objects.equals(rectangle.getId(), "room29")) {
                        Room cock = rooms.get(0);
                        rectangle.getScene().setCursor(Cursor.HAND);
                        roomName.setText("Number " + cock.getNumber());
                        roomLabel.setVisible(true);
                    }
                    if (Objects.equals(rectangle.getId(), "room15")) {
                        roomName.setText("Number " + rooms.get(1).getNumber());
                        roomLabel.setVisible(true);
                    }
                    } else {
                    roomLabel.setVisible(false);
                    rectangle.getScene().setCursor(Cursor.DEFAULT);
                }
            //
            //System.out.println(point2D);
           // for (Node node : rectangles) {
            //    if (node.contains(point2D)) {
            //        System.out.println("poop");
            //        roomLabel.setTranslateX(click.getX());
            //        roomLabel.setTranslateY(click.getY());
            //        roomLabel.setVisible(true);
            //    }
           // }


        });
    }

    //---------------------------------------------------------------//
    //Persistence                                                    //
    //---------------------------------------------------------------//
    /**
     * Uses the XStream library to save the system data to a .xml file in the project
     */
    @FXML
    public void onActionSave() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //confirmation popup
        alert.setHeaderText("Are you sure you want to save all data to VaccinationSystem.xml?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.orElse(null ) == ButtonType.OK) { //if ok is selected continue, else do nothing
            XStream xstream = new XStream(new DomDriver()); //initialise the xstream object
            ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("VaccinationSystem.xml")); //initialise an objectoutputsteam to a specific file
            out.writeObject(rooms); //write out the objects you want saved
            out.close();

            Alerts.genericInfo("Saved to VaccinationSystem.xml successful!");
        }
    }

    /**
     * Uses the XStream library to load the system data from a .xml file in the project
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void onActionLoad() throws Exception {
        File xml = new File("VaccinationSystem.xml");
        if(xml.isFile()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to load all data from VaccinationSystem.xml?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(null ) == ButtonType.OK) {
                XStream xstream = new XStream(new DomDriver());
                ObjectInputStream is = xstream.createObjectInputStream(new FileReader("VaccinationSystem.xml"));  //initialise an objectoutputsteam from a specific file
                setRooms((List<Room>) is.readObject()); //tell it what object to assign values to
                is.close();

                Alerts.genericInfo("Loaded from VaccinationSystem.xml successful!");
            }
        } else {
            Alerts.genericInfo("Please save some data first!");
        }

    }

    private void setRooms(List<Room> readObject) {
        this.rooms = readObject;
    }

    /**
     * Completely resets all data in the System
     */
    @FXML
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


