package com.sysm.catalog.admin.domain.video.records;

import com.sysm.catalog.admin.domain.video.Video;

import java.time.Instant;

public record VideoPreview(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public VideoPreview(final Video aVideo) {
        this(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }
}