package com.sysm.catalog.admin.infrastructure.aggregates.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageMediaResponse(
        @JsonProperty("id") String id,
        @JsonProperty("checksum") String checksum,
        @JsonProperty("name") String name,
        @JsonProperty("location") String location
) {
}