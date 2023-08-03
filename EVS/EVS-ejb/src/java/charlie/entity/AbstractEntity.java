package charlie.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "timestamp NOT NULL DEFAULT (now())")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, columnDefinition = "timestamp NOT NULL DEFAULT (now())")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "uuid", unique = true, insertable = true, updatable = false, nullable = false)
    private String uuid;

    @Column(name = "jpa_version", nullable = false, updatable = false)
    @Version
    private int jpaVersion;

    public AbstractEntity() {
        this(false);
    }

    public AbstractEntity(boolean newEntity) {
        if (newEntity) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(int jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    @Override
    public String toString() {
        return "AbstractEntity{" + "id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", uuid=" + uuid + ", jpaVersion=" + jpaVersion + '}';
    }

    @Override
    public int hashCode() {
        if (uuid == null) {
            throw new IllegalStateException("UUID not set");
        }
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        return Objects.equals(this.uuid, other.uuid);
    }

    @PrePersist
    public void checkUuid() {
        if (uuid == null || uuid.length() != 36) {
            throw new IllegalStateException(getClass().getSimpleName() + ": invalid UUID (" + uuid + ")");
        }
    }

}
