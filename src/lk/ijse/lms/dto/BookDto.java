package lk.ijse.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private String bookId;
    private String author;
    private String title;
    private String genre;
    private String available;
    private Date createdDate;
    private Date updatedDate;

}
