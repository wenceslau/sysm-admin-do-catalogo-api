package com.sysm.catalog.admin.infrastructure.configuration;

public interface EventService {
    void send(Object event);
}