package com.gobliip.auth.server.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gobliip.auth.server.common.model.BaseModel;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Entity(name = "auth_authorities")
public class AuthAuthority extends BaseModel implements GrantedAuthority, Serializable {

    @Id
    private Long id;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private AuthRole role;

    public AuthAuthority() {
    }

    @JsonCreator
    public AuthAuthority(final AuthRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonValue
    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
