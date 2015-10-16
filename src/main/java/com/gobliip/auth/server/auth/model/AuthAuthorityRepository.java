package com.gobliip.auth.server.auth.model;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by lsamayoa on 10/15/15.
 */
public interface AuthAuthorityRepository extends PagingAndSortingRepository<AuthUser, Long> {
}
