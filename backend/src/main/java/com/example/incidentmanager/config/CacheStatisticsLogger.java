package com.example.incidentmanager.config;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caffeine Cache Statistics Logger
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheStatisticsLogger {

    private final CacheManager cacheManager;

    /**
     * Log statistics every 5 minutes
     */
    @Scheduled(fixedRateString = "${cache.stats.fixedRate:300000}")
    public void logCacheStatistics() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
            if (caffeineCache != null) {
                Cache nativeCache = caffeineCache.getNativeCache();
                var stats = nativeCache.stats();
                
                String hitRateFormatted = String.format("%.2f", stats.hitRate() * 100);
                log.info("Cache '{}' - Hit Rate: {}%, Hits: {}, Misses: {}, Size: {}",
                    cacheName,
                    hitRateFormatted,
                    stats.hitCount(),
                    stats.missCount(),
                    nativeCache.estimatedSize()
                );
            }
        });
    }
}