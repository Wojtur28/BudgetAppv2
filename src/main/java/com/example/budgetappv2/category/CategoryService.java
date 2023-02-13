package com.example.budgetappv2.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Category> getCategoryById(Long id) {
        try {
            return new ResponseEntity<>(categoryRepository.findById(id).stream().findFirst()
                    .orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getCategoryById\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getAllCategory\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Category> getCategoryByName(String name) {
        try {
            return new ResponseEntity<>(categoryRepository.findGroupByName(name), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getCategoryByName\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Category> addCategory(Category category) {
        try {
            List<Category> allCategories = categoryRepository.findAll();
            for (Category c : allCategories) {
                if (c.getName().equals(category.getName())) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.CREATED);
        }  catch (Exception e) {
            log.error("Error with \"addCategory\"");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Category> updateCategory(Category category) {
        try {
            Category categoryData = categoryRepository.findById(category.getId()).stream()
                    .findFirst()
                    .orElse(null);
            if (categoryData == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            categoryData.setBudget(category.getBudget());
            categoryData.setName(category.getName());
            categoryData.setStartDate(category.getStartDate());
            categoryData.setEndDate(category.getEndDate());
            return new ResponseEntity<>(categoryRepository.save(categoryData), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"updateCategory\"");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error with \"deleteCategory\"");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
