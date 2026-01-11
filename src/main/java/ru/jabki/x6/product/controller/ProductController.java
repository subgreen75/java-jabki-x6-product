package ru.jabki.x6.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.jabki.x6.product.model.ApiStatusFind;
import ru.jabki.x6.product.model.Product;
import ru.jabki.x6.product.service.ProductService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name = "Товары")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Создать товар")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить товар по id")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить товар по id")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление товара")
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }


    @GetMapping("/exists/{id}")
    @Operation(summary = "Проверить существует ли товар по id")
    public ResponseEntity<ApiStatusFind> existsById(@PathVariable("id") Long id) {
        Boolean exists = productService.existsById(id);
        return ResponseEntity.ok()
                .body(
                        new ApiStatusFind(
                                exists,
                                (exists ? "Товар с id " + id + " найден" : "Товара с id " + id + " не существует")
                        )
                );
    }
}