package com.gobliip.auth.server.oauth.service;

import com.gobliip.auth.server.auth.model.*;
import com.gobliip.auth.server.auth.model.repository.AuthAuthorityRepository;
import com.gobliip.auth.server.oauth.model.repository.OAuthClientRepository;
import com.gobliip.auth.server.oauth.model.repository.OAuthGrantTypeRepository;
import com.gobliip.auth.server.oauth.model.repository.OAuthScopeRepository;
import com.gobliip.auth.server.auth.model.request.OAuthClientRequest;
import com.gobliip.auth.server.auth.service.AuthUserService;
import com.gobliip.auth.server.oauth.model.OAuthClient;
import com.gobliip.auth.server.oauth.model.OAuthGrantType;
import com.gobliip.auth.server.oauth.model.OAuthScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lsamayoa on 10/19/15.
 */
@Service
@Validated
@Transactional
public class OAuthClientService {
    @Autowired
    private OAuthClientRepository oAuthClientRepository;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private OAuthScopeRepository oAuthScopeRepository;

    @Autowired
    private AuthAuthorityRepository authAuthorityRepository;

    @Autowired
    private OAuthGrantTypeRepository oAuthGrantTypeRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OAuthClient createClient(@Valid final OAuthClientRequest oAuthClientRequest) {
        final OAuthClient oAuthClient = new OAuthClient();

        oAuthClient.setOwner(authUserService.getCurrentUser());
        oAuthClient.setClientId(UUID.randomUUID().toString());
        oAuthClient.setClientSecret(UUID.randomUUID().toString());

        oAuthClient.setAccessTokenValidity(oAuthClientRequest.getAccessTokenValidity());
        oAuthClient.setRefreshTokenValidity(oAuthClientRequest.getRefreshTokenValidity());
        oAuthClient.setWebServerRedirectUris(oAuthClientRequest.getWebServerRedirectUris());

        final Set<OAuthGrantType> requestedOAuthGrantTypes = oAuthClientRequest.getAuthorizedGrantTypes();
        if (requestedOAuthGrantTypes != null && !requestedOAuthGrantTypes.isEmpty()) {
            final Set<OAuthGrantType.GrantType> requestedGrantTypes = requestedOAuthGrantTypes
                    .stream()
                    .filter(a -> a != null)
                    .map(OAuthGrantType::getGrantType)
                    .collect(Collectors.toSet());
            final Set<OAuthGrantType> grants = oAuthGrantTypeRepository.findByGrantTypeIn(requestedGrantTypes);
            // TODO: Replace with exception/validation
            Assert.isTrue(grants.size() == requestedGrantTypes.size(), "Requested authorities count must match the authorities fetched from the db");
            oAuthClient.setAuthorizedGrantTypes(grants);
        }

        final Set<OAuthScope> requestedScopes = oAuthClientRequest.getScopes();
        if (requestedScopes != null && !requestedScopes.isEmpty()) {
            final Set<String> scopeNames = requestedScopes
                    .stream()
                    .filter(s -> s != null)
                    .map(OAuthScope::getName)
                    .collect(Collectors.toSet());
            final Set<OAuthScope> scopes = oAuthScopeRepository.findByNameIn(scopeNames);
            // TODO: Replace with exception/validation
            Assert.isTrue(scopes.size() == requestedScopes.size(), "Requested scopes count must match the scopes fetched from the db");
            oAuthClient.setScopes(scopes);
        }

        final Set<OAuthScope> requestedAttoaproveScopes = oAuthClientRequest.getAutoApproveScopes();
        if (requestedAttoaproveScopes != null && !requestedAttoaproveScopes.isEmpty()) {
            final Set<String> scopeNames = requestedAttoaproveScopes
                    .stream()
                    .filter(s -> s != null)
                    .map(OAuthScope::getName)
                    .collect(Collectors.toSet());
            final Set<OAuthScope> scopes = oAuthScopeRepository.findByNameIn(scopeNames);
            // TODO: Replace with exception/validation
            Assert.isTrue(scopes.size() == requestedAttoaproveScopes.size(), "Requested auto approve scopes count must match the scopes fetched from the db");
            oAuthClient.setAutoApproveScopes(scopes);
        }

        final Set<AuthAuthority> requestedAuthorities = oAuthClientRequest.getAuthorities();
        if (requestedAuthorities != null && !requestedAuthorities.isEmpty()) {
            final Set<AuthRole> requestedAuthoritiesRoles = requestedAuthorities
                    .stream()
                    .filter(a -> a != null)
                    .map(AuthAuthority::getRole)
                    .collect(Collectors.toSet());
            final Set<AuthAuthority> authorities = authAuthorityRepository.findByRoleIn(requestedAuthoritiesRoles);
            // TODO: Replace with exception/validation
            Assert.isTrue(authorities.size() == requestedAuthorities.size(), "Requested authorities count must match the authorities fetched from the db");
            oAuthClient.setAuthorities(authorities);
        }

        return oAuthClientRepository.save(oAuthClient);
    }

}
