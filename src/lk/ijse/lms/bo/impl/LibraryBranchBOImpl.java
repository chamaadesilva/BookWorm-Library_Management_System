package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.LibraryBranchBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.LibraryBranchDAO;
import lk.ijse.lms.dto.LibraryBranchDto;
import lk.ijse.lms.entity.LibraryBranch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryBranchBOImpl implements LibraryBranchBO {

    private final LibraryBranchDAO libraryBranchDAO = (LibraryBranchDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.LIBRARYBRANCH);

    @Override
    public boolean add(LibraryBranchDto libraryBranchDto) throws Exception {
        return libraryBranchDAO.add(new LibraryBranch(
                libraryBranchDto.getLibId(),
                libraryBranchDto.getName(),
                libraryBranchDto.getLocation(),
                libraryBranchDto.getCreatedDate(),
                libraryBranchDto.getUpdatedDate()

        ));

    }


@Override
public List<LibraryBranchDto> findAll() throws Exception {
    ArrayList<LibraryBranchDto> libraryBranchDtos = new ArrayList<>();
    List<LibraryBranch> all = libraryBranchDAO.findAll();
    for (LibraryBranch libraryBranch : all) {
        libraryBranchDtos.add(new LibraryBranchDto(
            libraryBranch.getLibId(), libraryBranch.getName(),
            libraryBranch.getLocation(),libraryBranch.getCreatedDate(),
                libraryBranch.getUpdatedDate()
        ));
    }
    return libraryBranchDtos;
}
    @Override
    public LibraryBranchDto find(String id) throws Exception {
        LibraryBranch libraryBranch = libraryBranchDAO.find(id);
        return new LibraryBranchDto(
                libraryBranch.getLibId(),
                libraryBranch.getName(),
                libraryBranch.getLocation(),
                libraryBranch.getCreatedDate(),
                libraryBranch.getUpdatedDate()
        );

    }

    @Override
    public boolean delete(String id) throws Exception {
        return libraryBranchDAO.delete(id);
    }

    @Override
    public boolean update(LibraryBranchDto libraryBranchDto) throws Exception {
        return libraryBranchDAO.update(new LibraryBranch(
                libraryBranchDto.getLibId(),
                libraryBranchDto.getName(),
                libraryBranchDto.getLocation(),
                libraryBranchDto.getCreatedDate(),
                libraryBranchDto.getUpdatedDate()
        ));
    }

    @Override
    public String generateNewBranchId() throws SQLException, ClassNotFoundException {
        return libraryBranchDAO.generateId();
    }
}
