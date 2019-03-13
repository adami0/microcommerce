package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    public default List<Product> findAll() {
        return null;
    }

    public default Product findById(int id) {
        return null;
    }

    public default Product save(Product product) {
        return null;
    }
}
