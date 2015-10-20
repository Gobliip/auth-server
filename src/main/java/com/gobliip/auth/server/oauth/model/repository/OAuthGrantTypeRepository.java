package com.gobliip.auth.server.oauth.model.repository;

import com.gobliip.auth.server.oauth.model.OAuthGrantType;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lsamayoa on 10/19/15.
 */
public interface OAuthGrantTypeRepository extends CrudRepository<OAuthGrantType, Long> {
    Set<OAuthGrantType> findByGrantTypeIn(Collection<OAuthGrantType.GrantType> grantTypes);
}
