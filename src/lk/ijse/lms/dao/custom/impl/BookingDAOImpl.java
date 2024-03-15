package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.BookingDAO;
import lk.ijse.lms.entity.Bookings;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    @Override
    public boolean add(Bookings entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Bookings entity) throws Exception {
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

        Query query = session.createQuery(Constants.BOOKINGS_DELETE);
        query.setParameter("id", s);

        if (query.executeUpdate() > 0) {
            bool = true;
        }

        transaction.commit();
        session.close();
        return bool;
    }

    @Override
    public Bookings find(String s) throws Exception {
        return null;
    }

    @Override
    public List<Bookings> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(Constants.FIND_ALL_BOOKINGS);
        List<Bookings> list = query.list();


        transaction.commit();

        session.close();
        return list;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query sqlQuery = session.createSQLQuery(Constants.GENERATE_BOOKINGS_ID);
        List<String> idlist = sqlQuery.list();
        transaction.commit();
        session.close();

        for (String id : idlist) {
            if (id != null) {
                int newStudentId = Integer.parseInt(id.replace("BBI-", "")) + 1;
                return String.format("BBI-%03d", newStudentId);
            }
        }
        return "BBI-001";
    }
}
