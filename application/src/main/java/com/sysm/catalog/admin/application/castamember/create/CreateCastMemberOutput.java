package com.sysm.catalog.admin.application.castamember.create;

import com.sysm.catalog.admin.domain.aggregates.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {

    public static CreateCastMemberOutput from(final CastMember aMember) {
        return new CreateCastMemberOutput(aMember.getId().getValue());
    }

    public static CreateCastMemberOutput from(final String id) {
        return new CreateCastMemberOutput(id);
    }
}