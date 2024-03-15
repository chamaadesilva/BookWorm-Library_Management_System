package lk.ijse.lms.bo;

import lk.ijse.lms.bo.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case USER:
                return new UserBOImpl();
            case LIBRARYBRANCH:
                return new LibraryBranchBOImpl();
            case BOOK:
                return new BookBOImpl();
            case MEMBER:
                return new MemberBoImpl();
            case BOOKING:
                return new BookingBOImpl();
            case SEARCHBO:
                return new SearchBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        USER, LIBRARYBRANCH, BOOK, MEMBER, BOOKING,SEARCHBO
    }
}