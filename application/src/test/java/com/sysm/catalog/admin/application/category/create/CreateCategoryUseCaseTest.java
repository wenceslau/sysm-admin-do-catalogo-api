package com.sysm.catalog.admin.application.category.create;

import com.sysm.catalog.admin.application.category.UseCaseTest;
import com.sysm.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class CreateCategoryUseCaseTest extends UseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    //1-Test do caminho feliz
    //2-Test passaando uma propriedade invalida
    //3-Criando uma categoria inativa
    //4-Simulando um erro generico vindo do gateway

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final CategoryGateway categoryGateway = mock(CategoryGateway.class);

        //On this moment, o then answer will return the first parameter received in create method.
        //This means mockito will return the same value sent to method
        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final var userCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand).get();

        //Common asserts
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        //Assert mockito, check if create method was called once, and if the return are as we expected
        verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
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

        verify(categoryGateway, times(0)).create(any());


    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {

        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final CategoryGateway categoryGateway = mock(CategoryGateway.class);

        //On this moment, o then answer will return the first parameter received in create method.
        //This means mockito will return the same value sent to method
        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final var userCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand).get();

        //Common asserts
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        //Assert mockito, check if create method was called once, and if the return are as we expected
        verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.nonNull(aCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWhenGatewayThrowsRandomException_whenCallsCreateCategory_shouldReturnAException() {

        var expectedName = "Filme";
        var expectedDescription = "Categoria mais assistida";
        var expectedActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive);

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final CategoryGateway categoryGateway = mock(CategoryGateway.class);

        //On this moment, o then answer will return the first parameter received in create method.
        //This means mockito will return the same value sent to method
        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        //removido por que o @Mock e @InjectMocks ja criam eles
        //final var userCase = new DefaultCreateCategoryUseCase(categoryGateway);

        var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().getMessage());

        //Assert mockito, check if create method was called once, and if the return are as we expected
        verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
    }

}