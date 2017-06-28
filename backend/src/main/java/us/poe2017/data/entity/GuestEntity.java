package us.poe2017.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guests")
public class GuestEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @Column(name = "kid")
    private Boolean kid;

    @Column(name = "under_21")
    private Boolean under21;

    @Column(name = "plus_one", nullable = false)
    private boolean plusOne;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "guest")
    private List<GuestResponseEntity> responses;


    public GuestEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isPlusOne() {
        return plusOne;
    }

    public void setPlusOne(boolean plusOne) {
        this.plusOne = plusOne;
    }
}