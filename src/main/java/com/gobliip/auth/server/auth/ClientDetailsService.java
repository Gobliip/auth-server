package com.gobliip.auth.server.auth;

import com.gobliip.auth.server.auth.model.OAuthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Service
public class ClientDetailsService implements org.springframework.security.oauth2.provider.ClientDetailsService {

    public static final String QL_SELECT_CLIENT_CREDENTIALS = "SELECT c FROM com.gobliip.auth.server.auth.model.OAuthClientDetails c WHERE c.clientId = :clientId";
    private final EntityManager entityManager;

    @Autowired
    public ClientDetailsService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        final List<OAuthClientDetails> queryResult = entityManager
                .createQuery(QL_SELECT_CLIENT_CREDENTIALS, OAuthClientDetails.class)
                .setParameter("clientId", clientId)
                .getResultList();
        if(queryResult.isEmpty()) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }

        final OAuthClientDetails oAuthClientDetails = queryResult.get(0);

        final BaseClientDetails details = new BaseClientDetails(
                oAuthClientDetails.getClientId(),
                oAuthClientDetails.getResourceIds(),
                oAuthClientDetails.getScope(),
                oAuthClientDetails.getAuthorizedGrantTypes(),
                oAuthClientDetails.getAuthorities(),
                oAuthClientDetails.getWebServerRedirectUri());
        details.setClientSecret(oAuthClientDetails.getClientSecret());

        final Integer accessTokenValidity = oAuthClientDetails.getAccessTokenValidity();
        if (accessTokenValidity != null) {
            details.setAccessTokenValiditySeconds(accessTokenValidity);
        }

        final Integer refreshTokenValidity = oAuthClientDetails.getRefreshTokenValidity();
        if (refreshTokenValidity != null) {
            details.setRefreshTokenValiditySeconds(refreshTokenValidity);
        }

        if (oAuthClientDetails.isAutoApprove()) {
            details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(oAuthClientDetails.getScope()));
        }
        return details;
    }
}
