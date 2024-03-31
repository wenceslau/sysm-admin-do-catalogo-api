package com.sysm.catalog.admin.domain.aggregates.category;

import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    Pagination<Category> findAll(SearchQuery aQuery);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}
