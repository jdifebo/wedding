package us.poe2017.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "group_response")
public class GroupResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;

    @Column(name = "email")
    private String email;

    @Column(name = "dietary_restrictions")
    private String dietaryRestrictions;

    @Column(name = "comments")
    private String comments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_response")
    private List<GuestResponseEntity> guests;


    public GroupResponseEntity() {

    }

    public GroupResponseEntity(GroupEntity group, String email, String dietaryRestrictions, String comments) {
        this.group = group;
        this.email = email;
        this.dietaryRestrictions = dietaryRestrictions;
        this.comments = comments;
        this.guests = new ArrayList<>();
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<GuestResponseEntity> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestResponseEntity> guests) {
        this.guests = guests;
    }
}