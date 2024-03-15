package lk.ijse.lms.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

@Entity
public class Bookings implements SuperEntity{
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "member_ID", referencedColumnName = "memberId")
    private Member mID;

    @ManyToOne
    @JoinColumn(name = "book_ID", referencedColumnName = "bookId")
    private Book bID;

    @ManyToOne
    @JoinColumn(name = "branch_ID", referencedColumnName = "libId")
    private LibraryBranch lID;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
