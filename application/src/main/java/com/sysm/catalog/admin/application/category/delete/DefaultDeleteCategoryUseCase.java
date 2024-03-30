package com.sysm.catalog.admin.application.category.delete;

import com.sysm.catalog.admin.domain.category.CategoryGateway;
import com.sysm.catalog.admin.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String anId) {
        categoryGateway.deleteById(CategoryID.from(anId));
    }

}
