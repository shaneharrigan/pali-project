package com.example.PaliProject.config;

import com.example.PaliProject.cache.MyCache;
import com.example.PaliProject.cache.InMemoryCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up application beans.
 * <p>
 * This class defines a bean for the {@link MyCache} interface, specifically providing an {@link InMemoryCache}
 * implementation. The configuration is managed by the Spring container.
 * </p>
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * Provides a bean for the {@link MyCache} interface.
     * <p>
     * This method returns an instance of {@link InMemoryCache} which is used as the caching mechanism for the
     * application.
     * </p>
     *
     * @return a {@link MyCache} bean instance
     */
    @Bean
    public MyCache cache() {
        logger.info("Creating a Cache bean with InMemoryCache implementation.");
        return new InMemoryCache();
    }
}
