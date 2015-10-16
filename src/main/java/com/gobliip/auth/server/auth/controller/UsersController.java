package com.gobliip.auth.server.auth.controller;

import com.gobliip.auth.server.auth.model.AuthUser;
import com.gobliip.auth.server.auth.model.AuthUserRepository;
import com.gobliip.auth.server.auth.model.AuthUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by lsamayoa on 10/15/15.
 */
@RestController
@RequestMapping("users")
@Transactional(rollbackFor = Exception.class)
public class UsersController {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Autowired
    private AuthUserRepository authUserRepository;

    @RequestMapping(value = "me", produces = APPLICATION_JSON_CHARSET_UTF_8)
    public AuthUser user(@AuthenticationPrincipal OAuth2Authentication user) {
        return authUserRepository.findByUsername((String)user.getPrincipal());
    }

    @RequestMapping(value = "",
            method = POST,
            consumes = APPLICATION_JSON_CHARSET_UTF_8,
            produces = APPLICATION_JSON_CHARSET_UTF_8)
    @PreAuthorize("#oauth2.clientHasRole('ADMIN') && hasRole('ADMIN')")
    public AuthUser createUser(@Valid @RequestBody final AuthUserRequest authUserRequest) {
        return authUserRepository.save(authUserRequest);
    }
}
