package com.sysm.catalog.admin.infrastructure.aggregates.genre.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysm.catalog.admin.application.genre.retrieve.get.GenreGetOutput;

public record GenreResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {

    public static GenreResponse from(GenreGetOutput genreGetOutput) {
        return new GenreResponse(genreGetOutput.id(), genreGetOutput.name());
    }
}
