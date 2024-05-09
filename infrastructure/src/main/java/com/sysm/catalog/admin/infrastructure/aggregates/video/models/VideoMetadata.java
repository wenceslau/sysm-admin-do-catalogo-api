package com.sysm.catalog.admin.infrastructure.aggregates.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VideoMetadata(
        @JsonProperty("encoded_video_folder") String encodedVideoFolder,
        @JsonProperty("resource_id") String resourceId,
        @JsonProperty("file_path") String filePath
) {
}