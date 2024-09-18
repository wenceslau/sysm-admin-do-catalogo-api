package com.sysm.catalog.admin.infrastructure.aggregates.castmember.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sysm.catalog.admin.application.castamember.retrieve.get.CastMemberOutput;

public record CastMemberResponse(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name
) {

    public static CastMemberResponse from(CastMemberOutput castMemberOutput) {
        return new CastMemberResponse(
            castMemberOutput.id(),
            castMemberOutput.name()
        );
    }
}
