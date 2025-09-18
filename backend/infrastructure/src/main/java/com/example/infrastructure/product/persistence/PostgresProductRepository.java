package com.example.infrastructure.product.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.application.product.port.out.ProductRepository;
import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

@Repository
public class PostgresProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> productRowMapper;

    public PostgresProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRowMapper = (rs, rowNum) -> {
            ProductId id = ProductId.of(rs.getString("id"));
            String code = rs.getString("code");
            String name = rs.getString("name");
            String description = rs.getString("description");
            byte[] image = rs.getBytes("image");
            String category = rs.getString("category");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String internalReference = rs.getString("internal_reference");
            int shellId = rs.getInt("shell_id");
            Product.InventoryStatus inventoryStatus = Product.InventoryStatus.valueOf(rs.getString("inventory_status"));
            int rating = rs.getInt("rating");
            long createdAt = rs.getTimestamp("created_at").getTime();
            long updatedAt = rs.getTimestamp("updated_at").getTime();
            
            Product product = new Product(
                id, code, name, description, image, category, 
                price, quantity, internalReference, shellId, 
                inventoryStatus, rating, createdAt, updatedAt
            );
            
            return product;
        };
    }

    @Override
    public Product save(Product product) {
        if (findById(product.getId()).isPresent()) {
            updateProduct(product);
        } else {
            insertProduct(product);
        }
        return product;
    }

    private void insertProduct(Product product) {
        String sql = "INSERT INTO products(id, code, name, description, image, category, price, " +
                     "quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at) " +
                     "VALUES (?::uuid, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            product.getId().toString(),
            product.getCode(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity(),
            product.getInternalReference(),
            product.getShellId(),
            product.getInventoryStatus().name(),
            product.getRating(),
            new java.sql.Timestamp(product.getCreatedAt()),
            new java.sql.Timestamp(product.getUpdatedAt())
        );
    }

    private void updateProduct(Product product) {
        String sql = "UPDATE products SET code = ?, name = ?, description = ?, image = ?, " +
                     "category = ?, price = ?, quantity = ?, internal_reference = ?, " +
                     "shell_id = ?, inventory_status = ?, rating = ?, updated_at = ? " +
                     "WHERE id = ?::uuid";
        
        jdbcTemplate.update(sql,
            product.getCode(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity(),
            product.getInternalReference(),
            product.getShellId(),
            product.getInventoryStatus().name(),
            product.getRating(),
            new Timestamp(product.getUpdatedAt()),
            product.getId().toString()
        );
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        try {
            String sql = "SELECT * FROM products WHERE id = ?::uuid";
            Product product = jdbcTemplate.queryForObject(sql, productRowMapper, id.toString());
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public void deleteById(ProductId id) {
        String sql = "DELETE FROM products WHERE id = ?::uuid";
        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM products WHERE code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return count != null && count > 0;
    }
}