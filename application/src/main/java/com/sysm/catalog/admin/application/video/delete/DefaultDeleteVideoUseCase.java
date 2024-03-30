package com.sysm.catalog.admin.application.video.delete;


import com.sysm.catalog.admin.domain.video.MediaResourceGateway;
import com.sysm.catalog.admin.domain.video.VideoGateway;
import com.sysm.catalog.admin.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {

    private final VideoGateway videoGateway;

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway, MediaResourceGateway mediaResourceGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = mediaResourceGateway;
    }

    @Override
    public void execute(final String anIn) {
        var videoID = VideoID.from(anIn);
        this.videoGateway.deleteById(videoID);
        this.mediaResourceGateway.clearResources(videoID);
    }
}