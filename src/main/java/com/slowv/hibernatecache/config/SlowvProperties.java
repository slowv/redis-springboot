package com.slowv.hibernatecache.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@ConfigurationProperties(prefix = "slowv", ignoreUnknownFields = false)
public class SlowvProperties {
    private final Cache cache = new Cache();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final ClientApp clientApp = new ClientApp();

    @Getter
    public static class Cache {
        private final Memcached memcached = new Memcached();

        private final Redis redis = new Redis();

        @Data
        public static class Memcached {

            private boolean enabled = SlowvDefault.Cache.Memcached.enabled;

            /**
             * Comma or whitespace separated list of servers' addresses.
             */
            private String servers = SlowvDefault.Cache.Memcached.servers;

            private int expiration = SlowvDefault.Cache.Memcached.expiration;

            private boolean useBinaryProtocol = SlowvDefault.Cache.Memcached.useBinaryProtocol;

            private Authentication authentication = new Authentication();

            @Data
            public static class Authentication {
                private boolean enabled = SlowvDefault.Cache.Memcached.Authentication.enabled;
                private String username;
                private String password;
            }
        }

        @Data
        public static class Redis {
            private String[] server = SlowvDefault.Cache.Redis.server;
            private int expiration = SlowvDefault.Cache.Redis.expiration;
            private boolean cluster = SlowvDefault.Cache.Redis.cluster;
            private int connectionPoolSize = SlowvDefault.Cache.Redis.connectionPoolSize;
            private int connectionMinimumIdleSize = SlowvDefault.Cache.Redis.connectionMinimumIdleSize;
            private int subscriptionConnectionPoolSize = SlowvDefault.Cache.Redis.subscriptionConnectionPoolSize;
            private int subscriptionConnectionMinimumIdleSize = SlowvDefault.Cache.Redis.subscriptionConnectionMinimumIdleSize;
        }
    }

    public static class ClientApp {

        private String name = SlowvDefault.ClientApp.name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
