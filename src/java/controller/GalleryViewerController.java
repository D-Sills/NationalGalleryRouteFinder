package controller;

import Model.GraphNode;
import Model.GraphNodeAL;
import Model.Room;
import Utilities.Alerts;
import Utilities.CreateRooms;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static Model.CustomGraph.*;
import static Utilities.CreateRooms.*;

/**
 * @author Darren Sills & Gedvydas Jucius
 * GalleryView class which shows the GalleryViewController.Fxml screen.
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
    @FXML
    private ListView<List<GraphNode<?>>> routeListView;
    @FXML
    private Spinner<Integer> maxSpinner;
    @FXML
    private Label pixelUnits;

    private final List<Node> rectangles = new ArrayList<>();
    private final List<GraphNode<Room>> nodes = new ArrayList<>();
    private final List<GraphNode<?>> nodestovisit = new ArrayList<>();
    private final List<GraphNode<?>> nodestoignore = new ArrayList<>();
    int[][] pixels;
    double x;
    double y;
    double x2;
    double y2;
    Point2D point2D;
    boolean endPointSet;
    int start;
    int startForReal;
    int end;
    int endForReal;
    boolean startPointSet;
    Image og;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        //CreateRooms.Create();
        try {
            CreateRooms.onActionLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        maxSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0,
                        1000,
                        10
                )
        );
        og = mapImage.getImage();
        routeListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<List<GraphNode<?>>> call(ListView<List<GraphNode<?>>> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(List<GraphNode<?>> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Route " + (getAllPathsFinal().indexOf(item)+1));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Generate the shortest path between two points using Djikstra's algorithm
     */
    public void generateShortDjik() {
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
            assert cpa != null;
            drawRoute(cpa);
            System.out.println("\nThe total path cost is: " + cpa.pathCost);
        }
    }

    /**
     * Generate the most interesting path between two points using Djikstra's algorithm, taking into account ignored and favoured rooms
     */
    public void generateInterestingDjik() {
        GraphNode<Room> startNode = null;
        GraphNode<Room> endNode = null;
        for (GraphNode<Room> node : nodes) {
            if (node.getData().getNumber() == startForReal)
                startNode = node;
            if (node.getData().getNumber() == endForReal)
                endNode = node;
        }
        if (startNode != null && endNode != null) {
            List<GraphNode<?>> tmp = new ArrayList<>(nodestoignore);
            List<GraphNode<?>> tmp2 = new ArrayList<>(nodestovisit);
            tmp2.add(endNode);
            CostedPath cpa = findInterestingPathDijkstra(startNode, tmp, tmp2);
            if (cpa.pathList.isEmpty()) {
                clearPaths();
                Alerts.genericWarning("Current path is not possible");
                return;
            }
            drawRoute(Objects.requireNonNull(cpa));
            System.out.println("\nThe total path cost is: " + cpa.pathCost);
        }
    }

    /**
     * Generate a path between two points using BFS, pixel by pixel
     */
    public void generateShortBFS() {
        Image tmp = og;
        WritableImage writableImage = new WritableImage(tmp.getPixelReader(), (int) tmp.getWidth(), (int) tmp.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        pixels = new int[(int) tmp.getWidth()][(int) tmp.getHeight()];
        for (int x = 0; x < tmp.getWidth(); x++) {
            for (int y = 0; y < tmp.getHeight(); y++) {
                Color c = pixelReader.getColor(x, y);
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
        mapImage.setImage(writableImage);

        int row = pixels.length;
        int col = pixels[0].length;
        GraphNodeAL<Integer> startNode = null;
        GraphNodeAL<Integer> endNode = null;
        List<GraphNodeAL<Integer>> bfsNodes = new ArrayList<>();
        /* The following loop checks for its neighbours
           and unites the indexes  if both are 1. */
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                if (x < row - 1 && pixels[x][y] == pixels[x + 1][y]) {
                    if (x == (int) startPoint.getTranslateX() && y == (int) startPoint.getTranslateY() + 7) {

                    }
                    if (x == (int) endPoint.getTranslateX() && y == (int) endPoint.getTranslateY() + 7) {
                        //endNode = node;
                        System.out.println("foundend");
                    }
                }
                if (y < col - 1 && pixels[x][y] == pixels[x][y + 1]) {
                    GraphNodeAL<Integer> node = new GraphNodeAL<>(x * col + (y + 1));
                    bfsNodes.add(node);
                    if (x == (int) startPoint.getTranslateX() && y == (int) startPoint.getTranslateY() + 7) {
                        startNode = node;
                        System.out.println("foundstart");
                    }
                    if (x == (int) endPoint.getTranslateX() && y == (int) endPoint.getTranslateY() + 7) {
                        endNode = node;
                        System.out.println("foundend");
                    }
                }
            }
        }

        for (int i = 0; i < bfsNodes.size(); i++) {
            GraphNodeAL<Integer> node = bfsNodes.get(i);
            if (i < bfsNodes.size() - 1) {
                GraphNodeAL<Integer> next = bfsNodes.get(bfsNodes.indexOf(node) + 1);
                node.connectToNodeUndirected(next);
            }
        }

        System.out.println(endNode.data + "\n" + startNode.data);
        List<GraphNodeAL<?>> bfsPath = findPathBreadthFirst(startNode, endNode.data);
        System.out.println(bfsPath);
        int num = 0;
        for (GraphNodeAL<?> graphNode : bfsPath) {
            Integer tits = (Integer) graphNode.data;
            num++;
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col; y++) {
                    if ((x * col + y) == tits) {
                        pixelWriter.setColor(x, y, Color.PINK);
                    }
                }
            }
        }
        pixelUnits.setText(String.valueOf(bfsNodes.size()));
        System.out.println(num);



        //System.out.println(Arrays.deepToString(pixels));
    }

    /**
     * Generate all paths between two points using DFS and displays them in a listview
     */
    public void generateValidDFS() {
        GraphNode<Room> startNode = null;
        GraphNode<Room> endNode = null;
        for (GraphNode<Room> node : nodes) {
            if (node.getData().getNumber() == startForReal)
                startNode = node;
            if (node.getData().getNumber() == endForReal)
                endNode = node;
        }
        if (startNode != null && endNode != null) {
            routeListView.getItems().clear();
            int max = maxSpinner.getValue();
            List<List<GraphNode<?>>> cpa = findAllPathsDepthFirst(startNode,null,endNode.getData());
            getAllPathsFinal().clear();
            int i=0;
            for (List<GraphNode<?>> list: cpa) {
                i++;
                if (i <= max) {
                    getAllPathsFinal().add(list);
                }
            }
            //for(int i=getAllPathsFinal().size()-1; i>0; i--) {
            //    for(int j=i-1; j>=0; j--) {
            //        if(getAllPathsFinal().get(i).equals(getAllPathsFinal().get(j))) {
            //            getAllPathsFinal().remove(i);
            //            break;
            //        }
            //    }
            //}
            for (List<GraphNode<?>> path : getAllPathsFinal()) {

                routeListView.getItems().add(path);
                routeListView.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                        List<GraphNode<?>> route = routeListView.getSelectionModel().getSelectedItem();
                        drawRoute(route);
                    }
                });
            }
            routeListView.setDisable(false);
            routeListView.getSelectionModel().select(0);
            drawRoute(routeListView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Draws the lines between nodes on the map
     */
    private void drawRoute(CostedPath path) {
        clearPaths();
        boolean startB = false;
        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;
        List<GraphNode<?>> pathList = path.pathList;

        for (int j = 0; j < pathList.size(); j++) {
            GraphNode<?> n = pathList.get(j);
            Room room = (Room) n.getData();
            if (!startB) {
                startX = (int) startPoint.getTranslateX();
                startY = (int) startPoint.getTranslateY();
                for (Node node : rectangles) {
                    String i = "room" + room.getNumber();
                    if (node.getId().equals(i)) {
                        Rectangle rectangle = (Rectangle) node;
                        endX = rectangle.getLayoutX() + (rectangle.getWidth() / 2);
                        endY = rectangle.getLayoutY() + (rectangle.getHeight() / 2);
                    }
                }
                drawPath(startX, startY, endX, endY);
                startB = true;
            }
            for (Node node : rectangles) {
                String i = "room" + room.getNumber();
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
                    String i2 = "room" + next.getNumber();
                    if (node.getId().equals(i2)) {
                        Rectangle rectangle2 = (Rectangle) node;
                        endX = rectangle2.getLayoutX() + (rectangle2.getWidth() / 2);
                        endY = rectangle2.getLayoutY() + (rectangle2.getHeight() / 2);
                    }
                }
            }

            System.out.println(startX + "," + startY + "," + endX + "," + endY);
            drawPath(startX, startY, endX, endY);
            System.out.println(n.getData());
        }
    }

    private void drawRoute(List<GraphNode<?>> path) {
        clearPaths();
        boolean startB = false;
        double startX = 0;
        double startY = 0;
        double endX = 0;
        double endY = 0;
        List<GraphNode<?>> pathList = path;
        for (int j = 0; j < pathList.size(); j++) {
            GraphNode<?> n = pathList.get(j);
            Room room = (Room) n.getData();
            if (!startB) {
                startX = (int) startPoint.getTranslateX();
                startY = (int) startPoint.getTranslateY();
                for (Node node : rectangles) {
                    String i = "room" + room.getNumber();
                    if (node.getId().equals(i)) {
                        Rectangle rectangle = (Rectangle) node;
                        endX = rectangle.getLayoutX() + (rectangle.getWidth() / 2);
                        endY = rectangle.getLayoutY() + (rectangle.getHeight() / 2);
                    }
                }
                drawPath(startX, startY, endX, endY);
                startB = true;
            }
            for (Node node : rectangles) {
                String i = "room" + room.getNumber();
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
                    String i2 = "room" + next.getNumber();
                    if (node.getId().equals(i2)) {
                        Rectangle rectangle2 = (Rectangle) node;
                        endX = rectangle2.getLayoutX() + (rectangle2.getWidth() / 2);
                        endY = rectangle2.getLayoutY() + (rectangle2.getHeight() / 2);
                    }
                }
            }
            System.out.println(startX + "," + startY + "," + endX + "," + endY);
            drawPath(startX, startY, endX, endY);
            System.out.println(n.getData());
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

    private void addLisenters(Node rectangle) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            //start point
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (mouseEvent.getClickCount() == 1) {
                    for (Room room : rooms) {
                        String i = "room" + room.getNumber();
                        if (rectangle.getId().equals(i)) {
                            start = room.getNumber();
                            if (start == endForReal) {
                                Alerts.genericWarning("Same");
                                return;
                            }
                            routeListView.getItems().clear();
                            routeListView.setDisable(true);
                            clearPaths();
                            startForReal = start;
                            startPoint.setTranslateX(x2);
                            startPoint.setTranslateY(y2);
                            startPoint.setVisible(true);
                            startPointSet = true;
                        }
                    }
                }
                //favoured room
                if (mouseEvent.getClickCount() >= 2) {
                    for (Room room : rooms) {
                        String i = "room" + room.getNumber();
                        if (rectangle.getId().equals(i)) {
                            TreeItem<String> treeItem = new TreeItem<>();
                            treeItem.setValue(room.getNumber() + " - " + room.getDescription());
                            for (TreeItem<String> treeItem1 : favoured.getChildren()) {
                                if (treeItem1.getValue().equals(room.getNumber() + " - " + room.getDescription())) {
                                    for (GraphNode<Room> node: nodes) {
                                        if (node.getData().equals(room)) {
                                            nodestovisit.remove(node);
                                        }
                                    }
                                    favoured.getChildren().remove(treeItem1);
                                    return;
                                }
                            }
                            favoured.getChildren().add(treeItem);
                            for (GraphNode<Room> node: nodes) {
                                if (node.getData().equals(room)) {
                                    nodestovisit.add(node);
                                }
                            }
                        }
                    }
                }
            }
            //end point
            if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.SECONDARY) {
                for (Room room : rooms) {
                    String i = "room" + room.getNumber();
                    if (rectangle.getId().equals(i)) {
                        end = room.getNumber();
                        if (end == startForReal) {
                            Alerts.genericWarning("Same");
                            return;
                        }
                        routeListView.getItems().clear();
                        routeListView.setDisable(true);
                        clearPaths();
                        endForReal = end;
                        endPoint.setTranslateX(x2);
                        endPoint.setTranslateY(y2);
                        endPoint.setVisible(true);
                        endPointSet = true;
                    }
                }
            }

            //ignore room
            if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.MIDDLE) {
                for (Room room : rooms) {
                    String i = "room" + room.getNumber();
                    if (rectangle.getId().equals(i)) {
                        TreeItem<String> treeItem = new TreeItem<>();
                        treeItem.setValue(room.getNumber() + " - " + room.getDescription());
                        for (TreeItem<String> treeItem1 : ignored.getChildren()) {
                            if (treeItem1.getValue().equals(room.getNumber() + " - " + room.getDescription())) {
                                for (GraphNode<Room> node: nodes) {
                                    if (node.getData().equals(room)) {
                                        nodestoignore.remove(node);
                                    }
                                }
                                ignored.getChildren().remove(treeItem1);
                                return;
                            }
                        }
                        ignored.getChildren().add(treeItem);
                        for (GraphNode<Room> node: nodes) {
                            if (node.getData().equals(room)) {
                                nodestoignore.add(node);
                            }
                        }
                    }
                }
            }
        });

        rectangle.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                for (Room room : rooms) {
                    String i = "room" + room.getNumber();
                    if (rectangle.getId().equals(i)) {
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

            roomTree.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2) {
                    TreeItem<String> item = roomTree.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());
                    Room theroom = null;
                    for (Room room : rooms) {
                        if (item.getValue().equals(room.getNumber() + " - " + room.getDescription())) {
                            theroom = room;
                        }
                        for (TreeItem<String> treeItem1 : ignored.getChildren()) {
                            if (treeItem1.equals(item)) {
                                for (GraphNode<?> node : nodestoignore) {
                                    if (node.getData().equals(theroom)) {
                                        nodestoignore.remove(node);
                                        ignored.getChildren().remove(item);
                                    }
                                }

                            }
                        }
                        for (TreeItem<String> treeItem1 : favoured.getChildren()) {
                            if (treeItem1.equals(item)) {
                                for (GraphNode<?> node : nodestovisit) {
                                    if (node.getData().equals(theroom)) {
                                        nodestovisit.remove(node);
                                        favoured.getChildren().remove(item);
                                    }
                                }

                            }
                        }
                    }
                }
            });
        });
    }
}


