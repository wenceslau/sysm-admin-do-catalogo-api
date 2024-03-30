package com.sysm.catalog.admin.domain.resource;

import com.sysm.catalog.admin.domain.ValueObject;

import java.util.Objects;

public class Resource extends ValueObject {

    private final String checksum;
    private final byte[] content;
    private final String contentType;
    private final String name;

    private Resource(final String checksum, final byte[] content, final String contentType, final String name) {
        this.checksum = Objects.requireNonNull(checksum);
        this.content = Objects.requireNonNull(content);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
    }

    public static Resource with(final String checkSum, final byte[] content, final String contentType, final String name) {
        return new Resource(checkSum, content, contentType, name);
    }

    public String checksum() {
        return checksum;
    }

    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public String name() {
        return name;
    }
}