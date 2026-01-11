package ru.jabki.x6.product.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.x6.product.exception.BadRequestException;
import ru.jabki.x6.product.model.Product;

@Repository
@AllArgsConstructor
public class ProductRepository {
    private static final String INSERT = """
               INSERT INTO x6_product.product(name, description, price, category)
               VALUES (:name, :description, :price, :category)
               RETURNING *;   
            """;

    private static final String UPDATE = """
            UPDATE x6_product.product
            SET name = :name, description = :description, price = :price, category = :category
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM x6_product.product
            WHERE id = :id;
            """;
    private static final String GET_BY_ID = """
            SELECT * 
            FROM x6_product.product
            WHERE id = :id;
            """;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;

    public Product insert(final Product product) {
        return jdbcTemplate.queryForObject(INSERT, productToSql(product), productMapper);
    }

    public Product update(final Product product) {
        return jdbcTemplate.queryForObject(UPDATE, productToSql(product), productMapper);
    }

    public void delete(final Long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        } catch (Exception e) {
            throw new BadRequestException(String.format("Товар с id: %d не найден", id));
        }
    }

    public Product getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), productMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Товар с id: %d не найден", id));
        }
    }

    private MapSqlParameterSource productToSql(final Product product) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", product.getId());
        params.addValue("name", product.getName());
        params.addValue("description", product.getDescription());
        params.addValue("price", product.getPrice());
        params.addValue("category", product.getCategory());
        return params;
    }
}