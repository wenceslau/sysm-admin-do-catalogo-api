package com.sysm.catalog.admin.infrastructure.aggregates.castmember.models;

import com.sysm.catalog.admin.application.Fixture;
import com.sysm.catalog.admin.infrastructure.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
class CastMembersResponseTest {

    @Autowired
    private JacksonTester<CastMemberGetResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type().name();
        final var expectedCreatedAt = Instant.now().toString();
        final var expectedUpdatedAt = Instant.now().toString();

        final var response = new CastMemberGetResponse(
                expectedId,
                expectedName,
                expectedType,
                expectedCreatedAt,
                expectedUpdatedAt
        );

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.type", expectedType)
                .hasJsonPathValue("$.created_at", expectedCreatedAt)
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt);
    }
}
