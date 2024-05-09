package com.sysm.catalog.admin.domain;


import com.sysm.catalog.admin.domain.event.DomainEvent;
import com.sysm.catalog.admin.domain.event.DomainEventPublisher;
import com.sysm.catalog.admin.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    private final List<DomainEvent> domainEvents;

    public Entity(final ID id, final List<DomainEvent> domainEvents) {
        Objects.requireNonNull(id, "id should not be null");
        this.id = id;
        this.domainEvents = new ArrayList<>(domainEvents == null ? Collections.emptyList() : domainEvents);
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void publishDomainEvents(final DomainEventPublisher publisher) {
        if (publisher == null){
            return;
        }
        for (DomainEvent domainEvent : getDomainEvents()) {
            publisher.publish(domainEvent);
        }
        //getDomainEvents().forEach(publisher::publish);
        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event) {
        if (event == null) {
            return;
        }
        this.domainEvents.add(event);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Entity<?> entity = (Entity<?>) object;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
