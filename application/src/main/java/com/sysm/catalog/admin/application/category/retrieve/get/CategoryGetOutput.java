package com.sysm.catalog.admin.application.category.retrieve.get;

import com.sysm.catalog.admin.domain.category.Category;
import com.sysm.catalog.admin.domain.category.CategoryID;

import java.time.Instant;

public record CategoryGetOutput(
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static CategoryGetOutput from(final Category aCategory) {
        return new CategoryGetOutput(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
