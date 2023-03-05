package com.example.budgetappv2.service;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.CategoryRepository;
import com.example.budgetappv2.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    private List<Category> categories;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
                .id(1L)
                .budget(BigDecimal.valueOf(100))
                .name("test")
                .startDate(LocalDate.of(2021, Month.JANUARY, 1))
                .endDate(LocalDate.now())
                .build();

        categories = List.of(category);
    }

    @Test
    @DisplayName("Should return all categories")
    public void shouldReturnAllCategories() {

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> foundCategories = categoryService.getCategories().getBody();

        assertEquals(categories, foundCategories);

    }

    @Test
    @DisplayName("Should return category by id")
    public void shouldReturnCategoryById() {

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(category));

        Category foundCategory = categoryService.getCategoryById(1L).getBody();

        assertEquals(category, foundCategory);
    }

    @Test
    @DisplayName("Should return category by name")
    public void shouldReturnCategoryByName() {

        when(categoryRepository.findCategoryByName("test")).thenReturn(Optional.ofNullable(category));

        Category foundCategory = categoryService.getCategoryByName("test").getBody();

        assertEquals(category, foundCategory);
    }

    @Test
    @DisplayName("Should create new category")
    public void shouldCreateCategory() {

        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.addCategory(category).getBody();

        assertEquals(category, createdCategory);
    }

    @Test
    @DisplayName("Should update category")
    public void shouldUpdateCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));

        when(categoryRepository.save(category)).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(category).getBody();

        assertEquals(category, updatedCategory);
    }

    @Test
    @DisplayName("Should delete category")
    public void shouldDeleteCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));

        ResponseEntity<HttpStatus> response = categoryService.deleteCategoryById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
