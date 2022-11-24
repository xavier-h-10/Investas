package com.fundgroup.backend.config;

import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.cache.StockCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.fundgroup.backend")
public class CacheConfig {
    @Bean
    public FundAssemblyCache fundAssemblyCache()
    {
        return new FundAssemblyCache();
    }

    @Bean
    public StockCache stockCache()
    {
        return new StockCache();
    }
}
