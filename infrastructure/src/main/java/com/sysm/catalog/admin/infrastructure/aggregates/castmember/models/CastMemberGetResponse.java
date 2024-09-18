package com.sysm.catalog.admin.infrastructure.aggregates.castmember.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CastMemberGetResponse(
        @JsonProperty("id")  String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt
) {
}
