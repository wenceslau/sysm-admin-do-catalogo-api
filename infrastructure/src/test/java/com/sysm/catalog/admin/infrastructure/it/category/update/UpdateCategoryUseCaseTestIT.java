package com.sysm.catalog.admin.infrastructure.it.category.update;

import com.sysm.catalog.admin.infrastructure.IntegrationTest;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryCommand;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryGateway;
import com.sysm.catalog.admin.domain.exceptions.NotFoundException;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class UpdateCategoryUseCaseTestIT {

    @Autowired
    private UpdateCategoryUseCase useCase;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    //1-Test do caminho feliz
    //2-Test passaando uma propriedade invalida
    //3-Criando uma categoria inativa
    //4-Simulando um erro generico vindo do gateway
    //5-ID não exist

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        save(aCategory);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        var category = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        final var aCategory =
                Category.newCategory("Film", null, true);

        save(aCategory);

        final var expectedId = aCategory.getId();
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription,
                expectedActive);

        var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().getMessage());

        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        save(aCategory);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        var category = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());


    }

    @Test
    public void givenAValidCommandWhenGatewayThrowsRandomException_whenCallsUpdateCategory_shouldReturnAException() {
        final var aCategory = Category.newCategory("Film", null, true);

        save(aCategory);

        final var expectedId = aCategory.getId();
        var expectedName = "Filme";
        var expectedDescription = "Categoria mais assistida";
        var expectedActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedActive
        );

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).update(any());

        var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().getMessage());

        var category = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(aCategory.getName(), category.getName());
        Assertions.assertEquals(aCategory.getDescription(), category.getDescription());
        Assertions.assertEquals(aCategory.isActive(), category.isActive());
        Assertions.assertEquals(aCategory.getId(), aCategory.getId());
        Assertions.assertEquals(category.getCreatedAt(), category.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), category.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), category.getDeletedAt());
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException(){
        final var expectedId = "123";
        final String expectedName = "Categoria";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var expectedErrorMessage = "The Category with id 123 was not found";

        final var aCommand = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription,
                expectedActive);

        var domainException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertNotNull(domainException);
        Assertions.assertEquals(expectedErrorMessage, domainException.getMessage());

    }

    private void save(Category... aCategory) {

        var categoryJpaEntities = Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList();

        categoryRepository.saveAll(categoryJpaEntities);
    }

}
