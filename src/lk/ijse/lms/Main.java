package lk.ijse.lms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.lms.util.FactoryConfiguration;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @SneakyThrows
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(load(Objects.requireNonNull(this.getClass()
                .getResource("view/MainForm.fxml")))));
        primaryStage.setTitle("BookWorm");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
