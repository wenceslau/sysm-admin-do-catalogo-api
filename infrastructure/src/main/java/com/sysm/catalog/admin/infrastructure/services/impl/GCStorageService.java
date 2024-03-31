package com.sysm.catalog.admin.infrastructure.services.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.sysm.catalog.admin.domain.aggregates.resource.Resource;
import com.sysm.catalog.admin.infrastructure.services.StorageService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class GCStorageService implements StorageService {

    private final String bucket;
    private final Storage storage;

    public GCStorageService(final String bucket, final Storage storage) {
        this.bucket = bucket;
        this.storage = storage;
    }

    @Override
    public void store(final String id, final Resource resource) {
        final var info = BlobInfo.newBuilder(this.bucket, id)
                .setContentType(resource.contentType())
                .setCrc32cFromHexString(resource.checksum())
                .build();

        this.storage.create(info, resource.content());
    }

    @Override
    public Optional<Resource> get(final String name) {
        final var blob = this.storage.get(this.bucket, name);

        return Optional.ofNullable(blob)
                .map(resource -> Resource.with(
                        blob.getCrc32cToHexString(),
                        blob.getContent(),
                        blob.getContentType(),
                        name));
    }

    @Override
    public List<String> list(final String prefix) {
        final var blobs = this.storage.list(bucket, Storage.BlobListOption.prefix(prefix));

        return StreamSupport.stream(blobs.iterateAll().spliterator(), false)
                .map(BlobInfo::getBlobId)
                .map(BlobId::getName)
                .toList();
    }

    @Override
    public void deleteAll(final Collection<String> names) {
        final var blobs = names.stream()
                .map(name -> BlobId.of(this.bucket, name))
                .toList();

        this.storage.delete(blobs);
    }
}