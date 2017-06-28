package us.poe2017.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guest_response")
public class GuestResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_response")
    private GroupResponseEntity groupResponse;

    @ManyToOne
    @JoinColumn(name = "guest")
    private GuestEntity guest;

    @Column(name = "attending")
    private Boolean attending;

    @Column(name = "plus_one_name")
    private String plusOneName;

    public GuestResponseEntity() {

    }

    public GuestResponseEntity(GroupResponseEntity groupResponse, GuestEntity guest, Boolean attending, String plusOneName) {
        this.groupResponse = groupResponse;
        this.guest = guest;
        this.attending = attending;
        this.plusOneName = plusOneName;
    }
}