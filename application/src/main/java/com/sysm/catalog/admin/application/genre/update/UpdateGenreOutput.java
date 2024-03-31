package com.sysm.catalog.admin.application.genre.update;

import com.sysm.catalog.admin.domain.aggregates.genre.Genre;

public record UpdateGenreOutput(String id) {

    public static UpdateGenreOutput from(final Genre aGenre) {
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }
}