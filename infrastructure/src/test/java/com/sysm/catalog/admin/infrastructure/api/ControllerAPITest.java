package com.sysm.catalog.admin.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysm.catalog.admin.infrastructure.ApiTest;
import com.sysm.catalog.admin.infrastructure.ControllerTest;
import com.sysm.catalog.admin.application.category.create.CreateCategoryOutput;
import com.sysm.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.sysm.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.CategoryGetOutput;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.list.CategoryListOutput;
import com.sysm.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryOutput;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.sysm.catalog.admin.domain.aggregates.category.Category;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.domain.exceptions.DomainException;
import com.sysm.catalog.admin.domain.exceptions.NotFoundException;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.validation.handler.Notification;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CreateCategoryRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.UpdateCategoryRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ControllerTest(controllers = CategoryAPI.class)
public class ControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCategoryUseCase createUseCase;

    @MockBean
    private GetCategoryByIdUseCase getByIdUserCase;

    @MockBean
    private UpdateCategoryUseCase updateUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteUseCase;
    
    @MockBean
    private ListCategoriesUseCase listUseCase;

    @Test
    void test() {
        System.out.println("Test");
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        //given
        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var contentRequest =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedActive);

        //when
        Mockito.when(createUseCase.execute(any()))
                .thenReturn(right(CreateCategoryOutput.with("123")));

        var request = MockMvcRequestBuilders.post("/categories")
                .with(ApiTest.CATEGORIES_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categories/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDescription, cmd.description());
                    Assertions.assertEquals(expectedActive, cmd.isActive());
                    return true;
                }
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        //given
        var expectedName = "";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var contentRequest =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedActive);

        //when
        Mockito.when(createUseCase.execute(any()))
                .thenReturn(left(Notification.create(new Error("'name' should not be null"))));

        var request = MockMvcRequestBuilders.post("/categories")
                .with(ApiTest.CATEGORIES_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo("'name' should not be null")));

        verify(createUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDescription, cmd.description());
                    Assertions.assertEquals(expectedActive, cmd.isActive());
                    return true;
                }
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        //given
        var expectedName = "";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var contentRequest =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedActive);

        //when
        Mockito.when(createUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error("'name' should not be null")));

        var request = MockMvcRequestBuilders.post("/categories")
                .with(ApiTest.CATEGORIES_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo("'name' should not be null")));

        verify(createUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDescription, cmd.description());
                    Assertions.assertEquals(expectedActive, cmd.isActive());
                    return true;
                }
        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        //given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        //when
        Mockito.when(getByIdUserCase.execute(any()))
                .thenReturn(CategoryGetOutput.from(aCategory));

        var request = MockMvcRequestBuilders
                .get("/categories/{id}", expectedId.getValue())
                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
       response.andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.id", equalTo(expectedId.getValue())))
                    .andExpect(jsonPath("$.name", equalTo(expectedName)))
                    .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                    .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                    .andExpect(jsonPath("$.created_at", equalTo(Objects.toString(aCategory.getCreatedAt()))))
                    .andExpect(jsonPath("$.updated_at", equalTo(Objects.toString(aCategory.getUpdatedAt()))))
                    .andExpect(jsonPath("$.deleted_at", equalTo(null)));

    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        //given
        final var expectedErrorMessage = "The Category with id 123 was not found";
        final var expectedId = CategoryID.from("123");

        //when
        doThrow(NotFoundException.with(Category.class, expectedId))
                .when(getByIdUserCase).execute(any());

        var request = MockMvcRequestBuilders
                .get("/categories/{id}", expectedId.getValue())
                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldUpdatedCatogory() throws Exception {
        //given
        var expectedId = "123";
        var expectedName = "Filme";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var contentRequest =
                new UpdateCategoryRequest(expectedName, expectedDescription, expectedActive);

        //when
        Mockito.when(updateUseCase.execute(any()))
                .thenReturn(right(UpdateCategoryOutput.with(expectedId)));

        var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isOk())
                .andExpect(header().string("Location", "/categories/%s".formatted(expectedId)))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        //given
        var expectedName = "";
        var expectedDescription = "Catergoria mais assistida";
        var expectedActive = true;

        final var contentRequest =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedActive);

        //when
        Mockito.when(updateUseCase.execute(any()))
                .thenReturn(left(Notification.create(new Error("'name' should not be null"))));

        var request = MockMvcRequestBuilders.put("/categories/123")
                .with(ApiTest.CATEGORIES_JWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo("'name' should not be null")));

        verify(updateUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDescription, cmd.description());
                    Assertions.assertEquals(expectedActive, cmd.isActive());
                    return true;
                }
        ));
    }
    @Test
    public void givenAValidParams_whenCallListCategories_shouldReturnCategories() throws Exception {
        //give
        final var category = Category.newCategory("name", "description",true);
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;
        final var expectedItems = List.of(CategoryListOutput.from(category));
        var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems);

        //when
        when(listUseCase.execute(any()))
                .thenReturn(pagination);

        var request = MockMvcRequestBuilders.get("/categories")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("search", expectedTerms)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(category.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(category.getName())))
                .andExpect(jsonPath("$.items[0].description", equalTo(category.getDescription())))
                .andExpect(jsonPath("$.items[0].is_active", equalTo(true)))
                .andExpect(jsonPath("$.items[0].created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(null)));


        verify(listUseCase, times(1)).execute(argThat(cmd -> {
            Assertions.assertEquals(expectedPage, cmd.page());
            Assertions.assertEquals(expectedPerPage, cmd.perPage());
            Assertions.assertEquals(expectedTerms, cmd.terms());
            Assertions.assertEquals(expectedSort, cmd.sort());
            Assertions.assertEquals(expectedDirection, cmd.direction());
            return true;
        }));

    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldReturnNoContent() throws Exception {
        // given
        final var expectedId = "123";

        doNothing()
                .when(deleteUseCase).execute(any());

        // when
        final var request = MockMvcRequestBuilders.delete("/categories/{id}", expectedId)
                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isNoContent());

        verify(deleteUseCase, times(1)).execute(eq(expectedId));
    }

    }
