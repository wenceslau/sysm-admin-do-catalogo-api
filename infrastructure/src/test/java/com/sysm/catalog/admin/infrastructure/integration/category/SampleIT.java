package com.sysm.catalog.admin.infrastructure.integration.category;

import com.sysm.catalog.admin.infrastructure.IntegrationTest;
import com.sysm.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void testInjection() {
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }

}
