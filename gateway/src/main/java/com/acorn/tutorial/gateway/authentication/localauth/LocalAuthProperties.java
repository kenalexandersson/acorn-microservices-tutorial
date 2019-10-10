package com.acorn.tutorial.gateway.authentication.localauth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Properties class for local authentication config attributes.
 * <p>
 * Local users are defined in a file "users.yml" located on classpath
 */
@Profile("localauth")
@Component
@PropertySource(value = "classpath:users.yml", ignoreResourceNotFound = true, factory = LocalAuthProperties.YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "localauth")
@Data
public class LocalAuthProperties {
    /**
     * The locally defined users.
     */
    private List<LocalUser> users;

    static class YamlPropertySourceFactory implements PropertySourceFactory {

        @Override
        public org.springframework.core.env.PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
            Properties propertiesFromYaml = loadYamlIntoProperties(resource);
            String sourceName = name != null ? name : resource.getResource().getFilename();
            return new PropertiesPropertySource(sourceName, propertiesFromYaml);
        }

        private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
            try {
                YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
                factory.setResources(resource.getResource());
                factory.afterPropertiesSet();
                return factory.getObject();

            } catch (IllegalStateException e) {
                // for ignoreResourceNotFound
                Throwable cause = e.getCause();
                if (cause instanceof FileNotFoundException) {
                    throw (FileNotFoundException) e.getCause();
                }

                throw e;
            }
        }
    }
}