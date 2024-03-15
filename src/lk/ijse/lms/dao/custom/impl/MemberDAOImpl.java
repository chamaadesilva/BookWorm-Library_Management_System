package lk.ijse.lms.dao.custom.impl;

import lk.ijse.lms.dao.custom.MemberDAO;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.entity.Member;
import lk.ijse.lms.entity.User;
import lk.ijse.lms.util.Constants;
import lk.ijse.lms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public boolean add(Member entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Member entity) throws Exception {
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
        Query query = session.createQuery(Constants.DELETE_MEMBER);
        query.setParameter("id", s);
        if (query.executeUpdate() > 0) {
            bool = true;
        }
        transaction.commit();
        session.close();
        return bool;
    }

    @Override
    public Member find(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Member member = session.get(Member.class, s);

        Query query = session.createQuery(Constants.FIND_MEMBER);

        System.out.println(query);

        transaction.commit();

        session.close();
        return member;
    }

    @Override
    public List<Member> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query program = session.createQuery(Constants.FIND_ALL_MEMBER);
        List<Member> list = program.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query sqlQuery = session.createSQLQuery(Constants.GENERATE_MEMBER_ID);
        List<String> idlist = sqlQuery.list();

        transaction.commit();
        session.close();

        for (String id : idlist) {
            if (id != null) {
                int newStudentId = Integer.parseInt(id.replace("MEM-", "")) + 1;
                return String.format("MEM-%03d", newStudentId);
            }
        }
        return "MEM-001";
    }
}
