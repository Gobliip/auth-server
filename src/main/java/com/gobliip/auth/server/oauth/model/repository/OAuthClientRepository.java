package com.gobliip.auth.server.oauth.model.repository;

import com.gobliip.auth.server.oauth.model.OAuthClient;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by lsamayoa on 10/18/15.
 */
public interface OAuthClientRepository extends PagingAndSortingRepository<OAuthClient, Long> {
    OAuthClient findByClientId(String clientId);
}
