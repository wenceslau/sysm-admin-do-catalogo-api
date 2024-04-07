package com.sysm.catalog.admin.domain.aggregates.video.records;

import com.sysm.catalog.admin.domain.event.DomainEvent;
import com.sysm.catalog.admin.domain.utils.InstantUtils;

import java.time.Instant;

public record VideoMediaCreated(
        String resourceId,
        String filePath,
        Instant occurredOn
) implements DomainEvent {

    public VideoMediaCreated(final String resourceId, final String filePath) {
        this(resourceId, filePath, InstantUtils.now());
    }
}