package lk.ijse.lms.bo;

import lk.ijse.lms.dto.BookingsDto;

public interface SearchBO extends SuperBO {
    BookingsDto find(String id) throws Exception;
}
