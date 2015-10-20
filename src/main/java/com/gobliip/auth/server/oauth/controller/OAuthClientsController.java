package com.gobliip.auth.server.oauth.controller;

import com.gobliip.auth.server.oauth.model.OAuthClient;
import com.gobliip.auth.server.oauth.model.repository.OAuthClientRepository;
import com.gobliip.auth.server.auth.model.request.OAuthClientRequest;
import com.gobliip.auth.server.oauth.service.OAuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by lsamayoa on 10/19/15.
 */
@RestController
@RequestMapping("/oauth/clients")
public class OAuthClientsController {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Autowired
    private OAuthClientService oAuthClientService;

    @Autowired
    private OAuthClientRepository oAuthClientRepository;

    @RequestMapping
    public Iterable<OAuthClient> all() {
        return oAuthClientRepository.findAll();
    }

    @RequestMapping(value = "", method = POST, consumes = APPLICATION_JSON_CHARSET_UTF_8)
    public OAuthClient createClient(@RequestBody final OAuthClientRequest clientRequest) {
        return oAuthClientService.createClient(clientRequest);
    }

}
