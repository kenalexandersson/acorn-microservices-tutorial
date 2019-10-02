package com.acorn.tutorial.gateway.authentication.localauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures the password encoder for the local user authentication.
 * <p>
 * Generate the password with:
 *
 * <pre>
 *   python -c 'import bcrypt; print(bcrypt.hashpw("password", bcrypt.gensalt(rounds=10)))' | sed 's/$2b/$2a/'
 * </pre>
 *
 * or
 *
 * <pre>
 *   htpasswd -bnBC 10 ""  password | tr -d ':\n' | sed 's/$2y/$2a/'
 * </pre>
 *
 * ... and prefix it with "{bcrypt}". For example:
 *
 * <pre>
 *     localauth:
 *       users:
 *         - userId: "admin"
 *           password: "{bcrypt}$2a$10$LSFBr7wQG1/AIkEdTzXOjOhK5lINUk4nQYfGKCjGvpe6m3XXUVE7y"
 *           roles:
 *             - administrator
 * </pre>
 *
 * The 'sed' operation is required due to a bug in Spring Security.
 * <a href="https://github.com/spring-projects/spring-security/issues/3320">A fix seems to be scheduled for release 5.2.0.</a>
 */
@Profile("localauth")
@Configuration
public class LocalAuthPasswordEncoderConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
