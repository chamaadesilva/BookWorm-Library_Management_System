package lk.ijse.lms.bo;

import lk.ijse.lms.dto.BookDto;
import lk.ijse.lms.dto.BookingsDto;

import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {

    boolean add(BookingsDto bookDto)throws Exception;

    List<BookingsDto> findAll() throws Exception;

    BookingsDto find(String id) throws Exception;

    boolean delete(String id) throws Exception;

    boolean update(BookingsDto bookDto) throws Exception;

    String generateNewBookId() throws SQLException, ClassNotFoundException;

}
