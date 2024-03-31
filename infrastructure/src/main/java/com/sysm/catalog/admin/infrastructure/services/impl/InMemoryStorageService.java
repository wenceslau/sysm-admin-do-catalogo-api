package com.sysm.catalog.admin.infrastructure.services.impl;

import com.sysm.catalog.admin.domain.aggregates.resource.Resource;
import com.sysm.catalog.admin.infrastructure.services.StorageService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorageService implements StorageService {

    private final Map<String, Resource> storage;

    public InMemoryStorageService() {
        this.storage = new ConcurrentHashMap<>();
    }

    public void clear() {
        this.storage.clear();
    }

    public Map<String, Resource> storage() {
        return this.storage;
    }

    @Override
    public void store(final String id, final Resource resource) {
        this.storage.put(id, resource);
    }

    @Override
    public Optional<Resource> get(final String id) {
        return Optional.ofNullable(this.storage.get(id));
    }

    @Override
    public List<String> list(final String prefix) {
        return this.storage.keySet().stream()
                .filter(it -> it.startsWith(prefix))
                .toList();
    }

    @Override
    public void deleteAll(final Collection<String> names) {
        names.forEach(this.storage::remove);
    }
}