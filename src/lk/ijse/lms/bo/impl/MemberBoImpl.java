package lk.ijse.lms.bo.impl;

import lk.ijse.lms.bo.MemberBO;
import lk.ijse.lms.dao.DAOFactory;
import lk.ijse.lms.dao.custom.MemberDAO;
import lk.ijse.lms.dao.custom.UserDAO;
import lk.ijse.lms.dto.LibraryBranchDto;
import lk.ijse.lms.dto.MemberDto;
import lk.ijse.lms.dto.UserDto;
import lk.ijse.lms.entity.LibraryBranch;
import lk.ijse.lms.entity.Member;
import lk.ijse.lms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberBoImpl implements MemberBO {

    private final MemberDAO memberDAO = (MemberDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.MEMBER);
    @Override
    public boolean add(MemberDto memberDto) throws Exception {
        return memberDAO.add(new Member(
                memberDto.getMemberId(),
                memberDto.getName(),
                memberDto.getAddress(),
                memberDto.getContact()
        ));
    }

    @Override
    public List<MemberDto> findAll() throws Exception {
        ArrayList<MemberDto> memberDtos = new ArrayList<>();
        List<Member> all = memberDAO.findAll();
        for (Member member : all) {
            memberDtos.add(new MemberDto(
                    member.getMemberId(), member.getName(), member.getAddress(), member.getContact()
            ));
        }
        return memberDtos;
    }

    @Override
    public MemberDto find(String id) throws Exception {
        Member member = memberDAO.find(id);
        return new MemberDto(
                member.getMemberId(),
                member.getName(),
                member.getAddress(),
                member.getContact()

        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return memberDAO.delete(id);
    }

    @Override
    public boolean update(MemberDto memberDto) throws Exception {
        return memberDAO.update(new Member(
                memberDto.getMemberId(),
                memberDto.getName(),
                memberDto.getAddress(),
                memberDto.getContact()
        ));
    }

    @Override
    public String generateNewMemberId() throws SQLException, ClassNotFoundException {
        return memberDAO.generateId();
    }

}
