package com.sysm.catalog.admin.infrastructure.aggregates.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateVideoResponse(@JsonProperty("id") String id) {
}