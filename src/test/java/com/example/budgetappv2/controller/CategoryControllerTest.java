package com.example.budgetappv2.controller;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.CategoryController;
import com.example.budgetappv2.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    private ResponseEntity<Category> category1;
    private ResponseEntity<Category> category2;
    private ResponseEntity<List<Category>> categories;

    @BeforeEach
    public void setUp() {
        category1 = ResponseEntity.ok(Category.builder()
                .id(1L)
                .budget(BigDecimal.valueOf(100))
                .name("test1")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.now())
                .transactions(null)
                .build());

        category2 = ResponseEntity.ok(Category.builder()
                .id(2L)
                .budget(BigDecimal.valueOf(200))
                .name("test2")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .transactions(null)
                .build());

        categories = ResponseEntity.ok(List.of(Objects.requireNonNull(category1.getBody()), Objects.requireNonNull(category2.getBody())));
    }

    //TODO: fix test
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return all categories")
    public void shouldReturnAllCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(categories);


    }
}
