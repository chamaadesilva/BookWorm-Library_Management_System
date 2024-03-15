package lk.ijse.lms.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.lms.bo.*;
import lk.ijse.lms.dto.BookDto;
import lk.ijse.lms.dto.BookingsDto;
import lk.ijse.lms.dto.LibraryBranchDto;
import lk.ijse.lms.dto.MemberDto;
import lk.ijse.lms.entity.Bookings;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.UILoader;
import lombok.SneakyThrows;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
public class BookingFormController {
    private final UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.USER);
    private final MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.MEMBER);
    private final BookBO bookBO = (BookBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.BOOK);
    private final LibraryBranchBO libraryBranchBO = (LibraryBranchBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.LIBRARYBRANCH);

    private final SearchBO searchBO = (SearchBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.SEARCHBO);

    private final BookingBO bookingBO = (BookingBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.BOOKING);
    public Label timeLbl;
    public Label dateLbl;
    public Button SaveBtn;
    public Button updateBtn;
    public Button removeBtn;
    public TextField searchTxt;
    public AnchorPane root;
    public TableView<BookingsDto> tblBookRecord;
    public TableColumn<Bookings, String> colMemberId;
    public TableColumn colBookId;
    public TableColumn colBorrowDate;
    public TableColumn colReturnDate;
    public TableColumn<Bookings, String> colStatus;
    public TableColumn<Bookings, String> colBorrowId;
    public TableColumn<Bookings, String> colBranchName;
    public TableColumn<Bookings, String> colPlaceDate;
    public JFXComboBox selectBookCmb;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public JFXComboBox memberIdOnTransaction;
    public JFXComboBox branchNameCmb;
    public TextField borrowId;
    public TextField textbookName;
    public TextField subbranchName;
    public TextField txtbookName;
    public TextField txtbranchName;
    ObservableList<BookingsDto> bookingObList = FXCollections.observableArrayList();
    int userRowNumber;

    public void initialize() throws Exception {


        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("mID"));
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("bID"));
        colPlaceDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            setBookingID();
            loadStudents();
            loadLibraryBranch();
            loadMember();
            loadBooking();
            loadDateAndTimeForPlaceOrderForm();
        } catch (Exception ignored) {
        }

        tblBookRecord.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            userRowNumber = (Integer) newValue;
            SaveBtn.setDisable(true);
            updateBtn.setDisable(false);
            removeBtn.setDisable(false);

            try {
                setBookings(bookingObList.get(userRowNumber).getId());
            } catch (Exception ignored) {
            }
        });

        setSelectBookCmb();

        branchNameCmb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                LibraryBranchDto roomDTO = libraryBranchBO.find((String) newValue);
                txtbranchName.setText(roomDTO.getName());
            } catch (Exception ignored) {
            }
        });

        memberIdOnTransaction.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                MemberDto roomDTO = memberBO.find((String) newValue);
                txtName.setText(roomDTO.getName());
                txtAddress.setText(roomDTO.getAddress());
                txtContact.setText(roomDTO.getContact());
            } catch (Exception ignored) {
            }
        });

    }

    public void setSelectBookCmb(){
        selectBookCmb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                BookDto roomDTO = bookBO.find((String) newValue);
                txtbookName.setText(roomDTO.getTitle());
            } catch (Exception ignored) {
            }
        });
    }

    private void setBookings(String bookingsId) throws Exception {
        List<BookingsDto> all = bookingBO.findAll();
        for (BookingsDto bookingsDto : all) {
            if (bookingsDto.getId().equals(bookingsId)) {
                selectBookCmb.setValue(bookingsDto.getBID());
                memberIdOnTransaction.setValue(bookingsDto.getMID());
                branchNameCmb.setValue(bookingsDto.getLID());
                borrowId.setText(bookingsDto.getId());
            }
        }
    }


    private void loadDateAndTimeForPlaceOrderForm() {


        //Set Date
        dateLbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            timeLbl.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }


    public void loadStudents() throws Exception {
        List<BookDto> bookDtos = bookBO.findAll();
        List<String> idList = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            if (!bookDto.getAvailable().equals("RESERVED!")) {
                idList.add(bookDto.getBookId());
            }
        }
        selectBookCmb.getItems().addAll(idList);
    }

    public void loadLibraryBranch() throws Exception {
        List<LibraryBranchDto> roomDTOList = libraryBranchBO.findAll();
        List<String> idList = new ArrayList<>();
        for (LibraryBranchDto libraryBranchDto : roomDTOList) {
            idList.add(libraryBranchDto.getLibId());
        }
        branchNameCmb.getItems().addAll(idList);
    }


    public void loadMember() throws Exception {
        List<MemberDto> roomDTOList = memberBO.findAll();
        List<String> idList = new ArrayList<>();
        for (MemberDto libraryBranchDto : roomDTOList) {
            idList.add(libraryBranchDto.getMemberId());
        }
        memberIdOnTransaction.getItems().addAll(idList);
    }


    public void loadBooking() throws Exception {
        bookingObList.clear();
        List<BookingsDto> all = bookingBO.findAll();
        List<BookingsDto> filteredArray = new ArrayList<>();
        for (BookingsDto bookingsDto : all) {
            if (!bookingsDto.getStatus().equals(Constants.AVAILABLE)) {
                filteredArray.add(bookingsDto);
            }
        }
        bookingObList.addAll(filteredArray);
        tblBookRecord.setItems(bookingObList);
    }

    public void setBookingID() throws SQLException, ClassNotFoundException {
        borrowId.setText(bookingBO.generateNewBookId());
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }


    @SneakyThrows
    public void SaveOnAction(ActionEvent actionEvent) {
        try {
            bookingBO.add(new BookingsDto(borrowId.getText(), memberIdOnTransaction.getSelectionModel().getSelectedItem().toString(), selectBookCmb.getSelectionModel().getSelectedItem().toString(), branchNameCmb.getSelectionModel().getSelectedItem().toString(), null, null));

            BookDto bookDto = bookBO.find(selectBookCmb.getSelectionModel().getSelectedItem().toString());
            bookDto.setAvailable(Constants.RESERVED);
            bookBO.update(bookDto);
            loadBooking();
            setBookingID();setSelectBookCmb();

        } catch (Exception ignored) {
        }
    }


    public void UpdateOnAction(ActionEvent actionEvent) {
        try {
            bookingBO.update(new BookingsDto(borrowId.getText(), memberIdOnTransaction.getSelectionModel().getSelectedItem().toString(), selectBookCmb.getSelectionModel().getSelectedItem().toString(), null, null));
            loadBooking();
            setBookingID();
        } catch (Exception ignored) {
        }
    }

    public void RemoveOnAction(ActionEvent actionEvent) throws Exception {
        BookDto bookDto = bookBO.find(selectBookCmb.getSelectionModel().getSelectedItem().toString());
        bookDto.setAvailable(Constants.AVAILABLE);
        bookBO.update(bookDto);
        loadBooking();
    }

    public void searchInBookingForm(KeyEvent keyEvent) throws Exception {
        if (searchTxt.getText().isEmpty() || searchTxt.getText() == null || searchTxt.getText().trim().isEmpty()) {
            loadBooking();
        } else {
            bookingObList.clear();
            BookingsDto bookingsDto = searchBO.find(searchTxt.getText());
            bookingObList.add(bookingsDto);
            tblBookRecord.setItems(bookingObList);
        }
    }
}


