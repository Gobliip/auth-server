package com.gobliip.auth.server.auth;

import com.gobliip.auth.server.auth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    public static final String QL_FIND_USER_BY_USERNAME = "SELECT u FROM com.gobliip.auth.server.auth.model.AuthUser u WHERE u.username = :username";

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final List<AuthUser> authUsers = entityManager.createQuery(QL_FIND_USER_BY_USERNAME, AuthUser.class)
                .setParameter("username", username)
                .getResultList();
        if (authUsers.isEmpty()) {
            throw new UsernameNotFoundException("No authUser with requested username: " + username);
        }
        final AuthUser authUser = authUsers.get(0);

        final User result = new User(
                authUser.getUsername(),
                authUser.getPassword(),
                authUser.isEnabled(),
                true, // accountNonExpired,
                true, // credentialsNonExpired,
                true, // accountNonLocked,
                authUser.getAuthorities());
        return result;
    }
}
