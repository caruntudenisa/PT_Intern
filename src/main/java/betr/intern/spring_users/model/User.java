package betr.intern.spring_users.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
  @Id private String id;
  private String name;
  private String email;
  private Boolean isStaff;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime modified;

  @CreatedBy private String createdBy;

  @LastModifiedBy private String lastModifiedBy;

  public LocalDateTime getCreated() {
    return this.created;
  }

  public LocalDateTime getModified() {
    return this.modified;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public String getLastModifiedBy() {
    return this.lastModifiedBy;
  }

  public User() {}

  public User(final String email, final String name) {
    this.email = email;
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Boolean getIsStaff() {
    return this.isStaff;
  }

  public void setIsStaff(final Boolean isStaff) {
    this.isStaff = isStaff;
  }
}
