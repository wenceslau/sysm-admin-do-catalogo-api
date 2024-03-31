package com.sysm.catalog.admin.infrastructure.services;

import com.sysm.catalog.admin.domain.aggregates.resource.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StorageService {

    void store(String id, Resource resource);

    Optional<Resource> get(String id);

    List<String> list(String prefix);

    void deleteAll(final Collection<String> ids);
}