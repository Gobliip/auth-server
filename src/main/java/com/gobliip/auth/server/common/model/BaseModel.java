package com.gobliip.auth.server.common.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

/**
 * Created by lsamayoa on 10/20/15.
 */
@MappedSuperclass
public class BaseModel {
    @Column(name = "created_at")
    @Temporal(DATE)
    private Date createdAt;

    @Column(name = "created_at")
    @Temporal(DATE)
    private Date updatedAt;

    @PreUpdate
    public void updatedAt(){
        this.updatedAt = new Date();
    }

    @PrePersist
    public void cretedAt() {
        this.createdAt = new Date();
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
}
