package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.BookBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.BookDAO;
import lk.ijse.lms.dto.BookDto;
import lk.ijse.lms.entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    private final BookDAO bookDAO = (BookDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BOOK);
    @Override
    public boolean add(BookDto bookDto) throws Exception {
        return bookDAO.add(new Book(
                bookDto.getBookId(),
                bookDto.getAuthor(),
                bookDto.getTitle(),
                bookDto.getGenre(),
                bookDto.getAvailable(),
                null,null));
    }

    @Override
    public List<BookDto> findAll() throws Exception {
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        List<Book> all = bookDAO.findAll();
        for (Book book : all) {
            bookDtos.add(new BookDto(
                    book.getBookId(), book.getAuthor(), book.getTitle(), book.getGenre(), book.getAvailability(), book.getCreatedDate(),book.getUpdatedDate()
            ));
        }
        return bookDtos;
    }

    @Override
    public BookDto find(String id) throws Exception {
        Book book = bookDAO.find(id);
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getGenre(),
                book.getAuthor(),
                book.getAvailability(),
                book.getCreatedDate(),
                book.getUpdatedDate()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return bookDAO.delete(id);
    }

    @Override
    public boolean update(BookDto bookDto) throws Exception {
        return bookDAO.update(new Book(
                bookDto.getBookId(),
                bookDto.getAuthor(),
                bookDto.getTitle(),
                bookDto.getGenre(),
                bookDto.getAvailable(),
                bookDto.getCreatedDate(),
                bookDto.getUpdatedDate()
        ));
    }

    @Override
    public String generateNewBookId() throws SQLException, ClassNotFoundException {
        return bookDAO.generateId();
    }

}
