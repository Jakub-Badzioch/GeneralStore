package com.general.store.config;

import com.general.store.model.dao.impl.Product;
import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching // wlącza dzialanie adnotacji zwiazancych z cachem
// cachowanie tworzy mape w springu w defaultowuym zachowaniu a w naszym przypadku w hazel cast (platforma do cachowania, ma duzo rzeczy in memory).
// w hazel castcie lepiej trzymac po 1 obiekcie w 1 mapie.
public class HazelCastConfig {
    @Bean
    public Config configHazelCast(){
        // eviction - eksmisja -> wywalanie produktow z cache'a
        final Config config = new Config()
                .setInstanceName("instance-1")
                .addMapConfig(new MapConfig()
                                .setName("product")
                                .setEvictionConfig(new EvictionConfig()
                                        // lfu - frequently
                                        // lru - recently
                                        .setEvictionPolicy(EvictionPolicy.LFU)
                                        .setSize(10)
                                        .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                )
                                .setTimeToLiveSeconds(500)
                        // ttl - time to live ile dane obiekt bedzie zyc w sekundach
                );
        config.getSerializationConfig()
                // pierwsza jedynka factyoryid ; druga - classid
                .addDataSerializableFactory(1, (int id) -> (id == 1) ? new Product() : null);;
        return config;
    }
}

