package com.gobliip.auth.server.oauth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by lsamayoa on 10/18/15.
 */
@Entity(name = "oauth_resources")
public class OAuthResource {

    public OAuthResource() {
    }

    @JsonCreator
    public OAuthResource(final String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
