package lk.ijse.lms.bo;

import lk.ijse.lms.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO{
    boolean add(UserDto userDTO) throws Exception;

    List<UserDto> findAll() throws Exception;

    UserDto find(String id) throws Exception;

    boolean delete(String id) throws Exception;

    boolean update(UserDto userDTO) throws Exception;

    String generateNewUserId() throws SQLException, ClassNotFoundException;

}