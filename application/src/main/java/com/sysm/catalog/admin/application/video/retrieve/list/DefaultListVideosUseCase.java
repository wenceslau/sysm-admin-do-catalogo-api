package com.sysm.catalog.admin.application.video.retrieve.list;

import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.aggregates.video.VideoGateway;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoSearchQuery;

import java.util.Objects;

public class DefaultListVideosUseCase extends ListVideosUseCase {

    private final VideoGateway videoGateway;

    public DefaultListVideosUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<VideoListOutput> execute(final VideoSearchQuery aQuery) {
        return this.videoGateway.findAll(aQuery)
                .map(VideoListOutput::from);
    }
}