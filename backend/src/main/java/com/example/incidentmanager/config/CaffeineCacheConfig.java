package com.example.incidentmanager.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine cache configuration (haute performance)
 */
@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
            "incidentSearchCache", 
            "incidents", 
            "statistics"
        );
        
        cacheManager.setCaffeine(caffeineCacheBuilder());
        
        return cacheManager;
    }

    private Caffeine caffeineCacheBuilder() {
        return Caffeine.newBuilder()
            // Expiration après écriture (TTL)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            // Expiration après dernier accès
            .expireAfterAccess(5, TimeUnit.MINUTES)
            // Taille maximale du cache (éviction automatique)
            .maximumSize(1000)
            // Activer les statistiques
            .recordStats();
    }

    /**
     * Cache statistics logger bean
     */
    @Bean
    public CacheStatisticsLogger cacheStatisticsLogger(CacheManager cacheManager) {
        return new CacheStatisticsLogger(cacheManager);
    }
}