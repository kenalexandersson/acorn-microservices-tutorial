package com.acorn.tutorial.gateway.authentication.localauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Profile("localauth")
public class LocalAuthProvider implements AuthenticationProvider {

    private static final Object CREDENTIALS_FOR_AUTHENTICATED_TOKEN = "[dummy credentials]";

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalAuthProvider.class);

    private final LocalAuthProperties properties;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LocalAuthProvider(LocalAuthProperties properties, PasswordEncoder passwordEncoder) {
        this.properties = properties;
        this.passwordEncoder = passwordEncoder;

        if (properties.getUsers() == null) {
            LOGGER.warn("No local users defined. Are we missing a 'users.yml' file?");
        } else {
            LOGGER.info("Setting up a local users directory with users found in users.yml:");
            properties.getUsers()
                    .forEach(localUser -> LOGGER.info("-> UserId: {}, roles: {}", localUser.getUserId(), localUser.getRoles()));
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        final String name = authentication.getName();
        final String rawPassword = authentication.getCredentials().toString();

        final Optional<LocalUser> localUser = getLocalUser(name, rawPassword);

        return localUser
                .map(LocalAuthProvider::createPreAuthenticatedAuthenticationToken)
                .orElseThrow(() -> new BadCredentialsException("Incorrect user name or password"));
    }

    private Optional<LocalUser> getLocalUser(String name, String rawPassword) {
        List<LocalUser> users = properties.getUsers();
        if (users == null) {
            return Optional.empty();
        }

        return users.stream()
                .filter(user -> name.equals(user.getUserId()) && passwordEncoder.matches(rawPassword, user.getPassword()))
                .findFirst();
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    private static PreAuthenticatedAuthenticationToken createPreAuthenticatedAuthenticationToken(LocalUser localUser) {
        final PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(localUser.getUserId(), CREDENTIALS_FOR_AUTHENTICATED_TOKEN, createGrantedAuthorities(localUser.getRoles()));
        token.setDetails(localUser);
        return token;
    }

    private static Collection<? extends GrantedAuthority> createGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
