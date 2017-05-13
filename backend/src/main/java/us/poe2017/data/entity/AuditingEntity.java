package us.poe2017.data.entity;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
 
//@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingEntity {
 
    @Column(name = "created_on", nullable = false)
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    private LocalDateTime updatedOn;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Version
    private long version;

//    @JoinColumn(name = "created_by", referencedColumnName="username")
//    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
//    @CreatedBy
//    private UserEntity createdBy;
//
//    @JoinColumn(name = "updated_by", referencedColumnName="username")
//    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
//    @LastModifiedBy
//    private UserEntity updatedBy;

    protected AuditingEntity() {
    }

    public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
}