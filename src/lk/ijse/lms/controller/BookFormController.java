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
import lk.ijse.lms.bo.BookBO;
import lk.ijse.lms.dto.BookDto;
import lk.ijse.lms.dto.LibraryBranchDto;
import lk.ijse.lms.entity.Book;
import lk.ijse.lms.util.NotificationController;
import lk.ijse.lms.util.UILoader;
import lk.ijse.lms.util.ValidationUtil;
import net.sf.ehcache.search.expression.Not;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BookFormController {

    private final BookBO bookBO = (BookBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.BOOK);
    private final LinkedHashMap<TextField, Pattern> bookHashMap = new LinkedHashMap<>();

    public AnchorPane root;
    public TextField txtBookId;
    public TextField txtAuthor;
    public TextField txtTitle;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public DatePicker txtCreatedDate;
    public DatePicker txtUpdatedDate;
    public TextField txtGenre;
    public TextField txtAvailabel;
    public TableView<BookDto> tblBook;
    public TableColumn<Book,String> colBookId;
    public TableColumn<Book,String> colAuthor;
    public TableColumn<Book,String> colTitle;
    public TableColumn<Book,String> colGenre;
    public TableColumn<Book,String> colAvailabel;
    public TableColumn<Book,String> colCreatedDate;
    public TableColumn<Book,String> colUpdatedDate;

    int bookRowNumber;

    int rowNumber;

    ObservableList<BookDto> bookObList = FXCollections.observableArrayList();


    public void initialize() {

        txtBookId.setEditable(false);
        txtAuthor.requestFocus();

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailabel.setCellValueFactory(new PropertyValueFactory<>("available"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colUpdatedDate.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));
        tblBook.refresh();

        try {
            loadBook();
            setBookID();
        } catch (Exception ignored) {
        }

        tblBook.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            bookRowNumber = (Integer) newValue;
            btnRemove.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);

            try {
                setBookData(bookObList.get(bookRowNumber).getBookId());
            } catch (Exception ignored) {
            }
        });

        validation_Detail_Checked_User_Book();
    }

    public void text_Field_Checker_In_Book(KeyEvent keyEvent) {
        ValidationUtil.textFieldChecker(bookHashMap, btnAdd, keyEvent);
    }

    public void validation_Detail_Checked_User_Book(){
        bookHashMap.put(txtAuthor, Pattern.compile("[A-Za-z]{3,}"));
        bookHashMap.put(txtTitle, Pattern.compile("[A-Za-z]{3,}"));
        bookHashMap.put(txtGenre, Pattern.compile("[A-Za-z0-9/.\\s]{3,}"));
        bookHashMap.put(txtAvailabel, Pattern.compile("[A-Za-z]{3,}"));



    }

    public void setBookID() throws SQLException, ClassNotFoundException {
        txtBookId.setText(bookBO.generateNewBookId());
    }

    public void clearTextFields() {
        txtBookId.clear();
        txtAuthor.clear();
        txtTitle.clear();
        txtGenre.clear();
        txtAvailabel.clear();
    }

    private void setBookData(String bookId) throws Exception {
        List<BookDto> bookDtos = bookBO.findAll();
        for (BookDto bookDto : bookDtos) {
            if (bookDto.getBookId().equals(bookId)) {
                txtBookId.setText(bookDto.getBookId());
                txtAuthor.setText(bookDto.getAuthor());
                txtTitle.setText(bookDto.getTitle());
                txtGenre.setText(bookDto.getGenre());
                txtAvailabel.setText(bookDto.getAvailable());
                txtCreatedDate.setValue(LocalDate.parse((CharSequence) bookDto.getCreatedDate()));
                txtUpdatedDate.setValue(LocalDate.parse((CharSequence) bookDto.getUpdatedDate()));
            }
        }
    }


    public void loadBook() throws Exception {
        bookObList.clear();
        List<BookDto> all = bookBO.findAll();
        bookObList.addAll(all);
        tblBook.setItems(bookObList);
    }


    public void btnOnActionAdd(ActionEvent actionEvent) throws Exception {
        bookBO.add(new BookDto(txtBookId.getText(),
                txtAuthor.getText(), txtTitle.getText(),
                txtGenre.getText(),txtAvailabel.getText(),
                null,null));
        clearTextFields();
        setBookID();
        loadBook();
        NotificationController.SuccessfulTableNotification();
    }

    public void btnOnActionUpdate(ActionEvent actionEvent) {
        tblBook.refresh();
        BookDto bookDto = new BookDto(
                txtBookId.getText(),
                txtAuthor.getText(),
                txtTitle.getText(),
                txtGenre.getText(),
                txtAvailabel.getText(),
                null,
                null
        );

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == ButtonType.OK) {
            try {
                if (bookBO.update(bookDto)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();

                    bookObList.remove(bookRowNumber);
                    bookObList.add(bookDto);
                    loadBook();
                    clearTextFields();
                    setBookID();
                    tblBook.refresh();
                    NotificationController.UpdatetSuccess();
                }
            } catch (Exception ignored) {
            }
        }
        tblBook.refresh();
    }

    public void btnOnActionRemove(ActionEvent actionEvent) {
        tblBook.refresh();
        try {
            List<BookDto> all = bookBO.findAll();
            all.removeIf(bookDto -> !bookDto.getBookId().equals(txtBookId.getText()));


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean bool = false;
                for (BookDto bookDto : all) {
                    bool = bookBO.delete(bookDto.getBookId());
                }
                if (bool && bookBO.delete(txtBookId.getText())) {

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Message");
                    alert2.setContentText("Saved..");
                    alert2.show();
                    clearTextFields();
                    setBookID();
                }
                NotificationController.UnSuccessfulTableNotification("Removed", "Removed");

            }
            loadBook();
        } catch (Exception ignored) {
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.load("MainForm", root);
    }


}
