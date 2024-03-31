package com.sysm.catalog.admin.infrastructure.aggregates.category.model;

import com.sysm.catalog.admin.infrastructure.JacksonTest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JacksonTest
public class CategoryListResponseTest {

    @Autowired
    private JacksonTester<CategoryListResponse> json;

    @Test
    public void testMarshal() throws Exception {
        final var expectedId = "123";
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var categoryGetResponse = new CategoryListResponse(
            expectedId,
            expectedName,
            expectedDescription,
            expectedStatus,
            expectedCreatedAt,
            expectedDeletedAt
        );

        final var actualJson = json.write(categoryGetResponse);

        System.out.println(actualJson);

        assertThat(actualJson).hasJsonPathValue("$.id", expectedId)
            .hasJsonPathValue("$.name", expectedName)
            .hasJsonPathValue("$.description", expectedDescription)
            .hasJsonPathValue("$.is_active", expectedStatus)
            .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
            .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());

    }

}
