package com.sysm.catalog.admin.application.category.delete;

import com.sysm.catalog.admin.application.category.UseCaseTest;
import com.sysm.catalog.admin.domain.category.Category;
import com.sysm.catalog.admin.domain.category.CategoryGateway;
import com.sysm.catalog.admin.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

public class DeleteCategoryUseCaseTest extends UseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    //1 camin feliz
    //2 id invalido
    //3 erro gateway

    @Test
    public void givenAValidCategory_whenDeleteCategoryUseCase_shouldBeOk(){
        final var aCategory = Category.newCategory("Film", "Desc", true);
        final var expectedId = aCategory.getId();

        Mockito.doNothing().when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInvalidCategory_whenDeleteCategoryUseCase_shouldBeOk() {
        final var expectedId = CategoryID.from("123");

        Mockito.doNothing().when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidCategory_whenGatewayThrowsException_shouldReturnException(){
        final var aCategory = Category.newCategory("Film", "Desc", true);
        final var expectedId = aCategory.getId();

        Mockito.doThrow(new IllegalStateException("Gateway Error")).when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expectedId);
    }

}
