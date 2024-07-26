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

import com.macedo.Product.dtos.ProductDTO;
import com.macedo.Product.entities.Product;
import com.macedo.Product.service.ProductService;

@RefreshScope
@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(ProductDTO filtro) {
        return new ResponseEntity<List<ProductDTO>>((productService.getProducts(filtro)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return new ResponseEntity<Product>((productService.getProductById(id)), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Integer id) {
        return new ResponseEntity<List<ProductDTO>>((productService.getProductsByCategoryId(id)), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return new ResponseEntity<Product>((productService.saveProduct(product)), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.createProduct(Product)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.updateProduct(id, Product)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Integer id,
            @RequestBody ProductDTO ProductIncompletaDTO) {
        return new ResponseEntity<ProductDTO>((productService.patchProduct(id, ProductIncompletaDTO)), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


}
