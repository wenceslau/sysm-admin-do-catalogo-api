package com.sysm.catalog.admin.application.genre.retrieve.list;

import com.sysm.catalog.admin.application.UseCase;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase
        extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}