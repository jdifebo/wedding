package us.poe2017.data.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "username", length = 32)
    private String username;

    @Column(name = "password", length = 60, nullable = false, columnDefinition="char(60)")
    private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "admin", nullable = false)
	private boolean admin;

//	@Column(name = "created_on", nullable = false)
//	@CreatedDate
//	private OffsetDateTime createdOn;
//
//	@Column(name = "updated_on")
//	@LastModifiedDate
//	private OffsetDateTime updatedOn;

    @Version
    private long version;

	public UserEntity(String username, String hashedPassword) {
		this.username = username;
		this.password = hashedPassword;
		this.enabled = true;
	}

	public UserEntity() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

//	public OffsetDateTime getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(OffsetDateTime createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public OffsetDateTime getUpdatedOn() {
//		return updatedOn;
//	}
//
//	public void setUpdatedOn(OffsetDateTime updatedOn) {
//		this.updatedOn = updatedOn;
//	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}