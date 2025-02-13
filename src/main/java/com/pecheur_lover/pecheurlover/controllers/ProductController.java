package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.ProductDao;
import com.pecheur_lover.pecheurlover.entities.Orders;
import com.pecheur_lover.pecheurlover.entities.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productDao.findAll());
    }

    @GetMapping("/{id_product}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id_product){
        return ResponseEntity.ok(productDao.findById(id_product));
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        Product createdProduct = productDao.save(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id_product}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id_product, @RequestBody Product product) {
        Product updatedProduct = productDao.update(id_product, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id_product}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id_product) {
        if(productDao.delete(id_product)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
