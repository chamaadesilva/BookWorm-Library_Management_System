package lk.ijse.lms.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Member implements SuperEntity{

    @Id
    private String memberId;
    private String name;
    private String address;
    private String contact;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public Member(String memberId, String name, String address, String contact) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }
}
