package lk.ijse.lms.dao;

import lk.ijse.lms.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case LIBRARYBRANCH:
                return new LibraryBranchDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case MEMBER:
                return new MemberDAOImpl();
            case BOOKINGS:
                return new BookingDAOImpl();
            case SEARCHDAO:
                return new SearchDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        USER,LIBRARYBRANCH,BOOK,MEMBER,BOOKINGS,SEARCHDAO
    }
}