package com.sysm.catalog.admin.infrastructure.integration.category.retrieve.get;

import com.sysm.catalog.admin.infrastructure.IntegrationTest;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryGateway;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.domain.exceptions.NotFoundException;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class GetCategoryByUseCaseTestIT {

    @Autowired
    private GetCategoryByIdUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        //given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        //when/then
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        save(aCategory);

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        //Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
       // Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
       // Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());

    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() {
        final var expectedErrorMessage = "The Category with id 123 was not found";
        final var expectedId = CategoryID.from("123");

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CategoryID.from("123");

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).findById(eq(expectedId));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }

    private void save(Category... aCategory) {

        var categoryJpaEntities = Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList();

        categoryRepository.saveAll(categoryJpaEntities);
    }

}
