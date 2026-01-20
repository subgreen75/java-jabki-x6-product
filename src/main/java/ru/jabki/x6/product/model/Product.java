package ru.jabki.x6.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private long id;
    private String name;
    private String description;
    private Float price;
    private String category;
}