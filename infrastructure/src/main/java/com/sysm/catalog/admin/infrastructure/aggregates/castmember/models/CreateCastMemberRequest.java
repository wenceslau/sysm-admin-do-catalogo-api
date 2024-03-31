package com.sysm.catalog.admin.infrastructure.aggregates.castmember.models;


import com.sysm.catalog.admin.domain.aggregates.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {
}