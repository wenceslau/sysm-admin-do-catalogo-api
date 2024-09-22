package com.sysm.catalog.admin.infrastructure.api;

import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryGetResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CreateCategoryRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("categories")
@Tag(name = "Category", description = "Category API")
public interface CategoryAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest request);

    @GetMapping
    @Operation(summary = "List categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "422", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
           // consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    CategoryGetResponse getCategoryById(@PathVariable("id") String id);

    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody UpdateCategoryRequest request);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") String id);
}
