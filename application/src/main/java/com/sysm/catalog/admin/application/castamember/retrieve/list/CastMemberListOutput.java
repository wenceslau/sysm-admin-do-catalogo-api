package com.sysm.catalog.admin.application.castamember.retrieve.list;


import com.sysm.catalog.admin.domain.aggregates.castmember.CastMember;
import com.sysm.catalog.admin.domain.aggregates.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt
) {

    public static CastMemberListOutput from(final CastMember aMember) {
        return new CastMemberListOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt()
        );
    }
}