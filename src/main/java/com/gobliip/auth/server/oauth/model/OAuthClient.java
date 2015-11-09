package com.gobliip.auth.server.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gobliip.auth.server.auth.model.AuthAuthority;
import com.gobliip.auth.server.auth.model.AuthUser;
import com.gobliip.auth.server.common.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Entity(name = "oauth_clients")
public class OAuthClient extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false)
    @JsonIgnore
    private AuthUser owner;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "oauth_client_resources",
            joinColumns = {@JoinColumn(name = "oauth_client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "oauth_resource_id", referencedColumnName = "id")})
    private Set<OAuthResource> resources = new HashSet<>();

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "oauth_client_scopes",
            joinColumns = {@JoinColumn(name = "oauth_client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "oauth_scope_id", referencedColumnName = "id")})
    private Set<OAuthScope> scopes = new HashSet<>();

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "oauth_client_grant_types",
            joinColumns = {@JoinColumn(name = "oauth_client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "oauth_grant_type_id", referencedColumnName = "id")})
    private Set<OAuthGrantType> authorizedGrantTypes = new HashSet<>();

    @ElementCollection(fetch = EAGER)
    @CollectionTable(
            name = "oauth_redirect_uris",
            joinColumns = @JoinColumn(name = "oauth_client_id", referencedColumnName = "id"))
    @Column(name = "redirect_uri")
    private Set<String> webServerRedirectUris = new HashSet<>();

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "oauth_client_authorities",
            joinColumns = {@JoinColumn(name = "oauth_client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<AuthAuthority> authorities = new HashSet<>();

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "oauth_client_auto_approve_scopes",
            joinColumns = {@JoinColumn(name = "oauth_client_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "oauth_scope_id", referencedColumnName = "id")})
    private Set<OAuthScope> autoApproveScopes = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public AuthUser getOwner() {
        return owner;
    }

    public void setOwner(AuthUser owner) {
        this.owner = owner;
    }

    public Set<OAuthResource> getResources() {
        return resources;
    }

    public void setResources(Set<OAuthResource> resources) {
        this.resources = resources;
    }

    public Set<OAuthScope> getScopes() {
        return scopes;
    }

    public void setScopes(Set<OAuthScope> scopes) {
        this.scopes = scopes;
    }

    public Set<OAuthGrantType> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<OAuthGrantType> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getWebServerRedirectUris() {
        return webServerRedirectUris;
    }

    public void setWebServerRedirectUris(Set<String> webServerRedirectUris) {
        this.webServerRedirectUris = webServerRedirectUris;
    }

    public Set<AuthAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<OAuthScope> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<OAuthScope> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }
}
