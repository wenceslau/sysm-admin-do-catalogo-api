package com.sysm.catalog.admin.infrastructure.aggregates.category;

import com.sysm.catalog.admin.infrastructure.MySQLGatewayTest;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testInjectionDependencies() {
        assertNotNull(categoryGateway);
        assertNotNull(categoryRepository);
    }

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var createdCategory = categoryGateway.create(category);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(category.getId(), createdCategory.getId());
        Assertions.assertEquals(expectedName, createdCategory.getName());
        Assertions.assertEquals(expectedDescription, createdCategory.getDescription());
        Assertions.assertEquals(expectedActive, createdCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), createdCategory.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), createdCategory.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), createdCategory.getDeletedAt());
        Assertions.assertNull(createdCategory.getDeletedAt());

        final var createdEntity = categoryRepository.findById(createdCategory.getId().getValue()).get();

        Assertions.assertEquals(createdCategory.getId().getValue(), createdEntity.getId());
        Assertions.assertEquals(expectedName, createdEntity.getName());
        Assertions.assertEquals(expectedDescription, createdEntity.getDescription());
        Assertions.assertEquals(expectedActive, createdEntity.isActive());
        Assertions.assertEquals(category.getCreatedAt(), createdEntity.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), createdEntity.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), createdEntity.getDeletedAt());
        Assertions.assertNull(createdEntity.getDeletedAt());

    }


    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnCategoryUpdated() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;
        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());
        final var createdCategoryEntity = categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(category.getId().getValue(), createdCategoryEntity.getId());
        Assertions.assertEquals(expectedName, createdCategoryEntity.getName());
        Assertions.assertEquals(expectedDescription, createdCategoryEntity.getDescription());
        Assertions.assertEquals(expectedActive, createdCategoryEntity.isActive());
        Assertions.assertEquals(category.getCreatedAt(), createdCategoryEntity.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), createdCategoryEntity.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), createdCategoryEntity.getDeletedAt());
        Assertions.assertNull(createdCategoryEntity.getDeletedAt());

        final var createdCategoryEntityFind = categoryRepository.findById(createdCategoryEntity.getId()).get();
        Assertions.assertEquals(createdCategoryEntity.getId(), createdCategoryEntityFind.getId());
        Assertions.assertEquals(expectedName, createdCategoryEntityFind.getName());
        Assertions.assertEquals(expectedDescription, createdCategoryEntityFind.getDescription());
        Assertions.assertEquals(expectedActive, createdCategoryEntityFind.isActive());
        Assertions.assertEquals(category.getCreatedAt(), createdCategoryEntityFind.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), createdCategoryEntityFind.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), createdCategoryEntityFind.getDeletedAt());
        Assertions.assertNull(createdCategoryEntityFind.getDeletedAt());

        final var expectedNameUpdated = "Category 1 Updated";
        final var expectedDescriptionUpdated = "Description 1 Updated";
        final var expectedActiveUpdated = false;

        final var categoryToUpdate = category.clone()
                .update(expectedNameUpdated, expectedDescriptionUpdated, expectedActiveUpdated);

        final var updatedCategory = categoryGateway.update(categoryToUpdate);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(categoryToUpdate.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedNameUpdated, updatedCategory.getName());
        Assertions.assertEquals(expectedDescriptionUpdated, updatedCategory.getDescription());
        Assertions.assertEquals(expectedActiveUpdated, updatedCategory.isActive());
        Assertions.assertEquals(categoryToUpdate.getCreatedAt(), updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(category.getUpdatedAt()));
        Assertions.assertEquals(categoryToUpdate.getDeletedAt(), updatedCategory.getDeletedAt());

        final var updatedEntity = categoryRepository.findById(updatedCategory.getId().getValue()).get();

        Assertions.assertEquals(updatedCategory.getId().getValue(), updatedEntity.getId());
        Assertions.assertEquals(expectedNameUpdated, updatedEntity.getName());
        Assertions.assertEquals(expectedDescriptionUpdated, updatedEntity.getDescription());
        Assertions.assertEquals(expectedActiveUpdated, updatedEntity.isActive());
        Assertions.assertEquals(categoryToUpdate.getCreatedAt(), updatedEntity.getCreatedAt());
        Assertions.assertEquals(categoryToUpdate.getDeletedAt(), updatedEntity.getDeletedAt());

    }


    @Test
    //test to validate delety by id using sintaxe name TDD
    public void givenAValidCategory_whenCallsDeleteById_shouldReturnCategoryDeleted() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;
        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        categoryGateway.deleteById(category.getId());

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    //Test a deleting by id using an invalid id
    public void givenAInvalidCategory_whenCallsDeleteById_shouldReturnCategoryDeleted() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;
        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());
        final var createdCategoryEntity = categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        categoryGateway.deleteById(CategoryID.from("123"));

        Assertions.assertEquals(1, categoryRepository.count());
    }

    @Test
    //Test find by id using a valid id
    public void givenAValidCategory_whenCallsFindById_shouldReturnCategory() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;
        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        final var categoryFind = categoryGateway.findById(category.getId());

        Assertions.assertEquals(category.getId(), categoryFind.get().getId());
        Assertions.assertEquals(expectedName, categoryFind.get().getName());
        Assertions.assertEquals(expectedDescription, categoryFind.get().getDescription());
        Assertions.assertEquals(expectedActive, categoryFind.get().isActive());
        Assertions.assertEquals(category.getCreatedAt(), categoryFind.get().getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), categoryFind.get().getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), categoryFind.get().getDeletedAt());
        Assertions.assertNull(categoryFind.get().getDeletedAt());
    }

    @Test
    //  Test find by id using an invalid id
    public void givenAInvalidCategory_whenCallsFindById_shouldReturnCategory() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description 1";
        final var expectedActive = true;
        final var category = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        final var categoryFind = categoryGateway.findById(CategoryID.from("123"));

        Assertions.assertEquals(Optional.empty(), categoryFind);
    }

    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        Category filmes = Category.newCategory("Filmes", "Filmes", true);
        Category series = Category.newCategory("Series", "Series", true);
        Category documentarios = Category.newCategory("Documentarios", "Documentarios", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        SearchQuery searchQuery = new SearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenEmptyCategoriesTable_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryRepository.count());

        SearchQuery searchQuery = new SearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWhitPage1_shouldReturnPaginated() {
         var expectedPage = 1;
         var expectedPerPage = 1;
         var expectedTotal = 3;

        Category filmes = Category.newCategory("Filmes", "Filmes", true);
        Category series = Category.newCategory("Series", "Series", true);
        Category documentarios = Category.newCategory("Documentarios", "Documentarios", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        //Page 1
        SearchQuery searchQuery = new SearchQuery(1, 1, "", "name", "asc");
        var actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());

        //Page 2
        expectedPage = 2;
        searchQuery = new SearchQuery(2, 1, "", "name", "asc");
        actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllAndTermsMatchCategoryName_shouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        Category filmes = Category.newCategory("Filmes", "Filmes", true);
        Category series = Category.newCategory("Series", "Series", true);
        Category documentarios = Category.newCategory("Documentarios", "Documentarios", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        SearchQuery searchQuery = new SearchQuery(0, 1, "doc", "name", "asc");
        final var actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndMaisAssistidaAsTerms_whenCallsFindAllAndTermsMatchCategoryDescription_shouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        Category filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        Category series = Category.newCategory("Series", "Uma Categoria assistidas", true);
        Category documentarios = Category.newCategory("Documentarios", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        SearchQuery searchQuery = new SearchQuery(0, 1, "MAIS ASSISTIDA", "name", "asc");
        final var actualResult = categoryGateway.findAll(searchQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());

    }

    @Test
    public void givenPrePersistedCategories_whenCallsExistsByIds_shouldReturnIds() {
        // given
        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series = Category.newCategory("Séries", "Uma categoria assistida", true);
        final var documentarios = Category.newCategory("Documentários", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var expectedIds = List.of(filmes.getId(), series.getId());

        final var ids = List.of(filmes.getId(), series.getId(), CategoryID.from("123"));

        // when
        final var actualResult = categoryGateway.existsByIds(ids);

        Assertions.assertTrue(
                expectedIds.size() == actualResult.size() &&
                        expectedIds.containsAll(actualResult)
        );    }

}