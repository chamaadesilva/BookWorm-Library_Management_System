package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.SearchDAO;
import lk.ijse.lms.entity.Bookings;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class SearchDAOImpl implements SearchDAO {
    @Override
    public boolean add(Bookings entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Bookings entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public Bookings find(String s) throws Exception {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Bookings member = session.get(Bookings.class, s);

        Query query = session.createQuery(Constants.FIND);

        System.out.println(query);

        transaction.commit();

        session.close();
        return member;

//    Bookings bookings = null;
//    try {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("FROM Bookings WHERE ");
//
//        String[] parts = s.split("-");
//
//        if ("BBI".equals(parts[0].trim())) {
//            stringBuffer.append("id = :s");
//        } else if ("BK".equals(parts[0].trim())) {
//            stringBuffer.append("book_ID = :s");
//        } else if ("MEM".equals(parts[0].trim())) {
//            stringBuffer.append("member_ID = :s");
//        }
//
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        Query query = session.createQuery(stringBuffer.toString());
//        query.setParameter("s", s);
//        bookings = (Bookings) query.uniqueResult();
//        transaction.commit();
//        session.close();
//    } catch (Exception ignored) {
//
//    }
//    return bookings;
    }

    @Override
    public List<Bookings> findAll() throws Exception {
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
