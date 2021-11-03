package com.example.SpringMongoDB.Driver.pkg.Configuration;


import com.hazelcast.config.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCacheConfig {

    /**
     * Logger
     */
    Logger LOGGER = LogManager.getLogger(UserCacheConfig.class);

    @Bean
    public Config cacheConfig() {

        LOGGER.info("***** Inside cacheConfig() in " + this.getClass().getName().toString());

        return new Config()
                .setInstanceName("hazel-instance")
                .addMapConfig(new MapConfig()
                        .setName("user-cache")
                        .setTimeToLiveSeconds(3000)
                        .setEvictionConfig(new EvictionConfig()
                                .setSize(200)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LRU)
                        )
                );
    }
}
