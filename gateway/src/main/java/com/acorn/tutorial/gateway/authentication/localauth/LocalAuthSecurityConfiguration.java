package com.acorn.tutorial.gateway.authentication.localauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Authenticates using a local user directory (from config files).
 */
@Profile("localauth")
@Configuration
@EnableWebSecurity(debug = false)
public class LocalAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LocalAuthProvider authProvider;

    @Autowired
    public LocalAuthSecurityConfiguration(LocalAuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // Instruct Spring Security to use our authentication provider
        auth.authenticationProvider(authProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests() // Instruct Spring Security to act on incoming requests
                .antMatchers("/actuator/**").permitAll() // This excludes /actuator from the security check, i.e. we don't need to be authenticated here
                .anyRequest().fullyAuthenticated() // instruct spring security that we must be authenticated to allow request
                .and()
                .httpBasic(); // ... using http basic
    }
}