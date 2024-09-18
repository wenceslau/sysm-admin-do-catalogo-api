package com.sysm.catalog.admin.application.video.retrieve.list;

import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.domain.aggregates.genre.GenreID;
import com.sysm.catalog.admin.domain.aggregates.video.Video;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoPreview;

import java.time.Instant;
import java.util.List;

public record VideoListOutput(
        String id,
        String title,
        List<String> genres_id,
        List<String> categories_id,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoListOutput from(final Video aVideo) {
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getGenres().stream()
                        .map(GenreID::getValue)
                        .toList(),
                aVideo.getCategories().stream()
                        .map(CategoryID::getValue)
                        .toList(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }

    public static VideoListOutput from(final VideoPreview aVideo) {

        return new VideoListOutput(
                aVideo.id(),
                aVideo.title(),
                List.of(),
                List.of(),
                aVideo.description(),
                aVideo.createdAt(),
                aVideo.updatedAt()
        );
    }
}
