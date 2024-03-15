package lk.ijse.lms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.util.UILoader;

import java.io.IOException;

public class MainFormController {
    public AnchorPane root;
    public JFXButton btnBook;
    public JFXButton btnLibraryBranch;
    public JFXButton btnMember;
    public JFXButton logOutBtn;

    public void openBookDetail(MouseEvent mouseEvent) throws IOException {
        UILoader.load("BookForm", root);
    }

    public void openLibraryBranchDetails(MouseEvent mouseEvent) throws IOException {
        UILoader.load("LibraryBranchForm", root);
    }

    public void userOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("UserForm", root);
    }

    public void BookingOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("BookingForm", root);
    }



    public void openMemberDetails(MouseEvent mouseEvent) throws IOException {
        UILoader.load("MemberForm", root);
    }


    public void SearchHistoryOnAction(ActionEvent event) throws IOException {
        UILoader.load("SearchHistoryForm", root);
    }

    public void logOtClicked(ActionEvent actionEvent) throws IOException {
        UILoader.load("LoginPageForm", root);
    }
}
