package com.sysm.catalog.admin.application.category.update;

import com.sysm.catalog.admin.domain.category.Category;

public record UpdateCategoryOutput(
    String id
) {

    public static UpdateCategoryOutput from(final Category aCategory){
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }

    public static UpdateCategoryOutput with(String anID) {
        return new UpdateCategoryOutput(anID);
    }

}
