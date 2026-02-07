package com.college.crushconnect.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "crushes",
        uniqueConstraints = @UniqueConstraint(columnNames = "from_user_id")
)
public class Crush {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_user_id", nullable = false)
    private Long fromUserId;

    @Column(name = "to_email_hash", nullable = false)
    private String toEmailHash;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToEmailHash() {
        return toEmailHash;
    }

    public void setToEmailHash(String toEmailHash) {
        this.toEmailHash = toEmailHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

