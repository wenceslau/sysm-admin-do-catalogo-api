package com.sysm.catalog.admin.application.video.update;


import com.sysm.catalog.admin.domain.aggregates.video.Video;

public record UpdateVideoOutput(String id) {

    public static UpdateVideoOutput from(final Video aVideo) {
        return new UpdateVideoOutput(aVideo.getId().getValue());
    }
}