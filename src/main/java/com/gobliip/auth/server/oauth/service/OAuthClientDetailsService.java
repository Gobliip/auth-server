package com.gobliip.auth.server.oauth.service;

import com.gobliip.auth.server.oauth.model.repository.OAuthClientRepository;
import com.gobliip.auth.server.oauth.model.OAuthClient;
import com.gobliip.auth.server.oauth.model.OAuthGrantType;
import com.gobliip.auth.server.oauth.model.OAuthResource;
import com.gobliip.auth.server.oauth.model.OAuthScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Service
public class OAuthClientDetailsService implements ClientDetailsService {


    @Autowired
    private OAuthClientRepository oAuthClientRepository;


    @Override
    @Transactional(readOnly = true)
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        final OAuthClient oAuthClient = oAuthClientRepository.findByClientId(clientId);
        if (oAuthClient == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }

        final BaseClientDetails details = new BaseClientDetails();
        details.setClientId(oAuthClient.getClientId());
        details.setClientSecret(oAuthClient.getClientSecret());
        details.setAuthorities(oAuthClient.getAuthorities());
        details.setRegisteredRedirectUri(oAuthClient.getWebServerRedirectUris());

        final Integer accessTokenValidity = oAuthClient.getAccessTokenValidity();
        if (accessTokenValidity != null) {
            details.setAccessTokenValiditySeconds(accessTokenValidity);
        }

        final Integer refreshTokenValidity = oAuthClient.getRefreshTokenValidity();
        if (refreshTokenValidity != null) {
            details.setRefreshTokenValiditySeconds(refreshTokenValidity);
        }

        final Set<OAuthResource> resources = oAuthClient.getResources();
        if (resources != null) {
            final Set<String> resourcesStr = resources.stream()
                    .filter(resource -> resource != null)
                    .map(OAuthResource::getName)
                    .collect(Collectors.toSet());
            details.setResourceIds(resourcesStr);
        }

        final Set<OAuthGrantType> authorizedGrantTypes = oAuthClient.getAuthorizedGrantTypes();
        if (authorizedGrantTypes != null) {
            final Set<String> authorizedGrantTypesStr = authorizedGrantTypes.stream()
                    .filter(grantType -> grantType != null)
                    .map(OAuthGrantType::getGrantType)
                    .map(OAuthGrantType.GrantType::toString)
                    .collect(Collectors.toSet());
            details.setAuthorizedGrantTypes(authorizedGrantTypesStr);
        }

        final Set<OAuthScope> scopes = oAuthClient.getScopes();
        if (scopes != null) {
            final Set<String> scopesStr = scopes.stream()
                    .filter(scope -> scope != null)
                    .map(OAuthScope::getName)
                    .collect(Collectors.toSet());
            details.setAutoApproveScopes(scopesStr);
        }

        final Set<OAuthScope> autoApproveScopes = oAuthClient.getAutoApproveScopes();
        if (autoApproveScopes != null) {
            final Set<String> autoApproveScopesStr = autoApproveScopes.stream()
                    .filter(scope -> scope != null)
                    .map(OAuthScope::getName)
                    .collect(Collectors.toSet());
            details.setAutoApproveScopes(autoApproveScopesStr);
        }
        return details;
    }
}
