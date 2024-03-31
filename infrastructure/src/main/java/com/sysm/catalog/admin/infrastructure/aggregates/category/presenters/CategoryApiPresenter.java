package com.sysm.catalog.admin.infrastructure.aggregates.category.presenters;

import com.sysm.catalog.admin.application.category.retrieve.get.CategoryGetOutput;
import com.sysm.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryGetResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryGetResponse present(final CategoryGetOutput anOutput) {
        return new CategoryGetResponse(
                anOutput.id().getValue(),
                anOutput.name(),
                anOutput.description(),
                anOutput.isActive(),
                anOutput.createdAt(),
                anOutput.updatedAt(),
                anOutput.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput anOutput) {
        return new CategoryListResponse(
                anOutput.id(),
                anOutput.name(),
                anOutput.description(),
                anOutput.isActive(),
                anOutput.createdAt(),
                anOutput.deletedAt()
        );
    }
}
