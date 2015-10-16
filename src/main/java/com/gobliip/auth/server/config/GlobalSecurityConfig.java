package com.gobliip.auth.server.config;

import com.gobliip.auth.server.auth.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by lsamayoa on 10/15/15.
 */
@Configuration
public class GlobalSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void init(final AuthenticationManagerBuilder auth) throws Exception {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
