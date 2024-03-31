package com.sysm.catalog.admin.infrastructure.aggregates.castmember.models;


import com.sysm.catalog.admin.domain.aggregates.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {
}