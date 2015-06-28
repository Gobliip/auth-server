package com.gobliip.auth.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.sql.DataSource;

import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Controller
@SessionAttributes("authorizationRequest")
@EnableResourceServer
@EnableEurekaClient
public class GobliipAuthServerApplication extends WebMvcConfigurerAdapter {

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}

	@Configuration
	protected static class SecurityConfig extends
			GlobalAuthenticationConfigurerAdapter {
		@Autowired
		private DataSource dataSource;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			auth.jdbcAuthentication().dataSource(dataSource)
					.passwordEncoder(passwordEncoder);
			// .withUser("admin").password(passwordEncoder.encode("r00t")).authorities("ROLE_ADMIN","ROLE_USER");
		}
	}

	@Configuration
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected static class LoginConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
			.and().requestMatchers()
				.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
			.and().authorizeRequests()
				.anyRequest()
				.authenticated();
			// @formatter:on
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.parentAuthenticationManager(authenticationManager);
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationConfig extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private DataSource dataSource;
	

		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() throws NoSuchAlgorithmException {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			
			KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance("RSA");
			kpGenerator.initialize(512);
			KeyPair keyPair = kpGenerator.generateKeyPair();
			converter.setKeyPair(keyPair);
			
			return converter;
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients.jdbc(dataSource);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authenticationManager(authenticationManager)
					.accessTokenConverter(jwtAccessTokenConverter());
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
					"isAuthenticated()");
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(GobliipAuthServerApplication.class, args);
	}
}
