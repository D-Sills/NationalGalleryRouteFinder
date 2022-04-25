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
        GraphNode<Room> r1 = new GraphNode<>(rooms.get(0));
        GraphNode<Room> r2 = new GraphNode<>(rooms.get(1));
        GraphNode<Room> r3 = new GraphNode<>(rooms.get(2));
        nodes.add(r1);
        nodes.add(r2);
        nodes.add(r3);
        r1.connectToNodeUndirected(r2, 2);
        r2.connectToNodeUndirected(r3, 2);

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


