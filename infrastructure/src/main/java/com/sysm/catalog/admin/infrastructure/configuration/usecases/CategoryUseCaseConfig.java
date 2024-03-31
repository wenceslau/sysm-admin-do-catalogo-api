package com.sysm.catalog.admin.infrastructure.configuration.usecases;

import com.sysm.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.sysm.catalog.admin.application.category.create.DefaultCreateCategoryUseCase;
import com.sysm.catalog.admin.application.category.delete.DefaultDeleteCategoryUseCase;
import com.sysm.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.sysm.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.sysm.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway gateway;

    public CategoryUseCaseConfig(CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(gateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(gateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(gateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(gateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(gateway);
    }

}
