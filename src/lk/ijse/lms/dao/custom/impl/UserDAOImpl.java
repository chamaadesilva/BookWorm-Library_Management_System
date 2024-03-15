package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.UserDAO;
import lk.ijse.lms.entity.User;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws Exception {
        boolean bool = false;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(Constants.USER_DELETE);
        query.setParameter("id", s);
        if (query.executeUpdate() > 0) {
            bool = true;
        }
        transaction.commit();
        session.close();
        return bool;
    }

    @Override
    public User find(String s) throws Exception {
        return null;
    }


    @Override
    public List<User> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query program = session.createQuery(Constants.USER_FIND_ALL);
        List<User> list = program.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query sqlQuery = session.createSQLQuery(Constants.USER_GENERATE_ID);
        List<String> idlist = sqlQuery.list();

        transaction.commit();
        session.close();

        for (String id : idlist) {
            if (id != null) {
                int newStudentId = Integer.parseInt(id.replace("USR-", "")) + 1;
                return String.format("USR-%03d", newStudentId);
            }
        }
        return "USR-001";
    }
}