package lk.ijse.lms.dto;

import lk.ijse.lms.entity.LibraryBranch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingsDto {
    private String id;
    private String mID;
    private String bID;
    private String lID;
    private Date createdDate;
    private Date updatedDate;
    private String status;

    public BookingsDto(String id, String mID, String bID, Date createdDate, Date updatedDate) {
        this.id = id;
        this.mID = mID;
        this.bID = bID;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public BookingsDto(String id, String mID, String bID, String lID, Date createdDate, Date updatedDate) {
        this.id = id;
        this.mID = mID;
        this.bID = bID;
        this.lID = lID;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
