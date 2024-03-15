package lk.ijse.lms.bo;

import lk.ijse.lms.dto.MemberDto;

import java.sql.SQLException;
import java.util.List;

public interface MemberBO extends SuperBO {
    boolean add(MemberDto memberDto) throws Exception;

    List<MemberDto> findAll() throws Exception;

    MemberDto find(String id) throws Exception;

    boolean delete(String id) throws Exception;

    boolean update(MemberDto memberDto) throws Exception;

    String generateNewMemberId() throws SQLException, ClassNotFoundException;

}
