package com.gobliip.auth.server.oauth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by lsamayoa on 10/18/15.
 */
@Entity(name = "oauth_grant_types")
public class OAuthGrantType {

    public static enum GrantType {
        password,
        refresh_token,
        authorization_code
    }

    public OAuthGrantType() {
    }

    @JsonCreator
    public OAuthGrantType(final GrantType grantType) {
        this.grantType = grantType;
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "grant_type", nullable = false)
    private GrantType grantType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonValue
    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }
}
