package com.sysm.catalog.admin.infrastructure.api.controller;

import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.genre.create.CreateGenreCommand;
import com.sysm.catalog.admin.application.genre.create.CreateGenreUseCase;
import com.sysm.catalog.admin.application.genre.delete.DeleteGenreUseCase;
import com.sysm.catalog.admin.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.sysm.catalog.admin.application.genre.retrieve.list.ListGenreUseCase;
import com.sysm.catalog.admin.application.genre.update.UpdateGenreCommand;
import com.sysm.catalog.admin.application.genre.update.UpdateGenreUseCase;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.CreateGenreRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreListResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreGetResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.UpdateGenreRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.presenters.GenreApiPresenter;
import com.sysm.catalog.admin.infrastructure.api.GenreAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;
    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final ListGenreUseCase listGenreUseCase;
    private final UpdateGenreUseCase updateGenreUseCase;

    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public GenreController(
        final CreateGenreUseCase createGenreUseCase,
        final DeleteGenreUseCase deleteGenreUseCase,
        final GetGenreByIdUseCase getGenreByIdUseCase,
        final ListGenreUseCase listGenreUseCase,
        final UpdateGenreUseCase updateGenreUseCase,
        final GetCategoryByIdUseCase getCategoryByIdUseCase
    ) {
        this.createGenreUseCase = createGenreUseCase;
        this.deleteGenreUseCase = deleteGenreUseCase;
        this.getGenreByIdUseCase = getGenreByIdUseCase;
        this.listGenreUseCase = listGenreUseCase;
        this.updateGenreUseCase = updateGenreUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
    }

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        final var aCommand = CreateGenreCommand.with(
                input.name(),
                input.isActive(),
                input.categories()
        );

        final var output = this.createGenreUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listGenreUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(output -> GenreApiPresenter.present(output, this.getCategoryByIdUseCase));
    }

    @Override
    public GenreGetResponse getById(final String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        final var aCommand = UpdateGenreCommand.with(
                id,
                input.name(),
                input.isActive(),
                input.categories()
        );

        final var output = this.updateGenreUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }
}
