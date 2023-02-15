/*
package com.example.budgetappv2.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CategoryControllerTests {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    private final List<Category> categories = new ArrayList<>(Arrays.asList(
            Category.builder()
                    .id(1L)
                    .budget(BigDecimal.valueOf(1000))
                    .name("Food")
                    .startDate(LocalDate.of(2021, 1, 1))
                    .endDate(LocalDate.of(2022, 1, 1))
                    .build(),
            Category.builder()
                    .id(2L)
                    .budget(BigDecimal.valueOf(2000))
                    .name("Transport")
                    .startDate(LocalDate.of(2021, 5, 1))
                    .endDate(LocalDate.of(2022, 1, 1))
                    .build()
    ));

    @Test
    @DisplayName("Should get all categories and return status 200")
    public void should_get_all_categories() throws Exception {

        when(categoryService.getAllCategories()).thenReturn(new ResponseEntity<>(categories, HttpStatus.OK));

        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Food"))
                .andExpect(jsonPath("$[0].budget").value(1000))
                .andExpect(jsonPath("$[0].startDate").value("2021-01-01"))
                .andExpect(jsonPath("$[0].endDate").value("2022-01-01"))
                .andExpect(jsonPath("$[1].name").value("Transport"))
                .andExpect(jsonPath("$[1].budget").value(2000))
                .andExpect(jsonPath("$[1].startDate").value("2021-05-01"))
                .andExpect(jsonPath("$[1].endDate").value("2022-01-01"))
                .andDo(print());

        ResponseEntity<List<Category>> response = categoryService.getAllCategories();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(categories, response.getBody());
    }

    @Test
    @DisplayName("Should get category by id and return status 200")
    public void should_get_category_by_id() throws Exception {

        when(categoryService.getCategoryById(1L)).thenReturn(new ResponseEntity<>(categories.get(0), HttpStatus.OK));

        mockMvc.perform(get("/categories/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Food"))
                .andExpect(jsonPath("$.budget").value(1000))
                .andExpect(jsonPath("$.startDate").value("2021-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-01-01"))
                .andDo(print());

        ResponseEntity<Category> response = categoryService.getCategoryById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(categories.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should get category by wrong id and return status 404")
    public void should_get_category_by_wrong_id() throws Exception {

        when(categoryService.getCategoryById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/categories/3"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<Category> response = categoryService.getCategoryById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should add category and return status 201")
    public void should_post_category() throws Exception {

        Category category = Category.builder()
                .id(3L)
                .budget(BigDecimal.valueOf(3000))
                .name("Entertainment")
                .startDate(LocalDate.of(2021, 1, 1))
                .endDate(LocalDate.of(2022, 1, 1))
                .build();

        when(categoryService.addCategory(any(Category.class))).thenReturn(new ResponseEntity<>(category, HttpStatus.CREATED));

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"budget\": 3000,\n" +
                        "    \"name\": \"Entertainment\",\n" +
                        "    \"startDate\": \"2021-01-01\",\n" +
                        "    \"endDate\": \"2022-01-01\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Entertainment"))
                .andExpect(jsonPath("$.budget").value(3000))
                .andExpect(jsonPath("$.startDate").value("2021-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-01-01"))
                .andDo(print());

        ResponseEntity<Category> response = categoryService.addCategory(category);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(category, response.getBody());
    }

    @Test
    @DisplayName("Should add category with wrong data and return status 400")
    public void should_post_category_with_wrong_data() throws Exception {

        when(categoryService.addCategory(any(Category.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"budget\": 3000,\n" +
                        "    \"name\": \"Entertainment\",\n" +
                        "    \"startDate\": \"2021-01-01\",\n" +
                        "    \"endDate\": \"2022-01-01\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<Category> response = categoryService.addCategory(categories.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should update category and return status 200")
    public void should_put_category() throws Exception {

        when(categoryService.updateCategory(any(Category.class))).thenReturn(new ResponseEntity<>(categories.get(0), HttpStatus.OK));

        mockMvc.perform(put("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"budget\": 1000,\n" +
                        "    \"name\": \"Food\",\n" +
                        "    \"startDate\": \"2021-01-01\",\n" +
                        "    \"endDate\": \"2022-01-01\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Food"))
                .andExpect(jsonPath("$.budget").value(1000))
                .andExpect(jsonPath("$.startDate").value("2021-01-01"))
                .andExpect(jsonPath("$.endDate").value("2022-01-01"))
                .andDo(print());

        ResponseEntity<Category> response = categoryService.updateCategory(categories.get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(categories.get(0), response.getBody());
    }

    @Test
    @DisplayName("Should update category with wrong data and return status 400")
    public void should_put_category_with_wrong_data() throws Exception {

        when(categoryService.updateCategory(any(Category.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        mockMvc.perform(put("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"budget\": 1000,\n" +
                        "    \"name\": \"Food\",\n" +
                        "    \"startDate\": \"2021-01-01\",\n" +
                        "    \"endDate\": \"2022-01-01\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        ResponseEntity<Category> response = categoryService.updateCategory(categories.get(0));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete category by id and return status 200")
    public void should_delete_category_by_id() throws Exception {

        when(categoryService.deleteCategoryById(1L)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk())
                .andDo(print());

        ResponseEntity<HttpStatus> response = categoryService.deleteCategoryById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete category by wrong id and return status 404")
    public void should_delete_category_by_wrong_id() throws Exception {

        when(categoryService.deleteCategoryById(3L)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/categories/3"))
                .andExpect(status().isNotFound())
                .andDo(print());

        ResponseEntity<HttpStatus> response = categoryService.deleteCategoryById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
*/
