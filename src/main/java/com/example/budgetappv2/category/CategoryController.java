package com.example.budgetappv2.category;

import com.example.budgetappv2.category.dto.CategoryDto;
import com.example.budgetappv2.category.dto.CategoryReadDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static com.example.budgetappv2.category.mapper.CategoryMapper.mapToCategory;
import static com.example.budgetappv2.category.mapper.CategoryReadDtoMapper.mapCategoriesToCategoriesReadDto;
import static com.example.budgetappv2.category.mapper.CategoryReadDtoMapper.mapCategoryToCategoryReadDto;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final long EMPTY_ID = -1;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Stream<CategoryReadDto>> getCategories() {
        return mapCategoriesToCategoriesReadDto(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReadDto> getCategoryById(@PathVariable long id) {
        return mapCategoryToCategoryReadDto(categoryService.getCategoryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryReadDto> getCategoryByName(@PathVariable String name) {
        return mapCategoryToCategoryReadDto(categoryService.getCategoryByName(name));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(mapToCategory(EMPTY_ID, categoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(mapToCategory(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategoryById(@PathVariable long id) {
        return categoryService.deleteCategoryById(id);
    }




}
