package com.sysm.catalog.admin.infrastructure.aggregates.castmember.presenters;

import com.sysm.catalog.admin.application.castamember.retrieve.get.CastMemberOutput;
import com.sysm.catalog.admin.application.castamember.retrieve.list.CastMemberListOutput;
import com.sysm.catalog.admin.infrastructure.aggregates.castmember.models.CastMemberListResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.castmember.models.CastMemberGetResponse;

public interface CastMemberPresenter {

    static CastMemberGetResponse present(final CastMemberOutput aMember) {
        return new CastMemberGetResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput aMember) {
        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}
