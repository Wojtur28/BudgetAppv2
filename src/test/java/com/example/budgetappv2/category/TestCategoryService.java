package com.example.budgetappv2.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestCategoryService {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    @DisplayName("Should get all categories and return status 200")
    public void getAllCategories_returnsFound() throws Exception {
        //given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        when(categoryService.getAllCategories()).thenReturn(new ResponseEntity<>(categories, HttpStatus.OK));
        //when
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should get category by id and return status 200 if found")
    public void getCategoryById_returnsFound() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        when(categoryService.getCategoryById(1L)).thenReturn(new ResponseEntity<>(category, HttpStatus.OK));
        //when
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Category> response = categoryController.getCategoryById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should add category and return status 200 if created")
    public void addCategory_returnsCreated() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        when(categoryService.addCategory(category)).thenReturn(new ResponseEntity<>(category, HttpStatus.CREATED));
        //when
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Category> response = categoryController.addCategory(category);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should update category and return status 200 if updated")
    public void updateCategory_returnsUpdated() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        when(categoryService.updateCategory(category)).thenReturn(new ResponseEntity<>(category, HttpStatus.OK));
        //when
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<Category> response = categoryController.updateCategory(category);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    @DisplayName("Should delete category by id and return status 200 if deleted")
    public void deleteCategoryById_returnsDeleted() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        when(categoryService.deleteCategoryById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        //when
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        ResponseEntity<HttpStatus> response = categoryController.deleteCategoryById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
