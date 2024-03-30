package com.sysm.catalog.admin.application.video.media.upload;

import com.sysm.catalog.admin.domain.video.Video;
import com.sysm.catalog.admin.domain.video.enums.VideoMediaType;

public record UploadMediaOutput(
        String videoId,
        VideoMediaType mediaType
) {

    public static UploadMediaOutput with(final Video aVideo, final VideoMediaType aType) {
        return new UploadMediaOutput(aVideo.getId().getValue(), aType);
    }
}