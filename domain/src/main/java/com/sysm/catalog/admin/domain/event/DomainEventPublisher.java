package com.sysm.catalog.admin.domain.event;

@FunctionalInterface
public interface DomainEventPublisher {
    void publish(final DomainEvent event);
}
