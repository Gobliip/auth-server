package com.gobliip.auth.server.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gobliip.auth.server.oauth.model.OAuthClient;
import com.gobliip.auth.server.oauth.model.OAuthGrantType;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by lsamayoa on 10/19/15.
 */
public class OAuthClientRequest extends OAuthClient {

    @JsonIgnore
    @Override
    public void setClientId(String clientId) {
        super.setClientId(clientId);
    }

    @JsonIgnore
    @Override
    public void setClientSecret(String clientSecret) {
        super.setClientSecret(clientSecret);
    }

    @NotNull
    @Max(2419200) // Maximum 30 days
    @Min(1209600) // Minimum 14 days
    @Override
    public Integer getRefreshTokenValidity() {
        return super.getRefreshTokenValidity();
    }

    @NotNull
    @Min(3600) // Minimum 1 hour
    @Max(7200) // Maximum 1 hour
    @Override
    public Integer getAccessTokenValidity() {
        return super.getAccessTokenValidity();
    }

    @Size(min = 1)
    @NotNull
    @Override
    public Set<String> getWebServerRedirectUris() {
        return super.getWebServerRedirectUris();
    }

    @Size(min = 1)
    @NotNull
    @Override
    public Set<OAuthGrantType> getAuthorizedGrantTypes() {
        return super.getAuthorizedGrantTypes();
    }
}
