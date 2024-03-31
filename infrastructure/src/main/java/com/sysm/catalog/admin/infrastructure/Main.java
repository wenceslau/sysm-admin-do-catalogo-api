package com.sysm.catalog.admin.infrastructure;

import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.genre.Genre;
import com.sysm.catalog.admin.infrastructure.aggregates.castmember.persistence.CastMemberRepository;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.persistence.GenreRepository;
import com.sysm.catalog.admin.infrastructure.aggregates.video.persistence.VideoRepository;
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
    public ApplicationRunner runnerTestCategory(CategoryRepository categoryRepository) {
        return args -> {
            System.out.println("Running test CategoryRepository...");

            var film = Category.newCategory("Filmes", "Filmes de todos os tipos", true);
            var categoryJpaEntity = CategoryJpaEntity.from(film);

            categoryRepository.saveAndFlush(categoryJpaEntity);
            var categoryPersisted = categoryRepository.findById(categoryJpaEntity.getId()).get();
            System.out.println("Entity persisted: "+ categoryPersisted.getName());

            categoryRepository.deleteById(categoryPersisted.getId());
        };
    }

    @Bean
    @Profile("development")
    public ApplicationRunner runnerTestGenre(GenreRepository genreRepository) {
        return args -> {
            System.out.println("Running test GenreRepository...");

            var list = genreRepository.findAll();
            System.out.println("Size entities: "+ list.size());

        };
    }

    @Bean
    @Profile("development")
    public ApplicationRunner runnerTestCastMember(CastMemberRepository castMemberRepository) {
        return args -> {
            System.out.println("Running test CastMemberRepository...");

            var list = castMemberRepository.findAll();
            System.out.println("Size entities: "+ list.size());

        };
    }

    @Bean
    @Profile("development")
    public ApplicationRunner runnerTestVideo(VideoRepository videoRepository) {
        return args -> {
            System.out.println("Running test VideoRepository...");

            var list = videoRepository.findAll();
            System.out.println("Size entities: "+ list.size());

        };
    }

}