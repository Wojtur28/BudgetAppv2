package com.example.budgetappv2.category;

import com.example.budgetappv2.category.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static com.example.budgetappv2.category.mapper.CategoryDtoMapper.mapCategoryToCategoryDto;
import static com.example.budgetappv2.category.mapper.CategoryMapper.mapToCategory;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final long EMPTY_ID = -1;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/name")
    public ResponseEntity<Stream<CategoryDto>> getCategoriesNames() {
        return mapCategoryToCategoryDto(categoryService.getCategories());
    }
    //TODO: I have to change name of this method. It's not returning names, but whole categories.

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
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
