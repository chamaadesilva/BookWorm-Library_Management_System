package lk.ijse.lms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * This class is annotated with @NoArgsConstructor from the Lombok library.
 * The @NoArgsConstructor annotation generates a constructor with no parameters.
 * The 'access' attribute is set to AccessLevel.PRIVATE, which means the generated constructor will be private.
 * This is often used in utility classes, where instances are not supposed to be created.
 */
public class UILoader {
    /**
     * Loads an FXML file into the provided AnchorPane.
     * The existing children of the AnchorPane are cleared before the new content is added.
     *
     * @param filename The name of the FXML file to load. The file should be located in the '../view/' directory.
     * @param context  The AnchorPane where the loaded FXML file will be displayed.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public static void loadOnTheCurrentPane(String filename, AnchorPane context) throws IOException {
        URL resource = UILoader.class.getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        context.getChildren().clear();
        context.getChildren().add(load);
    }

    /**
     * Loads an FXML file and sets it as the root of the scene of the current window.
     * The window is then centered on the screen.
     *
     * @param filename The name of the FXML file to load. The file should be located in the '../view/' directory.
     * @param context  The AnchorPane from which the current window (Stage) is retrieved.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    public static void load(String filename, AnchorPane context) throws IOException {
        URL resource = UILoader.class.getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) context.getScene().getWindow();
        window.setScene(new Scene(load));
        window.centerOnScreen();
    }
}
