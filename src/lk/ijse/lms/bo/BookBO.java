package lk.ijse.lms.bo;

import lk.ijse.lms.dto.BookDto;
import lk.ijse.lms.dto.LibraryBranchDto;

import java.sql.SQLException;
import java.util.List;

public interface BookBO extends SuperBO{
    boolean add(BookDto bookDto)throws Exception;

    List<BookDto> findAll() throws Exception;

    BookDto find(String id) throws Exception;

    boolean delete(String id) throws Exception;

    boolean update(BookDto bookDto) throws Exception;

    String generateNewBookId() throws SQLException, ClassNotFoundException;
}
