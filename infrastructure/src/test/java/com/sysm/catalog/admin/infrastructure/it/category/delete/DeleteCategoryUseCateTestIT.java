package com.sysm.catalog.admin.infrastructure.it.category.delete;

import com.sysm.catalog.admin.infrastructure.IntegrationTest;
import com.sysm.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryGateway;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteCategoryUseCateTestIT {

    @SpyBean
    private CategoryGateway categoryGateway;

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    //1 camin feliz
    //2 id invalido
    //3 erro gateway

    @Test
    public void givenAValidCategory_whenDeleteCategoryUseCase_shouldBeOk(){
        final var aCategory = Category.newCategory("Film", "Desc", true);
        final var expectedId = aCategory.getId();

        save(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());

    }

    @Test
    public void givenAnInvalidCategory_whenDeleteCategoryUseCase_shouldBeNok() {
        final var expectedId = CategoryID.from("123");

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

    private void save(Category... aCategory) {

        var categoryJpaEntities = Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList();

        categoryRepository.saveAll(categoryJpaEntities);
    }


}
