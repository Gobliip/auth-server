package com.gobliip.auth.server.auth.service;

import com.gobliip.auth.server.auth.model.AuthUser;
import com.gobliip.auth.server.auth.model.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by lsamayoa on 10/18/15.
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public AuthUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        final AuthUser authUser = authUserRepository.findByUsername(username);
        if (authUser == null) {
            throw new UsernameNotFoundException("No AuthUser with requested username: " + username);
        }
        return authUser;
    }
}
