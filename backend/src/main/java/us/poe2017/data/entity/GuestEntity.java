package us.poe2017.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guests")
public class GuestEntity {


    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @Column(name = "kid")
    private Boolean kid;

    @Column(name = "under_21")
    private Boolean under21;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "name")
    private List<GuestResponseEntity> responses;


    public GuestEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public Boolean getKid() {
        return kid;
    }

    public void setKid(Boolean kid) {
        this.kid = kid;
    }

    public Boolean getUnder21() {
        return under21;
    }

    public void setUnder21(Boolean under21) {
        this.under21 = under21;
    }
}