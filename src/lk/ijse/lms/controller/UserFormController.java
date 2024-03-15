package lk.ijse.lms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.bo.BOFactory;
import lk.ijse.lms.bo.UserBO;
import lk.ijse.lms.dto.UserDto;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;
import lk.ijse.lms.util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class UserFormController {

    private final UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.USER);

    private final LinkedHashMap<TextField, Pattern> userHashMap = new LinkedHashMap<>();
    public AnchorPane root;
    public TextField txtUserId;
    public TextField txtUserName;
    public TextField txtEmail;
    public TextField txtPassword;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public TableView<UserDto> tblUser;
    public TableColumn<UserDto, String> colUserId;
    public TableColumn<UserDto, String> colUserName;
    public TableColumn<UserDto, String> colEmail;
    public TableColumn<UserDto, String> colPassword;

    int userRowNumber;

    ObservableList<UserDto> userObList = FXCollections.observableArrayList();

    public String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public void initialize() {

        txtUserId.setEditable(false);
        txtUserName.requestFocus();

        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(cellData -> {
            String password = cellData.getValue().getPassword();
            String maskedPassword = password.replaceAll(".", "*");
            return new SimpleStringProperty(maskedPassword);
        });
        tblUser.refresh();


        try {
            loadUser();
            setUserID();
        } catch (Exception ignored) {
        }

        tblUser.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            userRowNumber = (Integer) newValue;
            btnAdd.setDisable(false);
            btnRemove.setDisable(true);
            btnRemove.setDisable(false);

            try {
                setUserData(userObList.get(userRowNumber).getId());
            } catch (Exception ignored) {
            }
        });

        validation_Detail_Checked_User();
    }

    public void text_Field_Checker_In_User(KeyEvent keyEvent) {
        ValidationUtil.textFieldChecker(userHashMap, btnAdd, keyEvent);
    }

    private void validation_Detail_Checked_User() {
        userHashMap.put(txtUserName, Pattern.compile("^[A-z]{4,10}$"));
        userHashMap.put(txtEmail, Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b"));
        userHashMap.put(txtPassword, Pattern.compile("^([0-9]{4,10})$"));
    }

    public void clearTextFields() {
        txtUserId.clear();
        txtUserName.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    public void setUserID() throws SQLException, ClassNotFoundException {
        txtUserId.setText(userBO.generateNewUserId());
    }

    private void setUserData(String userID) throws Exception {
        List<UserDto> userDTOS = userBO.findAll();
        for (UserDto userDTO : userDTOS) {
            if (userDTO.getId().equals(userID)) {
                txtUserId.setText(userDTO.getId());
                txtUserName.setText(userDTO.getName());
                txtEmail.setText(userDTO.getEmail());
                txtPassword.setText(userDTO.getPassword());
            }
        }
    }

    public void loadUser() throws Exception {
        userObList.clear();
        List<UserDto> all = userBO.findAll();
        for (UserDto userDTO : all) {
            userObList.add(userDTO);
        }

        tblUser.setItems(userObList);
    }


    public void btnOnActionAdd(ActionEvent actionEvent) throws Exception {
        NotificationController.SuccessfulTableNotification();
        userBO.add(new UserDto(txtUserId.getText(), txtUserName.getText(), txtEmail.getText(), txtPassword.getText()));
        clearTextFields();
        setUserID();
        loadUser();
    }

    public void btnOnActionUpdate(ActionEvent actionEvent) {
        tblUser.refresh();
        UserDto userDTO = new UserDto(txtUserId.getText(), txtUserName.getText(), txtEmail.getText(), txtPassword.getText());

            try {
                if (userBO.update(userDTO)) {

                    NotificationController.UpdatetSuccess();

                    userObList.remove(userRowNumber);
                    userObList.add(userDTO);
                    loadUser();
                    clearTextFields();
                    setUserID();
                    tblUser.refresh();

                }
            } catch (Exception ignored) {
            }
        tblUser.refresh();
    }

    public void btnOnActionRemove(ActionEvent actionEvent) {
        tblUser.refresh();
        try {
            List<UserDto> all = userBO.findAll();
            all.removeIf(userDTO -> !userDTO.getId().equals(txtUserId.getText()));


            Alert alert = new Alert(Alert.AlertType.WARNING);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean bool = false;
                for (UserDto userDTO : all) {
                    bool = userBO.delete(userDTO.getId());
                }
                if (bool && userBO.delete(txtUserId.getText())) {
                    userObList.remove(userRowNumber);
                    loadUser();
                    clearTextFields();
                    setUserID();
                    tblUser.refresh();
                }
                NotificationController.UnSuccessfulTableNotification("Removed", "Removed");
            }
            loadUser();
        } catch (Exception ignored) {
        }
    }


    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }


}