package lk.ijse.lms.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class LibraryBranch implements SuperEntity {

    @Id
    private String libId;
    private String name;
    private String location;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public LibraryBranch(String libId, String name, String location) {
        this.libId = libId;
        this.name = name;
        this.location = location;
    }

    public LibraryBranch(String libId, String name, String location, Date updatedDate) {
        this.libId = libId;
        this.name = name;
        this.location = location;
        this.updatedDate = updatedDate;
    }
}
