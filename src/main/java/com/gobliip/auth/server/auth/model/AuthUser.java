package com.gobliip.auth.server.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gobliip.auth.server.common.model.BaseModel;
import com.gobliip.auth.server.oauth.model.OAuthClient;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Entity(name = "auth_users")
@Inheritance(strategy = JOINED)
public class AuthUser extends BaseModel implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "auth_user_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<AuthAuthority> authorities = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "owner")
    @JsonIgnore
    private Set<OAuthClient> oAuthClients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
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

    public Set<AuthAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<OAuthClient> getoAuthClients() {
        return oAuthClients;
    }

    public void setoAuthClients(Set<OAuthClient> oAuthClients) {
        this.oAuthClients = oAuthClients;
    }
}
