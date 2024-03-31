package com.sysm.catalog.admin.infrastructure.api.controller;

import com.sysm.catalog.admin.application.category.create.CreateCategoryCommand;
import com.sysm.catalog.admin.application.category.create.CreateCategoryOutput;
import com.sysm.catalog.admin.application.category.create.CreateCategoryUseCase;
import com.sysm.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryCommand;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryOutput;
import com.sysm.catalog.admin.application.category.update.UpdateCategoryUseCase;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;
import com.sysm.catalog.admin.domain.validation.handler.Notification;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryGetResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CreateCategoryRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.UpdateCategoryRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.presenters.CategoryApiPresenter;
import com.sysm.catalog.admin.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase, UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @Override
    public ResponseEntity<?> createCategory( CreateCategoryRequest request) {

        System.out.println("Name: "+request);

        CreateCategoryCommand command = new CreateCategoryCommand(
                request.name(),
                request.description(),
                request.active() != null && request.active()
        );

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity
                        .unprocessableEntity()
                        .body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity
                        .created(URI.create("/categories/" + output.id()))
                        .body(output);

        return createCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, Integer page, Integer perPage, String sort, String direction) {
        return listCategoriesUseCase.execute(new SearchQuery(page, perPage, search, sort, direction));
    }

    @Override
    public CategoryGetResponse getCategoryById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCategoryRequest request) {
        UpdateCategoryCommand command = new UpdateCategoryCommand(
                id,
                request.name(),
                request.description(),
                request.active() != null && request.active()
        );

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity
                        .unprocessableEntity()
                        .body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity
                        .ok()
                        .location(URI.create("/categories/" + output.id()))
                        .body(output);

        return updateCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String id) {
        deleteCategoryUseCase.execute(id);
    }
}
