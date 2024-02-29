package com.slowv.hibernatecache.config;

public interface SlowvDefault {
    interface Cache {
        interface Memcached {

            boolean enabled = false;
            String servers = "localhost:11211";
            int expiration = 300; // 5 minutes
            boolean useBinaryProtocol = true;

            interface Authentication {
                boolean enabled = false;
            }
        }

        interface Redis {
            String[] server = {"redis://localhost:6379"};
            int expiration = 300; // 5 minutes
            boolean cluster = false;
            int connectionPoolSize = 64; // default as in redisson
            int connectionMinimumIdleSize = 24; // default as in redisson
            int subscriptionConnectionPoolSize = 50; // default as in redisson
            int subscriptionConnectionMinimumIdleSize = 1; // default as in redisson
        }
    }
    interface ClientApp {

        String name = "hibernateCache2nd";
    }
}
