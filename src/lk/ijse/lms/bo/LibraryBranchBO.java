package lk.ijse.lms.bo;

import lk.ijse.lms.dto.LibraryBranchDto;

import java.sql.SQLException;
import java.util.List;

public interface LibraryBranchBO extends SuperBO {
    boolean add(LibraryBranchDto libraryBranchDto)throws Exception;

    List<LibraryBranchDto> findAll() throws Exception;

    LibraryBranchDto find(String id) throws Exception;

    boolean delete(String id) throws Exception;

    boolean update(LibraryBranchDto libraryBranchDto) throws Exception;

    String generateNewBranchId() throws SQLException, ClassNotFoundException;
}
