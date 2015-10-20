package com.gobliip.auth.server.auth.model.repository;

import com.gobliip.auth.server.auth.model.AuthAuthority;
import com.gobliip.auth.server.auth.model.AuthRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lsamayoa on 10/15/15.
 */
public interface AuthAuthorityRepository extends PagingAndSortingRepository<AuthAuthority, Long> {
    AuthAuthority findByRole(final AuthRole role);

    Set<AuthAuthority> findByRoleIn(final Collection<AuthRole> authRoles);
}
