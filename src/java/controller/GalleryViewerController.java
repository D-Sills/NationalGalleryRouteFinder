package controller;

import Model.GraphNode;
import Model.Room;
import Utilities.Alerts;
import Utilities.CreateRooms;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import static Model.CustomGraph.*;
import static Utilities.CreateRooms.*;

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
    private Pane pane;
    @FXML
    private Pane pane2;
    @FXML
    private ImageView roomImage;
    @FXML
    private Label roomNo;
    @FXML
    private Label roomName;
    @FXML
    private Circle startPoint;
    @FXML
    private Circle endPoint;
    @FXML
    private Pane rectangleContainer;

    private final List<Node> rectangles = new ArrayList<>();
    private final List<GraphNode<Room>> nodes = new ArrayList<>();
    private final List<GraphNode<Room>> nodestovisit = new ArrayList<>();
    private final List<GraphNode<Room>> nodestoignore = new ArrayList<>();
    private final ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
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
    double x2;
    double y2;
    Point2D point2D;
    boolean once = false;
    int colourMode = 1;
    TextInputDialog td;
    boolean endPointSet;
    int start;
    int startForReal;
    int end;
    int endForReal;
    boolean startPointSet;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        CreateRooms.Create();
        /*GraphNode<Room> r1 = new GraphNode<>(rooms.get(0));
        GraphNode<Room> r2 = new GraphNode<>(rooms.get(1));
        GraphNode<Room> r3 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r4 = new GraphNode<>(rooms.get(3));
        GraphNode<Room> r5 = new GraphNode<>(rooms.get(4));
        GraphNode<Room> r6 = new GraphNode<>(rooms.get(5));
        GraphNode<Room> r7 = new GraphNode<>(rooms.get(6));
        GraphNode<Room> r8 = new GraphNode<>(rooms.get(7));
        GraphNode<Room> r9 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r10 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r11 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r12 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r13 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r14 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r15 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r16 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r17 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r18 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r19 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r20 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r21 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r22 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r23 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r24 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r25 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r26 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r27 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r28 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r29 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r30 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r31 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r32 = new GraphNode<>(rooms.get(2));
        GraphNode<Room> r3 = new GraphNode<>(rooms.get(2));*/

        for (Room room : rooms) {
            GraphNode<Room> roomGraphNode = new GraphNode<>(room);
            nodes.add(roomGraphNode);
        }



        nodes.get(0).connectToNodeUndirected(nodes.get(1), 1);
        nodes.get(1).connectToNodeUndirected(nodes.get(3), 1);
        nodes.get(1).connectToNodeUndirected(nodes.get(3), 1);
        nodes.get(3).connectToNodeUndirected(nodes.get(5), 1);
        nodes.get(3).connectToNodeUndirected(nodes.get(4), 1);
        nodes.get(5).connectToNodeUndirected(nodes.get(7), 1);
        nodes.get(4).connectToNodeUndirected(nodes.get(10), 1);
        nodes.get(10).connectToNodeUndirected(nodes.get(9), 1);
        nodes.get(10).connectToNodeUndirected(nodes.get(13), 1);
        nodes.get(10).connectToNodeUndirected(nodes.get(11), 1);
        nodes.get(9).connectToNodeUndirected(nodes.get(8), 1);

        //Blue Rooms
        nodes.get(51).connectToNodeUndirected(nodes.get(8), 1);
        nodes.get(51).connectToNodeUndirected(nodes.get(71), 1);
        nodes.get(51).connectToNodeUndirected(nodes.get(60), 1);
        nodes.get(51).connectToNodeUndirected(nodes.get(52), 1);
        nodes.get(52).connectToNodeUndirected(nodes.get(53), 1);
        nodes.get(53).connectToNodeUndirected(nodes.get(59), 1);
        nodes.get(53).connectToNodeUndirected(nodes.get(54), 1);
        nodes.get(54).connectToNodeUndirected(nodes.get(55), 1);
        nodes.get(55).connectToNodeUndirected(nodes.get(57), 1);
        nodes.get(55).connectToNodeUndirected(nodes.get(56), 1);
        nodes.get(57).connectToNodeUndirected(nodes.get(65), 1);
        nodes.get(57).connectToNodeUndirected(nodes.get(58), 1);
        nodes.get(58).connectToNodeUndirected(nodes.get(59), 1);
        nodes.get(59).connectToNodeUndirected(nodes.get(63), 1);
        nodes.get(59).connectToNodeUndirected(nodes.get(60), 1);
        nodes.get(60).connectToNodeUndirected(nodes.get(61), 1);
        nodes.get(61).connectToNodeUndirected(nodes.get(62), 1);
        nodes.get(62).connectToNodeUndirected(nodes.get(63), 1);
        nodes.get(63).connectToNodeUndirected(nodes.get(64), 1);
        nodes.get(64).connectToNodeUndirected(nodes.get(65), 1);
        nodes.get(65).connectToNodeUndirected(nodes.get(66), 1);

        //Yellow Rooms
        nodes.get(8).connectToNodeUndirected(nodes.get(14), 1);
        nodes.get(14).connectToNodeUndirected(nodes.get(15), 1);
        nodes.get(14).connectToNodeUndirected(nodes.get(16), 1);
        nodes.get(14).connectToNodeUndirected(nodes.get(72), 1);
        nodes.get(14).connectToNodeUndirected(nodes.get(29), 1);
        nodes.get(15).connectToNodeUndirected(nodes.get(16), 1);
        nodes.get(16).connectToNodeUndirected(nodes.get(17), 1);
        nodes.get(16).connectToNodeUndirected(nodes.get(17), 1);
        nodes.get(72).connectToNodeUndirected(nodes.get(26), 1);
        nodes.get(72).connectToNodeUndirected(nodes.get(18), 1);
        nodes.get(72).connectToNodeUndirected(nodes.get(18), 1);
        nodes.get(18).connectToNodeUndirected(nodes.get(19), 1);
        nodes.get(18).connectToNodeUndirected(nodes.get(24), 1);
        nodes.get(18).connectToNodeUndirected(nodes.get(22), 1);
        nodes.get(18).connectToNodeUndirected(nodes.get(21), 1);
        nodes.get(19).connectToNodeUndirected(nodes.get(20), 1);
        nodes.get(20).connectToNodeUndirected(nodes.get(21), 1);
        nodes.get(22).connectToNodeUndirected(nodes.get(23), 1);
        nodes.get(23).connectToNodeUndirected(nodes.get(24), 1);
        nodes.get(24).connectToNodeUndirected(nodes.get(25), 1);
        nodes.get(25).connectToNodeUndirected(nodes.get(28), 1);
        nodes.get(28).connectToNodeUndirected(nodes.get(27), 1);
        nodes.get(27).connectToNodeUndirected(nodes.get(26), 1);
        nodes.get(28).connectToNodeUndirected(nodes.get(29), 1);
        nodes.get(29).connectToNodeUndirected(nodes.get(13), 1);
        nodes.get(29).connectToNodeUndirected(nodes.get(30), 1);
        nodes.get(30).connectToNodeUndirected(nodes.get(31), 1);
        nodes.get(30).connectToNodeUndirected(nodes.get(32), 1);
        nodes.get(30).connectToNodeUndirected(nodes.get(67), 1);
        nodes.get(32).connectToNodeUndirected(nodes.get(37), 1);
        nodes.get(32).connectToNodeUndirected(nodes.get(33), 1);

        //Green Rooms
        nodes.get(33).connectToNodeUndirected(nodes.get(34), 1);
        nodes.get(34).connectToNodeUndirected(nodes.get(35), 1);
        nodes.get(34).connectToNodeUndirected(nodes.get(41), 1);
        nodes.get(35).connectToNodeUndirected(nodes.get(36), 1);
        nodes.get(36).connectToNodeUndirected(nodes.get(37), 1);
        nodes.get(36).connectToNodeUndirected(nodes.get(38), 1);
        nodes.get(36).connectToNodeUndirected(nodes.get(40), 1);
        nodes.get(38).connectToNodeUndirected(nodes.get(39), 1);
        nodes.get(39).connectToNodeUndirected(nodes.get(69), 1);
        nodes.get(40).connectToNodeUndirected(nodes.get(44), 1);
        nodes.get(41).connectToNodeUndirected(nodes.get(42), 1);
        nodes.get(42).connectToNodeUndirected(nodes.get(43), 1);
        nodes.get(43).connectToNodeUndirected(nodes.get(44), 1);
        nodes.get(44).connectToNodeUndirected(nodes.get(45), 1);
        nodes.get(45).connectToNodeUndirected(nodes.get(70), 1);

        //Central Rooms
        nodes.get(69).connectToNodeUndirected(nodes.get(68), 1);
        nodes.get(69).connectToNodeUndirected(nodes.get(11), 1);
        nodes.get(69).connectToNodeUndirected(nodes.get(70), 1);
        nodes.get(70).connectToNodeUndirected(nodes.get(46), 1);
        nodes.get(70).connectToNodeUndirected(nodes.get(0), 1);
        nodes.get(70).connectToNodeUndirected(nodes.get(1), 1);



        roomLabel.setVisible(false);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.jpeg")
        );
        roomTree.setShowRoot(false);
        roomTree.setRoot(root);
        root.getChildren().add(favoured);
        root.getChildren().add(ignored);
        favoured.setValue("Favoured Rooms");
        ignored.setValue("Ignored Rooms");
        rectangles.addAll(rectangleContainer.getChildren());
        for (Node node : rectangles) {
            String str = node.getId();
            // extract digits only from strings
            String numberOnly = str.replaceAll("[^0-9]", "");
            System.out.println(numberOnly);
            addLisenters(node);
        }
        addLisenters(rectangleContainer);
        rectangleContainer.setOnMouseMoved(mouseEvent -> {
            point2D = new Point2D(mouseEvent.getX(),mouseEvent.getY());
            x2 = mouseEvent.getX();
            y2 = mouseEvent.getY() + 7;
            x = mouseEvent.getX() - 363;
            y = mouseEvent.getY() - 170;
            roomLabel.setTranslateY(y-100);
            roomLabel.setTranslateX(x);
        });
    }

    private void addLisenters(Node rectangle) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() >= 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                for (Room room : rooms) {
                    String i = String.valueOf(room.getNumber());
                    if (rectangle.getId().contains(i)) {
                        start = room.getNumber();
                        if (start == endForReal) {
                            Alerts.genericWarning("Same");
                            return;
                        }
                        clearPaths();
                        startForReal = start;
                        startPoint.setTranslateX(x2);
                        startPoint.setTranslateY(y2);
                        startPoint.setVisible(true);
                        startPointSet = true;
                    }
                }
            }
            if (mouseEvent.getClickCount() >= 2 && mouseEvent.getButton() == MouseButton.SECONDARY) {
                for (Room room : rooms) {
                    String i = String.valueOf(room.getNumber());
                    if (rectangle.getId().contains(i)) {
                        end = room.getNumber();
                        if (end == startForReal) {
                            Alerts.genericWarning("Same");
                            return;
                        }
                        clearPaths();
                        endForReal = end;
                        endPoint.setTranslateX(x2);
                        endPoint.setTranslateY(y2);
                        endPoint.setVisible(true);
                        endPointSet = true;
                    }
                }
            }
            if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                for (Room room : rooms) {
                    String i = String.valueOf(room.getNumber());
                    if (rectangle.getId().contains(i)) {
                        TreeItem<String> treeItem = new TreeItem<>();
                        treeItem.setValue(room.getNumber() + " - " + room.getDescription());
                        for (TreeItem<String> treeItem1 : favoured.getChildren()) {
                            if (treeItem1.getValue().equals(room.getNumber() + " - " + room.getDescription()))
                                return;
                        }
                        favoured.getChildren().add(treeItem);
                    }
                }
            }
            if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.SECONDARY) {
                for (Room room : rooms) {
                    String i = String.valueOf(room.getNumber());
                    if (rectangle.getId().contains(i)) {
                        TreeItem<String> treeItem = new TreeItem<>();
                        treeItem.setValue(room.getNumber() + " - " + room.getDescription());
                        for (TreeItem<String> treeItem1 : ignored.getChildren()) {
                            if (treeItem1.getValue().equals(room.getNumber() + " - " + room.getDescription()))
                                return;
                        }
                        ignored.getChildren().add(treeItem);
                    }
                }
            }
        });

        rectangle.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                    for (Room room : rooms) {
                        String i = String.valueOf(room.getNumber());
                        if (rectangle.getId().contains(i)) {
                            rectangle.getScene().setCursor(Cursor.HAND);
                            roomName.setText(room.getDescription());
                            roomNo.setText("Room " + room.getNumber());
                            roomImage.setImage(new Image(String.valueOf(room.getImage())));
                            roomLabel.setVisible(true);
                        }
                    }
                } else {
                roomLabel.setVisible(false);
                rectangle.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    @FXML
    public void generateShortDjik() {
        clearPaths();
        boolean startB = false;
        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;
        GraphNode<Room> startNode = null;
        GraphNode<Room> endNode = null;
        for (GraphNode<Room> node : nodes) {
            if (node.getData().getNumber() == startForReal)
                startNode = node;
            if (node.getData().getNumber() == endForReal)
                endNode = node;
        }
        if (startNode != null && endNode != null) {
            CostedPath cpa = findCheapestPathDijkstra(startNode, endNode.getData());
            List<GraphNode<?>> pathList = cpa.pathList;
            for (int j = 0; j < pathList.size(); j++) {
                GraphNode<?> n = pathList.get(j);
                Room room = (Room) n.getData();
                for (Node node : rectangles) {
                    String i = String.valueOf("room"+room.getNumber());
                    if (node.getId().equals(i)) {
                        Rectangle rectangle = (Rectangle) node;
                        startX = rectangle.getLayoutX() + (rectangle.getWidth() / 2);
                        startY = rectangle.getLayoutY() + (rectangle.getHeight() / 2);
                    }
                }
                if (j == pathList.size() - 1) {
                    endX = endPoint.getTranslateX();
                    endY = endPoint.getTranslateY();
                    drawPath(startX, startY, endX, endY);
                } else {
                    GraphNode<?> m = pathList.get(j + 1);
                    Room next = (Room) m.getData();
                    for (Node node : rectangles) {
                        String i2 = String.valueOf("room"+next.getNumber());
                        if (node.getId().equals(i2)) {
                            Rectangle rectangle2 = (Rectangle) node;
                            endX = rectangle2.getLayoutX() + (rectangle2.getWidth() / 2);
                            endY = rectangle2.getLayoutY() + (rectangle2.getHeight() / 2);
                        }
                    }
                }
                if (!startB) {
                    startX = (int) startPoint.getTranslateX();
                    startY = (int) startPoint.getTranslateY();
                    startB = true;
                }
                System.out.println(startX + "," + startY + "," + endX + "," + endY);
                drawPath(startX, startY, endX, endY);
                System.out.println(n.getData());
            }
            System.out.println("\nThe total path cost is: " + cpa.pathCost);
        }
    }


    @FXML
    public void generateInterestingDjik() {
        clearPaths();
        boolean startB = false;
        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;
        GraphNode<Room> startNode = null;
        GraphNode<Room> endNode = null;
        for (GraphNode<Room> node : nodes) {
            if (node.getData().getNumber() == startForReal)
                startNode = node;
            if (node.getData().getNumber() == endForReal)
                endNode = node;
        }
        if (startNode != null && endNode != null) {
            CostedPath cpa = findCheapestPathDijkstra(startNode, endNode.getData());
            List<GraphNode<?>> pathList = cpa.pathList;
            for (int j = 0; j < pathList.size(); j++) {
                GraphNode<?> n = pathList.get(j);
                Room room = (Room) n.getData();
                for (Node node : rectangles) {
                    String i = String.valueOf(room.getNumber());
                    if (node.getId().contains(i)) {
                        Rectangle rectangle = (Rectangle) node;
                        startX = rectangle.getLayoutX() + (rectangle.getWidth() / 2);
                        startY = rectangle.getLayoutY() + (rectangle.getHeight() / 2);
                    }
                }
                if (j == pathList.size() - 1) {
                    endX = endPoint.getTranslateX();
                    endY = endPoint.getTranslateY();
                    drawPath(startX, startY, endX, endY);
                } else {
                    GraphNode<?> m = pathList.get(j + 1);
                    Room next = (Room) m.getData();
                    for (Node node : rectangles) {
                        String i2 = String.valueOf(next.getNumber());
                        if (node.getId().contains(i2)) {
                            Rectangle rectangle2 = (Rectangle) node;
                            endX = rectangle2.getLayoutX() + (rectangle2.getWidth() / 2);
                            endY = rectangle2.getLayoutY() + (rectangle2.getHeight() / 2);
                        }
                    }
                }
                if (!startB) {
                    startX = (int) startPoint.getTranslateX();
                    startY = (int) startPoint.getTranslateY();
                    startB = true;
                }
                System.out.println(startX + "," + startY + "," + endX + "," + endY);
                drawPath(startX, startY, endX, endY);
                System.out.println(n.getData());
            }
            System.out.println("\nThe total path cost is: " + cpa.pathCost);
        }
    }

    @FXML
    public void generateShortBFS() {
        Image map = mapImage.getImage();
        WritableImage writableImage = new WritableImage(map.getPixelReader(), (int) map.getWidth(), (int) map.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        pixels = new int[(int) map.getWidth()][(int) map.getHeight()];
        //new graph
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Color c = pixelReader.getColor(x, y);
                // System.out.println(c.getHue() + ", " + c.getSaturation() + ", " + c.getBrightness());
                if(c.getOpacity()==0)
                    continue;
                if (c.getHue()==36) {
                    pixels[x][y] = 0;
                    pixelWriter.setColor(x, y, Color.BLACK);
                } else {
                    pixels[x][y] = 1;
                    pixelWriter.setColor(x, y, Color.WHITE);
                }
            }
        }


        int row = pixels.length;
        int col = pixels[0].length;
        List<GraphNode<Integer>> bfsNodes = new ArrayList<>();
        /* The following loop checks for its neighbours
           and unites the indexes  if both are 1. */
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                if (pixels[x][y] == 1) {
                    GraphNode<Integer> node = new GraphNode<>(x * col + y);
                    bfsNodes.add(node);
                }
            }
        }

        Integer i = ((int) (startPoint.getTranslateX() * (startPoint.getTranslateY() + 7)));
        Integer j = ((int) (endPoint.getTranslateX() * (endPoint.getTranslateY() + 7)));
        GraphNode<Integer> startNode = null;
        GraphNode<Integer> endNode = null;

        for (GraphNode<Integer> node : bfsNodes) {
            GraphNode<Integer> next = bfsNodes.get(nodes.indexOf(node) +1 );
            if (next != null)
                node.connectToNodeUndirected(next, 1);
            if (Objects.equals(i, node.getData())) {
                startNode = node;
            }
            if (Objects.equals(j, node.getData())) {
                endNode = node;
            }
        }
        System.out.println(endNode.getData() + "\n" + startNode.getData());
        List<GraphNode<?>> bfsPath = findPathBreadthFirst(startNode, endNode);
        System.out.println(bfsPath);
        int num = 0;
        for (int k = 0; k < bfsPath.size(); k++) {
            Integer tits = (Integer) bfsPath.get(k).getData();
            num++;
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col; y++) {
                    if ((x * col + y) == tits) {
                        pixelWriter.setColor(x, y,Color.PINK);
                    }
                }
            }
        }
        System.out.println(num);


        mapImage.setImage(writableImage);
        //System.out.println(Arrays.deepToString(pixels));
    }


    @FXML
    public void generateValidDFS() {
        clearPaths();
        boolean startB = false;
        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;
        GraphNode<Room> startNode = null;
        GraphNode<Room> endNode = null;
        for (GraphNode<Room> node : nodes) {
            if (node.getData().getNumber() == startForReal)
                startNode = node;
            if (node.getData().getNumber() == endForReal)
                endNode = node;
        }
        if (startNode != null && endNode != null) {
            CostedPath cpa = searchGraphDepthFirstCheapestPath(startNode,nodestoignore,0,endNode.getData());
            List<GraphNode<?>> pathList = cpa.pathList;
            for (int j = 0; j < pathList.size(); j++) {
                GraphNode<?> n = pathList.get(j);
                Room room = (Room) n.getData();
                for (Node node : rectangles) {
                    String i = String.valueOf(room.getNumber());
                    if (node.getId().contains(i)) {
                        Rectangle rectangle = (Rectangle) node;
                        startX = rectangle.getLayoutX() + (rectangle.getWidth() / 2);
                        startY = rectangle.getLayoutY() + (rectangle.getHeight() / 2);
                    }
                }
                if (j == pathList.size() - 1) {
                    endX = endPoint.getTranslateX();
                    endY = endPoint.getTranslateY();
                    drawPath(startX, startY, endX, endY);
                } else {
                    GraphNode<?> m = pathList.get(j + 1);
                    Room next = (Room) m.getData();
                    for (Node node : rectangles) {
                        String i2 = String.valueOf(next.getNumber());
                        if (node.getId().contains(i2)) {
                            Rectangle rectangle2 = (Rectangle) node;
                            endX = rectangle2.getLayoutX() + (rectangle2.getWidth() / 2);
                            endY = rectangle2.getLayoutY() + (rectangle2.getHeight() / 2);
                        }
                    }
                }
                if (!startB) {
                    startX = (int) startPoint.getTranslateX();
                    startY = (int) startPoint.getTranslateY();
                    startB = true;
                }
                System.out.println(startX + "," + startY + "," + endX + "," + endY);
                drawPath(startX, startY, endX, endY);
                System.out.println(n.getData());
            }
            System.out.println("\nThe total path cost is: " + cpa.pathCost);
            System.out.println(allPathsFinal.toString());
        }
    }

    private void clearPaths() {
        List<Node> nodeList = new ArrayList<>(pane2.getChildren());
        pane2.getChildren().removeAll(nodeList);
    }

    private void drawPath(double fromX, double fromY, double toX, double toY) {
        Line line = new Line(fromX, fromY, toX, toY);
        line.setStroke(Color.INDIANRED);
        line.setStrokeWidth(3);
        pane2.getChildren().add(line);
    }
}


