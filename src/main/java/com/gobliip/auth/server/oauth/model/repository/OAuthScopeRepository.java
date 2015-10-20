package com.gobliip.auth.server.oauth.model.repository;

import com.gobliip.auth.server.oauth.model.OAuthScope;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lsamayoa on 10/19/15.
 */
public interface OAuthScopeRepository extends CrudRepository<OAuthScope, Long> {
    OAuthScope findByName(String name);

    Set<OAuthScope> findByNameIn(Collection<String> names);
}
