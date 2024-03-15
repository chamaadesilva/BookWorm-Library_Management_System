package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.SearchBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.SearchDAO;
import lk.ijse.lms.dto.BookingsDto;
import lk.ijse.lms.entity.Bookings;

public class SearchBOImpl implements SearchBO {

    private final SearchDAO searchDAO = (SearchDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SEARCHDAO);

    @Override
    public BookingsDto find(String id) throws Exception {
        Bookings bookings = searchDAO.find(id);
        BookingsDto bookingsDto = new BookingsDto();
        try {
            bookingsDto.setId(bookings.getId());
            bookingsDto.setMID(bookings.getMID().getMemberId());
            bookingsDto.setBID(bookings.getBID().getBookId());
            bookingsDto.setCreatedDate(bookings.getCreatedDate());
            bookingsDto.setUpdatedDate(bookings.getUpdatedDate());
            bookingsDto.setStatus(bookings.getBID().getAvailability());

        } catch (Exception ignored) {}
        return bookingsDto;
    }

}
