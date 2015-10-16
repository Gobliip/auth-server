package com.gobliip.auth.server.auth.model;

import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by lsamayoa on 10/15/15.
 */
public class AuthUserRequest extends AuthUser {

    @Size(min = 6)
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Size(min = 6)
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Valid
    @Override
    public List<AuthAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
