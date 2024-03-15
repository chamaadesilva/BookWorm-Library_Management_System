package lk.ijse.lms.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class is annotated with @NoArgsConstructor from the Lombok library.
 * The @NoArgsConstructor annotation generates a constructor with no parameters.
 * The 'access' attribute is set to AccessLevel.PRIVATE, which means the generated constructor will be private.
 * This is often used in utility classes, where instances are not supposed to be created.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String RESERVED = "RESERVED!";
    public static final String AVAILABLE = "AVAILABLE!";

    /** Queries for User */
    public static final String USER_DELETE = "DELETE FROM User WHERE id=:id";
    public static final String USER_FIND_ALL = "FROM User";
    public static final String USER_GENERATE_ID = "SELECT id FROM User ORDER BY id DESC LIMIT 1";

    /** Queries for Search */
    public static final String FIND = "FROM Bookings WHERE id=:id";

    /** Queries for Member */
    public static final String DELETE_MEMBER = "DELETE FROM Member WHERE id=:id";
    public static final String FIND_MEMBER = "SELECT name,address,contact FROM Member ORDER BY memberId DESC";
    public static final String FIND_ALL_MEMBER = "FROM Member";
    public static final String GENERATE_MEMBER_ID = "SELECT memberId FROM Member ORDER BY memberId DESC LIMIT 1";

    /** Queries for LibraryBranch */
    public static final String DELETE_LIBRARY_BRANCH = "DELETE FROM LibraryBranch WHERE id=:id";
    public static final String FIND_LIBRARY_BRANCH = "SELECT name FROM LibraryBranch ORDER BY libId DESC";
    public static final String FIND_ALL_LIBRARY_BRANCH = "FROM LibraryBranch";
    public static final String GENERATE_LIBRARY_BRANCH_ID = "SELECT libId FROM LibraryBranch ORDER BY libId DESC LIMIT 1";


    /** Queries for Bookings */
    public static final String BOOKINGS_DELETE = "DELETE FROM Bookings WHERE bID=:id";
    public static final String FIND_ALL_BOOKINGS = "FROM Bookings ";
    public static final String GENERATE_BOOKINGS_ID = "SELECT id FROM Bookings ORDER BY id DESC LIMIT 1";


    /** Queries for Book */
    public static final String DELETE_BOOK = "DELETE FROM Book WHERE id=:id";
    public static final String FIND_BOOK = "SELECT bookId,author,title,genre,availability,createdDate,updatedDate FROM Book ORDER BY bookId DESC";
    public static final String FIND_ALL_BOOKS = "FROM Book ";
    public static final String GENERATE_BOOK_ID = "SELECT bookId FROM Book ORDER BY bookId DESC LIMIT 1";
}
