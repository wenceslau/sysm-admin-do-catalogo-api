package com.sysm.catalog.admin.application.category.create;

import com.sysm.catalog.admin.domain.aggregates.category.Category;

public record CreateCategoryOutput(
    String id
) {

    public static CreateCategoryOutput from(final Category aCategory){
        return new CreateCategoryOutput(aCategory.getId().getValue());
    }

    public static CreateCategoryOutput with(String anID) {
        return new CreateCategoryOutput(anID);
    }
}
