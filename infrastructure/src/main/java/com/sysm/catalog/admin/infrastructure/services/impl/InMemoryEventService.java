package com.sysm.catalog.admin.infrastructure.services.impl;

import com.sysm.catalog.admin.infrastructure.configuration.EventService;
import com.sysm.catalog.admin.infrastructure.configuration.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryEventService implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryEventService.class.getName());

    @Override
    public void send(final Object event) {
        LOG.info("Event was observed {}", Json.writeValueAsString(event));
    }
}