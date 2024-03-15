package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.LibraryBranchDAO;
import lk.ijse.lms.entity.Book;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.entity.User;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class LibraryBranchDAOImpl implements LibraryBranchDAO {
    @Override
    public boolean add(LibraryBranch entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(LibraryBranch entity) throws Exception {
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
        Query query = session.createQuery(Constants.DELETE_LIBRARY_BRANCH);
        query.setParameter("id", s);
        if (query.executeUpdate() > 0) {
            bool = true;
        }
        transaction.commit();
        session.close();
        return bool;

    }

    @Override
    public LibraryBranch find(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        LibraryBranch libraryBranch = session.get(LibraryBranch.class, s);

        Query query = session.createQuery(Constants.FIND_LIBRARY_BRANCH);

        System.out.println(query);

        transaction.commit();

        session.close();
        return libraryBranch;
    }

    @Override
    public List<LibraryBranch> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query program = session.createQuery(Constants.FIND_ALL_LIBRARY_BRANCH);
        List<LibraryBranch> list = program.list();
        transaction.commit();
        session.close();

        return list;

    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "";
        Query sqlQuery = session.createSQLQuery(Constants.GENERATE_LIBRARY_BRANCH_ID);
        List<String> idlist = sqlQuery.list();

        transaction.commit();
        session.close();

        for (String id : idlist) {
            if (id != null) {
                int newStudentId = Integer.parseInt(id.replace("LIB-", "")) + 1;
                return String.format("LIB-%03d", newStudentId);
            }
        }
        return "LIB-001";
    }
}
