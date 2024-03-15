package lk.ijse.lms.dto;

import lk.ijse.lms.entity.LibraryBranch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
}