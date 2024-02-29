package com.slowv.hibernatecache.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;
import org.hibernate.cache.jcache.ConfigSettings;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(SlowvProperties properties) {
        final var jcacheConfig = new MutableConfiguration<>();
        final var redisUri = URI.create(properties.getCache().getRedis().getServer()[0]);

        final var config = new Config();
        if (properties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                    .useClusterServers()
                    .setMasterConnectionPoolSize(properties.getCache().getRedis().getConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(properties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(properties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .addNodeAddress(properties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                    .useSingleServer()
                    .setConnectionPoolSize(properties.getCache().getRedis().getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(properties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(properties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .setAddress(properties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
                CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, properties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        log.info("Config {}", jcacheConfiguration);
        return cm -> {
            createCache(cm, com.slowv.hibernatecache.domain.Product.class.getName(), jcacheConfiguration);
        };
    }

    private void createCache(
            javax.cache.CacheManager cm,
            String cacheName,
            javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
