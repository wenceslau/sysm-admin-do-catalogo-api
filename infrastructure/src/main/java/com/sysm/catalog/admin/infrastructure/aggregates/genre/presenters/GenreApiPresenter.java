package com.sysm.catalog.admin.infrastructure.aggregates.genre.presenters;

import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.genre.retrieve.get.GenreGetOutput;
import com.sysm.catalog.admin.application.genre.retrieve.list.GenreListOutput;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreListResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreGetResponse;

import java.util.ArrayList;

public interface GenreApiPresenter {

    static GenreGetResponse present(final GenreGetOutput output) {
        return new GenreGetResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output, GetCategoryByIdUseCase getCategoryByIdUseCase) {

        var listCategories = new ArrayList<CategoryResponse>();

        output.categories().forEach(category -> {
            var categoryOutput = getCategoryByIdUseCase.execute(category);
            listCategories.add(CategoryResponse.from(categoryOutput));
        });

        return new GenreListResponse(
                output.id(),
                output.name(),
                listCategories,
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
