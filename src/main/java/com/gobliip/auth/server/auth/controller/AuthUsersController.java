package com.gobliip.auth.server.auth.controller;

import com.gobliip.auth.server.auth.model.AuthUser;
import com.gobliip.auth.server.auth.model.request.AuthUserRequest;
import com.gobliip.auth.server.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by lsamayoa on 10/15/15.
 */
@RestController
@RequestMapping("users")
public class AuthUsersController {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Autowired
    private AuthUserService authUserService;

    @RequestMapping(value = "me", produces = APPLICATION_JSON_CHARSET_UTF_8)
    public AuthUser me(@AuthenticationPrincipal Principal principal) {

        return authUserService.getCurrentUser();
    }

    @RequestMapping(value = "",
            method = POST,
            consumes = APPLICATION_JSON_CHARSET_UTF_8,
            produces = APPLICATION_JSON_CHARSET_UTF_8)
    public AuthUser createUser(@RequestBody final AuthUserRequest authUserRequest) {
        return authUserService.createAuthUser(authUserRequest);
    }

    @RequestMapping(value = "/{authUserId}",
            method = DELETE,
            consumes = APPLICATION_JSON_CHARSET_UTF_8,
            produces = APPLICATION_JSON_CHARSET_UTF_8)
    public void deleteUser(@PathParam("authUserId") final Long authUserId) {
        authUserService.deleteAuthUser(authUserId);
    }
}
