package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.BookingBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.BookDAO;
import lk.ijse.lms.dao.custom.BookingDAO;
import lk.ijse.lms.dao.custom.LibraryBranchDAO;
import lk.ijse.lms.dao.custom.MemberDAO;
import lk.ijse.lms.dto.BookingsDto;
import lk.ijse.lms.entity.Book;
import lk.ijse.lms.entity.Bookings;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.util.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingBOImpl implements BookingBO {

    private final BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BOOKINGS);
    private final MemberDAO memberDAO = (MemberDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBER);
    private final BookDAO bookDAO = (BookDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.BOOK);
    private final LibraryBranchDAO libraryBranchDAO = (LibraryBranchDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.LIBRARYBRANCH);

    @Override
    public boolean add(BookingsDto bookDto) throws Exception {
        return bookingDAO.add(new Bookings(
                bookDto.getId(),
                memberDAO.find(bookDto.getMID()),
                bookDAO.find(bookDto.getBID()),
                libraryBranchDAO.find(bookDto.getLID()),
                bookDto.getCreatedDate(),
                bookDto.getUpdatedDate()
        ));
    }

    @Override
    public List<BookingsDto> findAll() throws Exception {

        List<BookingsDto> bookingsDtos = new ArrayList<>();
        for (Bookings bookings : bookingDAO.findAll()) {
            bookingsDtos.add(new BookingsDto(
                    bookings.getId(),
                    bookings.getMID().getMemberId(),
                    bookings.getBID().getBookId(),
                    bookings.getLID().getLibId(),
                    bookings.getCreatedDate(),
                    bookings.getUpdatedDate(),
                    bookings.getBID().getAvailability()
            ));
        }

        return bookingsDtos;
    }


    @Override
    public BookingsDto find(String id) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return bookingDAO.delete(id);
    }

    @Override
    public boolean update(BookingsDto bookDto) throws Exception {
        return bookingDAO.update(new Bookings(
                bookDto.getId(),
                memberDAO.find(bookDto.getMID()),
                bookDAO.find(bookDto.getBID()),
                libraryBranchDAO.find(bookDto.getLID()),
                bookDto.getCreatedDate(),
                bookDto.getUpdatedDate()
        ));

    }

    @Override
    public String generateNewBookId() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateId();
    }
}
