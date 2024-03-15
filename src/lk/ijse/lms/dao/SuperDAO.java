package lk.ijse.lms.dao;

import lk.ijse.lms.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface SuperDAO<Entity extends SuperEntity, ID> {
    boolean add(Entity entity) throws Exception;

    boolean update(Entity entity) throws Exception;

    boolean delete(ID id) throws Exception;

    Entity find(ID id) throws Exception;

    List<Entity> findAll() throws Exception;

    String generateId() throws SQLException, ClassNotFoundException;
}
