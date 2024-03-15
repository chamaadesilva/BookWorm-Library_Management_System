package lk.ijse.lms.util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * The NotificationController class provides utility methods for displaying notifications in a JavaFX application.
 * It uses the Notifications class from the ControlsFX library to create and display the notifications.
 */
public class NotificationController {

    /**
     * Displays a notification indicating that a table operation was successful.
     * The notification includes a title, a message, an icon, and it disappears after 6 seconds.
     */
    public static void SuccessfulTableNotification() {
        Notifications notificationBuilder = Notifications.create().title("Saved Successfully.!").text("Your Details Saved Successfully.").graphic(new ImageView(new Image("lk/ijse/lms/assets/check.png"))).hideAfter(Duration.seconds(6)).position(Pos.BASELINE_CENTER);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    /**
     * Displays a notification indicating that an update operation was successful.
     * The notification includes a title, a message, an icon, and it disappears after 6 seconds.
     */
    public static void UpdatetSuccess() {
        Notifications notificationBuilder = Notifications.create().title("Updated Successfully.!").text("Your Details Updated Successfully.").graphic(new ImageView(new Image("lk/ijse/lms/assets/check.png"))).hideAfter(Duration.seconds(6)).position(Pos.BASELINE_CENTER);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    /**
     * Displays a notification indicating that a table operation was unsuccessful.
     * The notification includes a title, a message, an icon, and it disappears after 8 seconds.
     *
     * @param option  The title of the notification.
     * @param option2 Unused parameter.
     */
    public static void UnSuccessfulTableNotification(String option, String option2) {
        Notifications notificationBuilder = Notifications.create().title(option + " Successful.!").text("Detail removed Successfully.").graphic(new ImageView(new Image("lk/ijse/lms/assets/error.png"))).hideAfter(Duration.seconds(8)).position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    /**
     * Displays a notification indicating that a delete operation was unsuccessful.
     * The notification includes a title, a message, an icon, and it disappears after 8 seconds.
     */
    public static void DeleteFailure() {
        Notifications notificationBuilder = Notifications.create().title("Delete UnSuccessful.! ").text("Delete Failed !.").graphic(new ImageView(new Image("lk/ijse/lms/assets/error.png"))).hideAfter(Duration.seconds(8)).position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    /**
     * Displays a notification indicating that a login operation was successful.
     * The notification includes a title, a message, an icon, and it disappears after 8 seconds.
     *
     * @param option The user role that successfully logged in.
     */
    public static void LoginSuccessfulNotification(String option) {
        Notifications notificationBuilder = Notifications.create().title(option + " Login Successful.!").text("You have Successfully Login " + option + " to the System.").graphic(new ImageView(new Image("lk/ijse/lms/assets/check.png"))).hideAfter(Duration.seconds(8)).position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    /**
     * Displays a notification indicating that a login operation was unsuccessful.
     * The notification includes a title, a message, an icon, and it disappears after 8 seconds.
     *
     * @param option   The user role that failed to log in.
     * @param attempts The number of failed login attempts.
     */
    public static void LoginUnSuccessfulNotification(String option, int attempts) {
        Notifications notificationBuilder = Notifications.create().title("\t\t\t☠" + option + " ACCESS DENIED..!").text("Unauthorized access detected.\nIf u entered 3 times wrong details\nyour login option is blocking temporally.").graphic(new ImageView(new Image("lk/ijse/lms/assets/error.png"))).hideAfter(Duration.seconds(8)).position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
}
