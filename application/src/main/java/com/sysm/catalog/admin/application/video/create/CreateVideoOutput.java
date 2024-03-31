package com.sysm.catalog.admin.application.video.create;

import com.sysm.catalog.admin.domain.aggregates.video.Video;

public record CreateVideoOutput(String id) {

    public static CreateVideoOutput from(final Video aVideo) {
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}