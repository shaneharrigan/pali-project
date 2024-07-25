package com.example.PaliProject.config;

import com.example.PaliProject.cache.ICache;
import com.example.PaliProject.cache.InMemoryICacheImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up application beans.
 * <p>
 * This class defines a bean for the {@link ICache} interface, specifically providing an {@link InMemoryICacheImpl}
 * implementation. The configuration is managed by the Spring container.
 * </p>
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * Provides a bean for the {@link ICache} interface.
     * <p>
     * This method returns an instance of {@link InMemoryICacheImpl} which is used as the caching mechanism for the
     * application.
     * </p>
     *
     * @return a {@link ICache} bean instance
     */
    @Bean
    public ICache cache() {
        logger.info("Creating a Cache bean with InMemoryCache implementation.");
        return new InMemoryICacheImpl();
    }
}
