package lk.ijse.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lms.bo.BOFactory;
import lk.ijse.lms.bo.BookingBO;
import lk.ijse.lms.bo.SearchBO;
import lk.ijse.lms.dto.BookingsDto;
import lk.ijse.lms.entity.Bookings;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.entity.Member;
import lk.ijse.lms.util.UILoader;

import java.io.IOException;
import java.util.List;

public class SearchHistoryFormController {
    public TextField searchField;
    ObservableList<BookingsDto> bookingObList = FXCollections.observableArrayList();
    ObservableList<BookingsDto> tempObList = FXCollections.observableArrayList();
    private final BookingBO bookingBO = (BookingBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.BOOKING);
    private final SearchBO searchBO = (SearchBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.SEARCHBO);

    public AnchorPane root;
    public TableView <BookingsDto>tblSearchHistory;
    public TableColumn colBorrowId;
    public TableColumn colMemberId;
    public TableColumn colBranchName;
    public TableColumn colPlaceDate;
    public TableColumn colStatus;
    public ImageView search;

    public void initialize() {
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("mID"));
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("bID"));
        colPlaceDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        try {
            loadBooking();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadBooking() throws Exception {
        bookingObList.clear();
        List<BookingsDto> all = bookingBO.findAll();
        bookingObList.addAll(all);
        tblSearchHistory.setItems(bookingObList);
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }





    public void searchOnAction(MouseEvent mouseEvent) {

    }

    public void searchHistoryOnKeyReleased(KeyEvent keyEvent) throws Exception {

        if (searchField.getText().isEmpty() || searchField.getText() == null || searchField.getText().trim().isEmpty()){
            loadBooking();
        }else {
            bookingObList.clear();
            BookingsDto bookingsDto = searchBO.find(searchField.getText());
            bookingObList.add(bookingsDto);
            tblSearchHistory.setItems(bookingObList);
        }
    }
}
