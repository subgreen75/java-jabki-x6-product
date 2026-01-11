package ru.jabki.x6.product.model;

import lombok.Data;
@Data
public class ApiStatusFind {
    //статус поиска. если найдено status = true, иначе = false
    final boolean status;
    final String message;
}