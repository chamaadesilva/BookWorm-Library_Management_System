package lk.ijse.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class LibraryBranchDto {
    private String libId;
    private String name;
    private String location;
    private Date createdDate;
    private Date updatedDate;
}
