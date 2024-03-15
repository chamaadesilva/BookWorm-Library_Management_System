package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.BookDAO;
import lk.ijse.lms.entity.Book;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean add(Book entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Book entity) throws Exception {
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
        Query query = session.createQuery(Constants.DELETE_BOOK);
        query.setParameter("id", s);
        if (query.executeUpdate() > 0) {
            bool = true;
        }
        transaction.commit();
        session.close();
        return bool;
    }

    @Override
    public Book find(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Book book = session.get(Book.class, s);

        Query query = session.createQuery(Constants.FIND_BOOK);

        System.out.println(query);

        transaction.commit();

        session.close();
        return book;
    }

    @Override
    public List<Book> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query program = session.createQuery(Constants.FIND_ALL_BOOKS);
        List<Book> list = program.list();
        transaction.commit();
        session.close();

        return list;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query sqlQuery = session.createSQLQuery(Constants.GENERATE_BOOK_ID);
        List<String> idlist = sqlQuery.list();

        transaction.commit();
        session.close();

        for (String id : idlist) {
            if (id != null) {
                int newStudentId = Integer.parseInt(id.replace("BK-", "")) + 1;
                return String.format("BK-%03d", newStudentId);
            }
        }
        return "BK-001";
    }
}
