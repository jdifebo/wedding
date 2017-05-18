package us.poe2017.data.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

//@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingDateEntity {

    @Column(name = "created_on", nullable = false)
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    private LocalDateTime updatedOn;


    protected AuditingDateEntity() {
    }

    public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

}