package com.macedo.Product.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Product.dtos.CategoryDTO;
import com.macedo.Product.service.CategoryService;

@RefreshScope
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(CategoryDTO filtro) {
        return new ResponseEntity<List<CategoryDTO>>((categoryService.getCategories(filtro)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
            return new ResponseEntity<CategoryDTO>((categoryService.getCategoryById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO Category) {
            return new ResponseEntity<CategoryDTO>((categoryService.createCategory(Category)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id,
                    @RequestBody CategoryDTO Category) {
            return new ResponseEntity<CategoryDTO>((categoryService.updateCategory(id, Category)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoryDTO> patchCategory(@PathVariable Integer id,
                    @RequestBody CategoryDTO CategoryIncompletaDTO) {
            return new ResponseEntity<CategoryDTO>((categoryService.patchCategory(id, CategoryIncompletaDTO)),
                            HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
    }

}
