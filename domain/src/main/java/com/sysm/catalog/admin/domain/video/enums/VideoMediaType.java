package com.sysm.catalog.admin.domain.video.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VideoMediaType {
    VIDEO,
    TRAILER,
    BANNER,
    THUMBNAIL,
    THUMBNAIL_HALF;

    public static Optional<VideoMediaType> of(final String value) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
