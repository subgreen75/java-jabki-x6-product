package ru.jabki.x6.product.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import ru.jabki.x6.product.exception.ProductException;
import ru.jabki.x6.product.model.Product;
import ru.jabki.x6.product.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "product", key = "#name.id()")
    public Product create(Product product) {
        validate(product);
        return productRepository.insert(product);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "product", key = "#id")
    public Product getById(long id) {
        final Product product = productRepository.getById(id);
        if (product == null) {
            throw new ProductException("Товар по id " + id + " не найден");
        }
        return product;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", key = "#id")
    public void delete(long id) {
        productRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "product", key = "#name.id()")
    public Product update(Product product) {
        validate(product);
        Product existsProduct = getById(product.getId());
        existsProduct.setName(product.getName());
        existsProduct.setDescription(product.getDescription());
        existsProduct.setPrice(product.getPrice());
        existsProduct.setCategory(product.getCategory());
        return productRepository.update(product);
    }

    private void validate(Product product) {
        if (product == null) {
            throw new ProductException("Товар не может быть пустым");
        }

        if (!StringUtils.hasText(product.getName())) {
            throw new ProductException("Наименование товара не может быть пустым");
        }

        if (!StringUtils.hasText(product.getDescription())) {
            throw new ProductException("Описание товара не может быть пустым");
        }

        if (product.getPrice() <= 0  ) {
            throw new ProductException("Цена должна быть положитиельной");
        }

        if (!StringUtils.hasText(product.getCategory())) {
            throw new ProductException("Категория товара не может быть пустой");
        }
    }

    @Transactional(readOnly = true)
    public Boolean existsById(long id) {
        try {
            final Product product = productRepository.getById(id);
            return product != null;
        } catch (Exception e) {
            return false;
        }
    }
}