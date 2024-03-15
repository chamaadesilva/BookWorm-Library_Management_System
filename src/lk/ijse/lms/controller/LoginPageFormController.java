package lk.ijse.lms.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;

import java.io.IOException;

public class LoginPageFormController {
    public AnchorPane root;
    public TextField txtName;
    public PasswordField txtPwd;
    public Label lblHide;

    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        if (txtName.getText().equals("root")) {
            if (txtPwd.getText().equals("password")) {
//                UILoader.SetUiCloseUnderTheAnchorpane("AdminOrCashier", FirstPageAnchorPaneContext);
                NotificationController.LoginSuccessfulNotification("YOUR");
                UILoader.load("MainForm", root);
            } else {
                NotificationController.LoginUnSuccessfulNotification("entered", 3);
            }
        } else {
            NotificationController.LoginSuccessfulNotification("");
        }
    }

    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("lk/ijse/lms/assets/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        txtPwd.setPromptText(txtPwd.getText());
        txtPwd.setText("");
        txtPwd.setDisable(true);
        txtPwd.requestFocus();
    }

    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("lk/ijse/lms/assets/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        txtPwd.setText(txtPwd.getPromptText());
        txtPwd.setPromptText("");
        txtPwd.setDisable(false);
    }
}
