package com.sysm.catalog.admin.infrastructure.it.category.create;

import com.sysm.catalog.admin.infrastructure.IntegrationTest;
import com.sysm.catalog.admin.application.category.create.CreateCategoryCommand;
import com.sysm.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryGateway;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    //1-Test do caminho feliz
    //2-Test passaando uma propriedade invalida
    //3-Criando uma categoria inativa
    //4-Simulando um erro generico vindo do gateway

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        //given
        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        //when/then
        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        var category = categoryRepository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.isActive());
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());

    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException(){
        final String expectedName = null;
        final var expectedDescription = "Catergoria mais assistida";
        final var expectedActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().getMessage());

        Assertions.assertEquals(0, categoryRepository.count());

        verify(categoryGateway, times(0)).create(any());

    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {

        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        final var actualOutput = useCase.execute(aCommand).get();

        //Common asserts
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        var category = categoryRepository.findById(actualOutput.id()).get();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedActive, category.isActive());
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());

    }

    @Test
    public void givenAValidCommandWhenGatewayThrowsRandomException_whenCallsCreateCategory_shouldReturnAException() {

        var expectedName = "Filme";
        var expectedDescription = "Categoria mais assistida";
        var expectedActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        //We use 'doThrow' when we are using spy bean
        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).create(any());

        //We use 'when' we are using mock
//        when(categoryGateway.create(any()))
//                .thenThrow(new IllegalStateException(expectedErrorMessage));

        var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().getMessage());

    }


}
