package com.gobliip.auth.server.auth.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Entity(name = "authorities")
public class AuthAuthority implements GrantedAuthority {

    @Id
    private long id;

    @Column(name = "authority")
    private String authority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
