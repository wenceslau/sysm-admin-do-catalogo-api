package com.sysm.catalog.admin.application.genre.retrieve.get;


import com.sysm.catalog.admin.domain.exceptions.NotFoundException;
import com.sysm.catalog.admin.domain.aggregates.genre.Genre;
import com.sysm.catalog.admin.domain.aggregates.genre.GenreGateway;
import com.sysm.catalog.admin.domain.aggregates.genre.GenreID;

import java.util.Objects;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase{

    private final GenreGateway genreGateway;

    public DefaultGetGenreByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreGetOutput execute(final String anIn) {
        final var aGenreId = GenreID.from(anIn);
        return this.genreGateway.findById(aGenreId)
                .map(GenreGetOutput::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, aGenreId));
    }
}
