package com.sysm.catalog.admin.infrastructure.aggregates.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysm.catalog.admin.domain.aggregates.video.enums.VideoMediaType;

public record UploadMediaResponse(
        @JsonProperty("video_id") String videoId,
        @JsonProperty("media_type") VideoMediaType mediaType
) {
}