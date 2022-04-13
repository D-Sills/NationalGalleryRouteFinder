package controller;

import Utilities.Alerts;
import Utilities.DisjointSet;
import Utilities.DisjointSetNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author Darren Sills
 * MainMenu class which shows the MainMenu.Fxml screen.
 * Responsible for swapping between the main scenes and also for saving and loading data via xstream.
 */
public class PCBViewerController implements Initializable {
    @FXML
    private Button convertButton;
    @FXML
    private Button openFile;
    @FXML
    private ImageView pcbImageBW;
    @FXML
    private ImageView pcbImageColour;
    @FXML
    private Tab bwTab;
    @FXML
    private Tab cTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label fileName;
    @FXML
    private Label fileSize;
    @FXML
    private Label briLabel;
    @FXML
    private Label satLabel;
    @FXML
    private Label hueVal;
    @FXML
    private Label hueVal2;
    @FXML
    private Label fileResolution;
    @FXML
    private Slider brightnessS;
    @FXML
    private RangeSlider hueS;
    @FXML
    private Slider satS;
    @FXML
    private VBox compLabel;
    @FXML
    private Label compSize;
    @FXML
    private Label compName;
    @FXML
    private Label compNumb;
    @FXML
    private ChoiceBox<String> componentSelect;
    @FXML
    private VBox postConvertBox;
    @FXML
    private Spinner<Integer> noiseSpinner;

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
    SingleSelectionModel<Tab> selectionModel;
    private Color[] colors;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    int[][] pixels;
    FileChooser fileChooser = new FileChooser();
    Image chosenImage;
    Stage stage;
    double h ;
    double h2 ;
    double s;
    double v;
    boolean once = false;
    int colourMode = 1;
    TextInputDialog td;
    DisjointSet<Integer> ds;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.jpeg")
        );
        satS.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            satLabel.textProperty().setValue(df.format(newValue));
            s = (double) newValue;
        });
        brightnessS.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            briLabel.textProperty().setValue(df.format(newValue));
            v = (double) newValue;
        });
        hueS.lowValueProperty().addListener((observableValue, oldValue, newValue) -> {
            hueVal.textProperty().setValue(df.format(newValue));
            h = (double) newValue;
        });
        hueS.highValueProperty().addListener((observableValue, oldValue, newValue) -> {
            hueVal2.textProperty().setValue(df.format(newValue));
            h2 = (double) newValue;
        });
        postConvertBox.setDisable(true);
        hueS.setHighValue(188);
        hueS.setLowValue(90);
        compLabel.setVisible(false);
        td = new TextInputDialog();
        td.setContentText("Component Name:");
        td.setTitle("Name Entry");
        DialogPane dialogPane = td.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        //dialogPane.getStyleClass().add("myDialog");
        selectionModel = tabPane.getSelectionModel();
        noiseSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0,
                        5000,
                        100
                )
        );
        noiseSpinner.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> noiseSpinner.increment(100);
                case DOWN -> noiseSpinner.decrement(100);
            }
        });
    }

    /**
     * Switches scenes to Appointments.fxml on button press
     */
    public void openFile() {
        stage = (Stage) pcbImageBW.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectionModel.select(cTab);
            postConvertBox.setDisable(true);
            bwTab.setDisable(true);
            final InputStream targetStream;
            try {
                targetStream = new DataInputStream(new FileInputStream(file));
                chosenImage = new Image(targetStream,491,491,false, false);
                fileName.setText(file.getName());
                long size = file.length() / 1024;
                fileSize.setText(size + "kb");
                fileResolution.setText(chosenImage.getWidth() + " x " + chosenImage.getHeight());
                pcbImageBW.setImage(chosenImage); // Set Image
                pcbImageColour.setImage(chosenImage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    public void convertButton() {
        if (pcbImageBW.getImage() == null) {
            Alerts.genericWarning("Please select an image!");
        } else {
            colourMode = 1;
            WritableImage writableImage = new WritableImage(chosenImage.getPixelReader(), (int) pcbImageColour.getFitWidth(), (int) pcbImageColour.getFitHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            PixelReader pixelReader = writableImage.getPixelReader();
            pixels = new int[(int) pcbImageColour.getFitWidth()][(int) pcbImageColour.getFitHeight()];
            ds = new DisjointSet<>();
            for (int x = 0; x < pcbImageColour.getFitWidth(); x++) {
                for (int y = 0; y < pcbImageColour.getFitHeight(); y++) {
                    Color c = pixelReader.getColor(x, y);
                    if (c.getHue() > h && c.getHue() < h2 && c.getSaturation() > s && c.getBrightness() > v) {
                        pixels[x][y] = 0;
                        pixelWriter.setColor(x, y, Color.WHITE);
                    } else {
                        pixels[x][y] = 1;
                        pixelWriter.setColor(x, y, Color.BLACK);
                    }
                }
            }
            int row = pixels.length;
            int col = pixels[0].length;
            int cock = row * col;
            for (int i = 0; i < cock; i++)
                ds.makeSet(i);
        /* The following loop checks for its neighbours
           and unites the indexes  if both are 1. */
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col; y++) {
                    if (pixels[x][y] == 0)
                        continue;

                    if (x < row - 1 && pixels[x][y] == pixels[x + 1][y])
                        ds.union(getPixel(x, y, col), getPixel(x + 1, y, col));
                    if (y < col - 1 && pixels[x][y] == pixels[x][y + 1])
                        ds.union(getPixel(x, y, col), getPixel(x, y + 1, col));
                }
            }

            //noise removal
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col; y++) {
                    DisjointSetNode<Integer> node = ds.getNode(x * (col) + y);
                    if (node != null && ds.findSet(node).getContents().size() <= noiseSpinner.getValue()) {
                        pixels[x][y] = 0;
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }
                }
            }
            ds.populateParents();

            for (DisjointSetNode<Integer> node : ds.parents) {
                if (node != null && ds.findSet(node).getContents().size() <= noiseSpinner.getValue()) {
                    ds.removeSet(node.getData());
                }
            }
            ds.populateParents();
            ObservableList<String> parents = FXCollections.observableArrayList();
            for (DisjointSetNode<Integer> node: ds.parents) {
                String s = (ds.parents.indexOf(node)+1) + " - " + node.getCompName();
                parents.add(s);
            }
            componentSelect.setItems(parents);

            if (!once) {
                addListeners(pcbImageBW);
                addListeners(pcbImageColour);
                once = true;
            }
            bwTab.setDisable(false);
            postConvertBox.setDisable(false);
            pcbImageBW.setImage(writableImage);
            selectionModel.select(bwTab);
        }
    }

    private void addListeners(ImageView imageView) {
        int col = pixels[0].length;
        imageView.setOnMouseClicked(click -> {
            int x = Math.round((int) click.getX());
            int y = Math.round((int) click.getY());
            if(click.getClickCount() == 2) {
                DisjointSetNode<Integer> node = ds.getNode(x * (col) + y);
                System.out.println("Double");
                if (node != null && ds.findSet(node).getContents().size() >= noiseSpinner.getValue()) {
                    td.getEditor().setText("");
                    td.showAndWait();
                    if (td.getEditor().getText().isBlank())
                        return;
                    else {
                        ds.findSet(node).setCompName(td.getEditor().getText());
                        ObservableList<String> parents = FXCollections.observableArrayList();
                        for (DisjointSetNode<Integer> node1 : ds.parents) {
                            String s = (ds.parents.indexOf(node1) + 1) + " - " + node1.getCompName();
                            parents.add(s);
                        }
                        componentSelect.setItems(parents);
                    }
                    }
                }

            for (DisjointSetNode<Integer> parent : ds.parents) {
                for (DisjointSetNode<Integer> node : parent.getContents()) {
                    if ((x * (col) + y) == node.getData()) {
                        if (click.getY() > 380) {
                            compLabel.setTranslateY(201);
                        } else
                            compLabel.setTranslateY(y - 170);
                        if (click.getX() > 245) {
                            compLabel.setTranslateX(x - 200);
                        } else
                            compLabel.setTranslateX(x + 40);
                        compName.setText("Component Name: " + parent.getCompName());
                        compNumb.setText("Component Number: " + (ds.parents.indexOf(parent) + 1));
                        compSize.setText("Component Size(Pixel Units): " + parent.getContents().size());
                        String s = (ds.parents.indexOf(parent)+1) + " - " + parent.getCompName();
                        componentSelect.getSelectionModel().select(s);
                        compLabel.setVisible(true);
                    }
                }
            }

            Scene scene = imageView.getScene();
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
                if (!inHierarchy(evt.getPickResult().getIntersectedNode(), imageView)) {
                    compLabel.setVisible(false);
                }
            });
        });
    }


    public void RandomColourButton() {
        colourMode = 2;
        WritableImage writableImage = new WritableImage(chosenImage.getPixelReader(), (int) pcbImageColour.getFitWidth(), (int) pcbImageColour.getFitHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        Random rand = new Random();
        colors = new Color[300];
        for (int i=0; i<300; i++) {
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            colors[i] = Color.rgb(r, g, b);
        }

        int row = pixels.length;
        int col = pixels[0].length;
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                DisjointSetNode<Integer> node = ds.getNode(x * (col) + y);
                if (node != null && ds.findSet(node).getContents().size() >= noiseSpinner.getValue()) {
                    Color random = colors[ds.parents.indexOf(ds.findSet(node))];
                    pixelWriter.setColor(x, y, random);
                } else
                    pixelWriter.setColor(x, y, Color.WHITE);
            }
        }
        pcbImageBW.setImage(writableImage);
        selectionModel.select(bwTab);
    }

    public void SampleColourButton() {
        colourMode = 3;
        WritableImage writableImage = new WritableImage(chosenImage.getPixelReader(), (int) pcbImageColour.getFitWidth(), (int) pcbImageColour.getFitHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        int row = pixels.length;
        int col = pixels[0].length;
        for (int x=0; x<row; x++) {
            for (int y=0; y<col; y++) {
                Color sample = chosenImage.getPixelReader().getColor(x, y);
                DisjointSetNode<Integer> node = ds.getNode(x*(col)+y);
                if (node != null && ds.findSet(node).getContents().size() >= noiseSpinner.getValue() ) {
                    pixelWriter.setColor(x, y, sample);
                } else
                pixelWriter.setColor(x, y, Color.WHITE);
            }
        }
        pcbImageBW.setImage(writableImage);
        selectionModel.select(bwTab);
    }

    public void NoColourButton() {
        colourMode = 1;
        WritableImage writableImage = new WritableImage(chosenImage.getPixelReader(), (int) pcbImageColour.getFitWidth(), (int) pcbImageColour.getFitHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        int row = pixels.length;
        int col = pixels[0].length;
        for (int x=0; x<row; x++) {
            for (int y=0; y<col; y++) {
                DisjointSetNode<Integer> node = ds.getNode(x*(col)+y);
                if (node != null && ds.findSet(node).getContents().size() >= noiseSpinner.getValue() ) {
                    pixelWriter.setColor(x, y, Color.BLACK);
                } else
                    pixelWriter.setColor(x, y, Color.WHITE);
            }
        }
        pcbImageBW.setImage(writableImage);
        selectionModel.select(bwTab);
    }

    public void BorderColourButton() {
        WritableImage writableImage = new WritableImage(chosenImage.getPixelReader(), (int) pcbImageColour.getFitWidth(), (int) pcbImageColour.getFitHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        int row = pixels.length;
        int col = pixels[0].length;
        for (int x=0; x<row; x++) {
            for (int y=0; y<col; y++) {
                DisjointSetNode<Integer> node = ds.getNode(x*(col)+y);
                if (node != null && ds.findSet(node).getContents().size() >= noiseSpinner.getValue() ) {
                  //  pixelWriter.setColor(x, y, sample);
                } else
                    pixelWriter.setColor(x, y, Color.WHITE);
            }
        }
        pcbImageBW.setImage(writableImage);

        Path outline = new Path();
        outline.getElements();
    }

    public void RemoveSetButton() {
        boolean success = false;
        DisjointSetNode<Integer> parent = null;

        if (ds.parents.isEmpty()) {
            return;
        }

        for (DisjointSetNode<Integer> node : ds.parents) {
            String s = (ds.parents.indexOf(node)+1) + " - " + node.getCompName();
            if (s.equalsIgnoreCase(componentSelect.getSelectionModel().getSelectedItem())) {
                parent = node;
                success = true;
            }
        }
        if (success) {
            ds.removeSet(parent.getData());
            ds.populateParents();
            ObservableList<String> parents = FXCollections.observableArrayList();
            for (DisjointSetNode<Integer> node : ds.parents) {
                String s = (ds.parents.indexOf(node)+1) + " - " + node.getCompName();
                parents.add(s);
            }
            componentSelect.setItems(parents);
            if (colourMode == 1)
                NoColourButton();
            if (colourMode == 2)
                RandomColourButton();
            if (colourMode == 3)
                SampleColourButton();
        } else {
            Alerts.genericWarning("Cock");
        }
    }

    private int getPixel(int x, int y, int cock) {
        return x*cock + y;
    }
}

