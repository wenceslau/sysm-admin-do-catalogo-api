package com.sysm.catalog.admin.infrastructure.aggregates.category.model;

import com.sysm.catalog.admin.infrastructure.JacksonTest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryGetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JacksonTest
public class CategoryGetResponseTest {

    @Autowired
    private JacksonTester<CategoryGetResponse> json;

    @Test
    public void testMarshal() throws Exception {
        final var expectedId = "123";
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var categoryGetResponse = new CategoryGetResponse(
            expectedId,
            expectedName,
            expectedDescription,
            expectedStatus,
            expectedCreatedAt,
            expectedUpdatedAt,
            expectedDeletedAt
        );

        final var actualJson = json.write(categoryGetResponse);

        System.out.println(actualJson);

        assertThat(actualJson).hasJsonPathValue("$.id", expectedId)
            .hasJsonPathValue("$.name", expectedName)
            .hasJsonPathValue("$.description", expectedDescription)
            .hasJsonPathValue("$.is_active", expectedStatus)
            .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
            .hasJsonPathValue("$.updated_at", expectedUpdatedAt.toString())
            .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());

    }

    @Test
    public void testUnmarshal() throws Exception {
        final var expectedId = "123";
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

    final var json = """
        {
            "id": "%s",
            "name": "%s",
            "description": "%s",
            "is_active": %s,
            "created_at": "%s",
            "updated_at": "%s",
            "deleted_at": "%s"
        }
        """.formatted(
            expectedId,
            expectedName,
            expectedDescription,
            expectedStatus,
            expectedCreatedAt,
            expectedUpdatedAt,
            expectedDeletedAt
        );

        final var object = this.json.parse(json);

        assertThat(object).hasFieldOrPropertyWithValue("id", expectedId)
            .hasFieldOrPropertyWithValue("name", expectedName)
            .hasFieldOrPropertyWithValue("description", expectedDescription)
            .hasFieldOrPropertyWithValue("active", expectedStatus)
            .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
            .hasFieldOrPropertyWithValue("updatedAt", expectedUpdatedAt)
            .hasFieldOrPropertyWithValue("deletedAt", expectedDeletedAt);

    }

}
