package com.sysm.catalog.admin.application.category.retrieve.list;

import com.sysm.catalog.admin.application.UseCase;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListCategoriesUseCase
        extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}