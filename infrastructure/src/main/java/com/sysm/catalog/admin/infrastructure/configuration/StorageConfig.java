package com.sysm.catalog.admin.infrastructure.configuration;

import com.google.cloud.storage.Storage;
import com.sysm.catalog.admin.infrastructure.configuration.properties.google.GoogleStorageProperties;
import com.sysm.catalog.admin.infrastructure.configuration.properties.storage.StorageProperties;
import com.sysm.catalog.admin.infrastructure.services.StorageService;
import com.sysm.catalog.admin.infrastructure.services.impl.GCStorageService;
import com.sysm.catalog.admin.infrastructure.services.impl.InMemoryStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties(value = "storage.catalogo-videos")
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    //When is running in one of these profiles, create this bean for memory, if not, create the real bean for GC storage
    @Bean("storageService")
    @Profile({"development", "test-integration", "test-e2e"})
    public StorageService inMemoryStorageService() {
        return new InMemoryStorageService();
    }

    @Bean("storageService")
    @ConditionalOnMissingBean
    public StorageService gcStorageService(
            final GoogleStorageProperties props,
            final Storage storage
    ) {
        return new GCStorageService(props.getBucket(), storage);
    }

}
