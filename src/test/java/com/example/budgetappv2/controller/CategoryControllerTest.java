package com.example.budgetappv2.controller;

import com.example.budgetappv2.category.Category;
import com.example.budgetappv2.category.CategoryController;
import com.example.budgetappv2.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    private ResponseEntity<Category> category1;
    private ResponseEntity<List<Category>> categories;

    @BeforeEach
    public void setUp() {
        category1 = ResponseEntity.ok(Category.builder()
                .id(1L)
                .budget(BigDecimal.valueOf(100))
                .name("test1")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.now())
                        .transactions(List.of())
                .build());

        ResponseEntity<Category> category2 = ResponseEntity.ok(Category.builder()
                .id(2L)
                .budget(BigDecimal.valueOf(200))
                .name("test2")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2021, 12, 31))
                .transactions(List.of())
                .build());

        categories = ResponseEntity.ok(List.of(Objects.requireNonNull(category1.getBody()), Objects.requireNonNull(category2.getBody())));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return all categories")
    public void shouldReturnAllCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> hasSize(2))
                .andExpect(result -> jsonPath("$[0].id", is(1L)))
                .andExpect(result -> jsonPath("$[0].name", is("test1")))
                .andExpect(result -> jsonPath("$[0].budget", is(100)))
                .andExpect(result -> jsonPath("$[0].startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$[0].endDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$[1].id", is(2L)))
                .andExpect(result -> jsonPath("$[1].name", is("test2")))
                .andExpect(result -> jsonPath("$[1].budget", is(200)))
                .andExpect(result -> jsonPath("$[1].startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$[1].endDate", is("2021-12-31")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return category by id")
    public void shouldReturnCategoryById() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(category1);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.name", is("test1")))
                .andExpect(result -> jsonPath("$.budget", is(100)))
                .andExpect(result -> jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$.endDate", is("2021-01-01")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should return category by name")
    public void shouldReturnCategoryByName() throws Exception {
        when(categoryService.getCategoryByName("test1")).thenReturn(category1);

        mockMvc.perform(get("/categories/name/test1"))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.name", is("test1")))
                .andExpect(result -> jsonPath("$.budget", is(100)))
                .andExpect(result -> jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$.endDate", is("2021-01-01")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should create category")
    public void addCategory() throws Exception {

        when(categoryService.addCategory(category1.getBody())).thenReturn(category1);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "test1",
                            "budget": 100,
                            "startDate": "2021-01-01",
                            "endDate": "2021-01-01"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.name", is("test1")))
                .andExpect(result -> jsonPath("$.budget", is(100)))
                .andExpect(result -> jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$.endDate", is("2021-01-01")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should update category")
    public void updateCategory() throws Exception {

        when(categoryService.updateCategory(category1.getBody())).thenReturn(category1);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "test1",
                            "budget": 100,
                            "startDate": "2021-01-01",
                            "endDate": "2021-01-01"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(result -> contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> jsonPath("$.id", is(1L)))
                .andExpect(result -> jsonPath("$.name", is("test1")))
                .andExpect(result -> jsonPath("$.budget", is(100)))
                .andExpect(result -> jsonPath("$.startDate", is("2021-01-01")))
                .andExpect(result -> jsonPath("$.endDate", is("2021-01-01")));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Should delete category by id")
    public void deleteCategoryById() throws Exception {
        when(categoryService.deleteCategoryById(1L)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk());
    }
}
