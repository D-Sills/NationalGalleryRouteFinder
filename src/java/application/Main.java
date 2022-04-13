package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Darren Sills
 * Main class is the launcher for the JavaFX application and also initializes the Vaccination System.
 */
public class Main extends Application {
    /**
     * Starts the JavaFX scene; Main Menu.fxml
     * @param stage stage for the JavaFX application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/GalleryViewer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 964, 813);
        stage.setTitle("National Gallery Route Finder");
        stage.setScene(scene);
        stage.setResizable(false); //application isn't responsive so non-resizable
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/mona-lisa.png")))); //set the icon
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
