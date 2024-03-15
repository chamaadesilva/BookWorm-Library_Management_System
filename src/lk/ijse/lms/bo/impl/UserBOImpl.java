package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.UserBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.UserDAO;
import lk.ijse.lms.dto.UserDto;
import lk.ijse.lms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean add(UserDto userDTO) throws Exception {
        return userDAO.add(new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        ));
    }

    @Override
    public List<UserDto> findAll() throws Exception {
        ArrayList<UserDto> userDTOS = new ArrayList<>();
        List<User> all = userDAO.findAll();
        for (User user : all) {
            userDTOS.add(new UserDto(
                    user.getId(), user.getName(), user.getEmail(), user.getPassword()
            ));
        }
        return userDTOS;
    }

    @Override
    public UserDto find(String id) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return userDAO.delete(id);
    }

    @Override
    public boolean update(UserDto userDTO) throws Exception {
        return userDAO.update(new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        ));
    }

    @Override
    public String generateNewUserId() throws SQLException, ClassNotFoundException {
        return userDAO.generateId();
    }
}