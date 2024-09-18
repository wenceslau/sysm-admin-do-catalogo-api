package com.sysm.catalog.admin.infrastructure.aggregates.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysm.catalog.admin.application.category.retrieve.get.CategoryGetOutput;

import java.time.Instant;

public record CategoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name){

    public static CategoryResponse from(CategoryGetOutput categoryGetResponse) {
        return new CategoryResponse(categoryGetResponse.id().getValue(), categoryGetResponse.name());
    }
}
