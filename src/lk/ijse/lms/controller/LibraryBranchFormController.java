package lk.ijse.lms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.bo.BOFactory;
import lk.ijse.lms.bo.LibraryBranchBO;
import lk.ijse.lms.dto.LibraryBranchDto;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;
import lk.ijse.lms.util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class LibraryBranchFormController {

    private final LibraryBranchBO libraryBranchBO = (LibraryBranchBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.LIBRARYBRANCH);
    private final LinkedHashMap<TextField, Pattern> libraryBranchHashMap = new LinkedHashMap<>();

    public AnchorPane root;
    public TextField txtLibId;
    public TextField txtName;
    public TextField txtLocation;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public DatePicker txtCreatedDate;
    public DatePicker txtUpdatedDate;
    public TableView<LibraryBranchDto> tblLibraryBranch;
    public TableColumn<LibraryBranchDto, String> colLibId;
    public TableColumn<LibraryBranchDto, String> colName;
    public TableColumn<LibraryBranchDto, String> colLocation;
    public TableColumn<LibraryBranchDto, String> colCreatedDate;
    public TableColumn<LibraryBranchDto, String> colUpdatedDate;


    int libraryBranchRowNumber;

    int rowNumber;

    ObservableList<LibraryBranchDto> libraryBranchObList = FXCollections.observableArrayList();

    public void initialize() {

        txtLibId.setEditable(false);
        txtName.requestFocus();

        colLibId.setCellValueFactory(new PropertyValueFactory<>("libId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colUpdatedDate.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));

        tblLibraryBranch.refresh();

        try {
            loadLibraryBranch();
            setLibraryBranchID();
        } catch (Exception ignored) {

        }

        tblLibraryBranch.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            libraryBranchRowNumber = (Integer) newValue;
            btnRemove.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);

            try {
                setLibraryBranchData(libraryBranchObList.get(libraryBranchRowNumber).getLibId());
            } catch (Exception ignored) {
            }
        });
        validation_Detail_Checked_LibraryBranch();
    }

    public void text_Field_Checker_In_LibraryBranch(KeyEvent keyEvent) {
        ValidationUtil.textFieldChecker(libraryBranchHashMap, btnAdd, keyEvent);
    }

    private void validation_Detail_Checked_LibraryBranch(){
        libraryBranchHashMap.put(txtName, Pattern.compile("[A-Za-z]{3,}"));
        libraryBranchHashMap.put(txtLocation, Pattern.compile("[A-Za-z0-9/.\\s]{3,}"));
    }

    public void clearTextFields() {
        txtLibId.clear();
        txtName.clear();
        txtLocation.clear();
    }

    private void setLibraryBranchData(String libId) throws Exception {
        btnAdd.setDisable(false);
        List<LibraryBranchDto> libraryBranchDTOS = libraryBranchBO.findAll();
        for (LibraryBranchDto libraryBranchDto : libraryBranchDTOS) {
            if (libraryBranchDto.getLibId().equals(libId)) {
                txtLibId.setText(libraryBranchDto.getLibId());
                txtName.setText(libraryBranchDto.getName());
                txtLocation.setText(libraryBranchDto.getLocation());
                txtCreatedDate.setValue(libraryBranchDto.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                txtUpdatedDate.setValue(libraryBranchDto.getUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        }
    }

    public void setLibraryBranchID() throws SQLException, ClassNotFoundException {
        txtLibId.setText(libraryBranchBO.generateNewBranchId());
    }

    public void loadLibraryBranch() throws Exception {
        libraryBranchObList.clear();
        List<LibraryBranchDto> all = libraryBranchBO.findAll();
        libraryBranchObList.addAll(all);
        tblLibraryBranch.setItems(libraryBranchObList);
    }

    public void btnOnActionAdd(ActionEvent actionEvent) throws Exception {
        libraryBranchBO.add(new LibraryBranchDto(
                txtLibId.getText(),
                txtName.getText(),
                txtLocation.getText(),
                null,
                null
        ));
        NotificationController.SuccessfulTableNotification();
        clearTextFields();
        setLibraryBranchID();
        loadLibraryBranch();
    }

    public void btnOnActionUpdate(ActionEvent actionEvent) {
        tblLibraryBranch.refresh();

        LibraryBranchDto libraryBranchDto = new LibraryBranchDto(
                txtLibId.getText(),
                txtName.getText(),
                txtLocation.getText(),
                null,
                null
        );

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                if (libraryBranchBO.update(libraryBranchDto)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    libraryBranchObList.remove(libraryBranchRowNumber);
                    libraryBranchObList.add(libraryBranchDto);
                    loadLibraryBranch();
                    clearTextFields();
                    setLibraryBranchID();
                    tblLibraryBranch.refresh();
                }
                NotificationController.UpdatetSuccess();
            } catch (Exception ignored) {}
        }
        tblLibraryBranch.refresh();
    }

    public void btnOnActionRemove(ActionEvent actionEvent) {
        tblLibraryBranch.refresh();
        try {
            List<LibraryBranchDto> all = libraryBranchBO.findAll();
            all.removeIf(libraryBranchDto -> !libraryBranchDto.getLibId().equals(txtLibId.getText()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean bool = false;
                for (LibraryBranchDto libraryBranchDto : all) {
                    bool = libraryBranchBO.delete(libraryBranchDto.getLibId());
                }

                if (bool && libraryBranchBO.delete(txtLibId.getText())) {

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();
                    setLibraryBranchID();
                }
                loadLibraryBranch();
                NotificationController.UnSuccessfulTableNotification("Removed", "Removed");

            }
            loadLibraryBranch();
            setLibraryBranchID();
        } catch (Exception ignored) {
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }



}
