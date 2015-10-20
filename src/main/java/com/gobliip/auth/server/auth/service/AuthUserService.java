package com.gobliip.auth.server.auth.service;

import com.gobliip.auth.server.auth.model.AuthUser;
import com.gobliip.auth.server.auth.model.repository.AuthAuthorityRepository;
import com.gobliip.auth.server.auth.model.repository.AuthUserRepository;
import com.gobliip.auth.server.auth.model.request.AuthUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static com.gobliip.auth.server.auth.model.AuthRole.ROLE_USER;

/**
 * Created by lsamayoa on 10/18/15.
 */
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthAuthorityRepository authAuthorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public AuthUser getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            return authUserRepository.findByUsername((String) authentication.getPrincipal());
        }
        return (AuthUser) authentication.getPrincipal();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AuthUser createAuthUser(@Valid final AuthUserRequest authUserRequest) {
        final AuthUser authUser = new AuthUser();
        authUser.setEnabled(true);
        authUser.setUsername(authUserRequest.getUsername());
        authUser.setPassword(passwordEncoder.encode(authUserRequest.getPassword()));
        authUser.getAuthorities().add(authAuthorityRepository.findByRole(ROLE_USER));

        return authUserRepository.save(authUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAuthUser(final Long authUserId) {
        authAuthorityRepository.delete(authUserId);
    }

}
