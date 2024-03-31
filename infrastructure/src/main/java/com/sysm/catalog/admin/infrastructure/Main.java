package com.sysm.catalog.admin.infrastructure;

import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import com.sysm.catalog.admin.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // Default profile
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

    @Bean
    @Profile("development")
    public ApplicationRunner runner(CategoryRepository categoryRepository) {
        return args -> {
            System.out.println("Running test category repository...");

            var film = Category.newCategory("Filmes", "Filmes de todos os tipos", true);
            var categoryJpaEntity = CategoryJpaEntity.from(film);

            categoryRepository.saveAndFlush(categoryJpaEntity);
            var categoryPersisted = categoryRepository.findById(categoryJpaEntity.getId()).get();
            System.out.println("Entity persisted: "+ categoryPersisted.getName());

            categoryRepository.deleteById(categoryPersisted.getId());
        };
    }

}