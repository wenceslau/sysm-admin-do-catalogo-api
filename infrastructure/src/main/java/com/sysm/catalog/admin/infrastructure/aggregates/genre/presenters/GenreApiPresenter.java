package com.sysm.catalog.admin.infrastructure.aggregates.genre.presenters;

import com.sysm.catalog.admin.application.genre.retrieve.get.GenreOutput;
import com.sysm.catalog.admin.application.genre.retrieve.list.GenreListOutput;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreListResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}