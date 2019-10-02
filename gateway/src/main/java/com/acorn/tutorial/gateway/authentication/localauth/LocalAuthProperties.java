package com.acorn.tutorial.gateway.authentication.localauth;

import com.acorn.tutorial.gateway.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Properties class for local authentication config attributes.
 * <p>
 * Local users are defined in a file "users.yml" that looks something like:
 * <pre>
 *   localauth:
 *     users:
 *       - userId: "admin"
 *         crewId: "admin"
 *         password: "{bcrypt}$2a$10$LSFBr7wQG1/AIkEdTzXOjOhK5lINUk4nQYfGKCjGvpe6m3XXUVE7y"
 *         roles:
 *           - administrator
 *
 *       - userId: "00446"
 *         crewId: "00446"
 *         password: "{bcrypt}$2a$10$LSFBr7wQG1/AIkEdTzXOjOhK5lINUk4nQYfGKCjGvpe6m3XXUVE7y"
 *         roles:
 *           - junior_crew
 * </pre>
 */
@Profile("localauth")
@Component
@PropertySource(value = "file:users.yml", ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)      // For production
@PropertySource(value = "classpath:users.yml", ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class) // For development purposes
@ConfigurationProperties(prefix = "localauth")
@Data
public class LocalAuthProperties {
    /**
     * The locally defined users.
     */
    private List<LocalUser> users;
}
