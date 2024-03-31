package com.sysm.catalog.admin.infrastructure.aggregates.category.persistence;

import com.sysm.catalog.admin.infrastructure.MySQLGatewayTest;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void giveAnInvalidNullName_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity.name";
        final var property = "name";

        final var category = Category.newCategory("Name 1", "Description 1", true);

        final var entity = CategoryJpaEntity.from(category);
        entity.setName(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());

    }

    @Test
    public void giveAnInvalidNullCreateAt_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity.createdAt";
        final var property = "createdAt";

        final var category = Category.newCategory("Name 1", "Description 1", true);

        final var entity = CategoryJpaEntity.from(category);
        entity.setCreatedAt(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());

    }

    @Test
    public void giveAnInvalidNullUpdatedAt_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity.updatedAt";
        final var property = "updatedAt";

        final var category = Category.newCategory("Name 1", "Description 1", true);

        final var entity = CategoryJpaEntity.from(category);
        entity.setUpdatedAt(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());

    }
}