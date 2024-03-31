package com.sysm.catalog.admin.infrastructure.aggregates.category.model;

import com.sysm.catalog.admin.infrastructure.JacksonTest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.UpdateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JacksonTest
public class UpdateCategoryRequestTest {

    @Autowired
    private JacksonTester<UpdateCategoryRequest> json;

    @Test
    public void testMarshal() throws Exception {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = false;

        final var updateCategoryRequest = new UpdateCategoryRequest(
            expectedName,
            expectedDescription,
            expectedStatus
        );

        final var actualJson = json.write(updateCategoryRequest);

        System.out.println(actualJson);

        assertThat(actualJson)
            .hasJsonPathValue("$.name", expectedName)
            .hasJsonPathValue("$.description", expectedDescription)
            .hasJsonPathValue("$.is_active", expectedStatus);

    }

    @Test
    public void testUnmarshal() throws Exception {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedStatus = false;

    final var json = """
        {
            "name": "%s",
            "description": "%s",
            "is_active": %s
        }
        """.formatted(
            expectedName,
            expectedDescription,
            expectedStatus
        );

        final var object = this.json.parse(json);

        assertThat(object)
            .hasFieldOrPropertyWithValue("name", expectedName)
            .hasFieldOrPropertyWithValue("description", expectedDescription)
            .hasFieldOrPropertyWithValue("active", expectedStatus);

    }

}
