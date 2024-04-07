package com.sysm.catalog.admin.domain.aggregates;

import com.sysm.catalog.admin.domain.Entity;
import com.sysm.catalog.admin.domain.Identifier;
import com.sysm.catalog.admin.domain.event.DomainEvent;
import com.sysm.catalog.admin.domain.utils.IdUtils;
import com.sysm.catalog.admin.domain.utils.InstantUtils;
import com.sysm.catalog.admin.domain.validation.ValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityTest {

    @Test
    public void givenNullAsEvents_whenInstantiate_shouldBeOk() {

        // given
        final List<DomainEvent> events = null;

        // when
        final var anEntity = new DummyEntity(new DummyID(), events);

        // then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertTrue(anEntity.getDomainEvents().isEmpty());
    }

    @Test
    public void givenDomainEvents_whenPassInConstructor_shouldCreateADefensiveClone() {

        // given
        final List<DomainEvent> events = new ArrayList<>();
        events.add(new DummyEvent());

        // when
        final var anEntity = new DummyEntity(new DummyID(), events);

        // then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertEquals(1, anEntity.getDomainEvents().size());

        Assertions.assertThrows(RuntimeException.class, () -> {
            final var actualEvents = anEntity.getDomainEvents();
            actualEvents.add(new DummyEvent());
        });
    }

    @Test
    public void givenEmptyDomainEvents_whenCallRegisterEvent_shouldAddEventToList() {

        // given
        final var expectedEvents = 1;
        final var anEntity = new DummyEntity(new DummyID(), new ArrayList<>());

        // when
        anEntity.registerEvent(new DummyEvent());

        // then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertEquals(expectedEvents, anEntity.getDomainEvents().size());
    }

    @Test
    public void givenAFewDomainEvents_whenCallsPublishEvents_shouldCallPublisherAndClearTheList() {

        // given
        final var expectedEvents = 3;
        final var counter = new AtomicInteger(0);
        final var anEntity = new DummyEntity(new DummyID(), new ArrayList<>());
        anEntity.registerEvent(new DummyEvent());
        anEntity.registerEvent(new DummyEvent());
        anEntity.registerEvent(new DummyEvent());

        Assertions.assertEquals(3, anEntity.getDomainEvents().size());

        // when
        anEntity.publishDomainEvents(event -> {
            counter.incrementAndGet();
        });

        // then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertTrue(anEntity.getDomainEvents().isEmpty());
        Assertions.assertEquals(expectedEvents, counter.get());
    }

    public static class DummyID extends Identifier {

        private final String id;

        public DummyID() {
            this.id = IdUtils.uuid();
        }

        @Override
        public String getValue() {
            return id;
        }
    }

    public static class DummyEntity extends Entity<DummyID> {

        public DummyEntity() {
            super(new DummyID(), null);
        }

        public DummyEntity(DummyID dummyID, List<DomainEvent> domainEvents) {
            super(dummyID, domainEvents);
        }

        @Override
        public void validate(ValidationHandler handler) {

        }
    }

    public static class DummyEvent implements DomainEvent {

        @Override
        public Instant occurredOn() {
            return InstantUtils.now();
        }
    }

}
