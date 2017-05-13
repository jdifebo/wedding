package us.poe2017.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "groups")
public class GroupEntity{


    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "groupName")
    private String groupName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_code")
    private List<GuestEntity> guests;


    public GroupEntity() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GuestEntity> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestEntity> guests) {
        this.guests = guests;
    }
}