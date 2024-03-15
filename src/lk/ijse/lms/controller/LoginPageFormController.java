package lk.ijse.lms.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.bo.BOFactory;
import lk.ijse.lms.bo.UserBO;
import lk.ijse.lms.dto.UserDto;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPageFormController {
    private final UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.USER);
    public AnchorPane root;
    public TextField txtName;
    public PasswordField txtPwd;
    public Label lblHide;
    public ImageView powerOffSystem;
    public ImageView userLock;
    public Label warningLabel;

    private int count = 0;

    public void initialize() {
        warningLabel.setVisible(false);
        userLock.setVisible(false);
        txtName.requestFocus();
    }

    public void logInOnAction(ActionEvent actionEvent) throws Exception {

        List<UserDto> all = userBO.findAll();

        if (count >= 3) {
            userLock.setVisible(true);
            txtName.setDisable(true);
            txtPwd.setDisable(true);
            warningLabel.setVisible(true);
            NotificationController.LoginUnSuccessfulNotification("entered", 3);
            TimerOnAction();
        } else {
            if (!all.isEmpty()) {
                boolean userFound = false;
                for (UserDto userDto : all) {
                    if (txtName.getText().equals(userDto.getName())) {
                        userFound = true;
                        if (txtPwd.getText().equals(userDto.getPassword())) {
                            NotificationController.LoginSuccessfulNotification("YOUR");
                            UILoader.load("MainForm", root);
                        } else {
                            count++;
                            NotificationController.LoginUnSuccessfulNotification("entered", 3);
                        }
                    }
                }
                if (!userFound) {
                    count++;
                    if (txtName.getText().equals("admin") && txtPwd.getText().equals("admin")) {
                        NotificationController.LoginSuccessfulNotification("YOUR");
                        UILoader.load("MainForm", root);
                    } else {
                        count++;
                        NotificationController.LoginUnSuccessfulNotification("entered", 3);
                    }
                }
            }
        }
    }

    public void TimerOnAction(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                userLock.setVisible(false);
                txtName.setDisable(false);
                txtPwd.setDisable(false);
                warningLabel.setVisible(false);

            }
        };
        timer.schedule(task, 30000);
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

    public void systemShutDown(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
