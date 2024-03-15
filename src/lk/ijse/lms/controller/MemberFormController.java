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
import lk.ijse.lms.bo.MemberBO;
import lk.ijse.lms.dto.MemberDto;
import lk.ijse.lms.entity.Member;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;
import lk.ijse.lms.util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class MemberFormController {

    private final MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.MEMBER);

    private final LinkedHashMap<TextField, Pattern> memberHashMap = new LinkedHashMap<>();
    public AnchorPane root;
    public TextField txtMemberId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public TableView<MemberDto> tblMember;
    public TableColumn<Member, String> colMemberId;
    public TableColumn<Member, String> colName;
    public TableColumn<Member, String> colAddress;
    public TableColumn<Member, String> colContact;

    int memberRowNumber;

    int rowNumber;

    ObservableList<MemberDto> memberObList = FXCollections.observableArrayList();

    public void initialize() {
        txtMemberId.setEditable(false);

        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblMember.refresh();

        try {
            loadMember();
            setMemberID();
        } catch (Exception ignored) {

        }

        tblMember.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            memberRowNumber = (Integer) newValue;
            btnRemove.setDisable(false);
            btnAdd.setDisable(true);
            btnAdd.setDisable(false);

            try {
                setMemberData(memberObList.get(memberRowNumber).getMemberId());
            } catch (Exception ignored) {
            }
        });

        validation_Detail_Checked_Member();
    }

    public void text_Field_Checker_In_Member(KeyEvent keyEvent) {
        ValidationUtil.textFieldChecker(memberHashMap,btnAdd,keyEvent);
    }

    private void validation_Detail_Checked_Member() {
        memberHashMap.put(txtName, Pattern.compile("[A-Za-z]{3,}"));
        memberHashMap.put(txtAddress, Pattern.compile("[A-Za-z0-9/.\\s]{3,}"));
        memberHashMap.put(txtContact, Pattern.compile("(?:\\+?[0-9]+[\\s.-]?)?(?:[0-9][\\s.-]?){9,}"));

    }

    public void clearTextFields() {
        txtMemberId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    public void setMemberID() throws SQLException, ClassNotFoundException {
        txtMemberId.setText(memberBO.generateNewMemberId());
    }

    private void setMemberData(String memberId) throws Exception {
        List<MemberDto> memberDtos = memberBO.findAll();
        for (MemberDto memberDto : memberDtos) {
            if (memberDto.getMemberId().equals(memberId)) {
                txtMemberId.setText(memberDto.getMemberId());
                txtName.setText(memberDto.getName());
                txtAddress.setText(memberDto.getAddress());
                txtContact.setText(memberDto.getContact());
            }
        }
    }


    public void loadMember() throws Exception {

        memberObList.clear();
        List<MemberDto> all = memberBO.findAll();
        for (MemberDto memberDto : all) {
            memberObList.add(memberDto);
        }
        tblMember.setItems(memberObList);
    }


    public void btnOnActionAdd(ActionEvent actionEvent) throws Exception {
        memberBO.add(new MemberDto(
                txtMemberId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContact.getText()));
        NotificationController.SuccessfulTableNotification();
        clearTextFields();
        setMemberID();
        loadMember();
    }

    public void btnOnActionUpdate(ActionEvent actionEvent) {
        tblMember.refresh();
        MemberDto memberDto = new MemberDto(txtMemberId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == ButtonType.OK) {
            try {
                if (memberBO.update(memberDto)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();
                    setMemberID();
                    clearTextFields();
                    memberObList.remove(memberRowNumber);
                    memberObList.add(memberDto);
                    loadMember();
                    tblMember.refresh();
                    NotificationController.UpdatetSuccess();

                }
            } catch (Exception ignored) {
            }
        }
        tblMember.refresh();
    }

    public void btnOnActionRemove(ActionEvent actionEvent) {
        tblMember.refresh();
        try {
            List<MemberDto> all = memberBO.findAll();
            all.removeIf(memberDto -> !memberDto.getMemberId().equals(txtMemberId.getText()));


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean bool = false;
                for (MemberDto memberDto : all) {
                    bool = memberBO.delete(memberDto.getMemberId());
                }
                if (bool && memberBO.delete(txtMemberId.getText())) {

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();
                    setMemberID();clearTextFields();
                }
                NotificationController.UnSuccessfulTableNotification("Removed", "Removed");
            }
            loadMember();
        } catch (Exception ignored) {
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }


}


