package com.gobliip.auth.server.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gobliip.auth.server.auth.model.AuthAuthority;
import com.gobliip.auth.server.auth.model.AuthUser;
import com.sun.istack.internal.NotNull;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by lsamayoa on 10/15/15.
 */
public class AuthUserRequest extends AuthUser {

    @Size(min = 6)
    @NotNull
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @JsonProperty
    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Size(min = 6)
    @NotNull
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @JsonIgnore
    @Override
    public Set<AuthAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
