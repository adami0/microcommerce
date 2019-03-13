package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.ecommerce.microcommerce.dao.ProductDaoImpl.products;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @GetMapping(value = "/Produits")
    public MappingJacksonValue ProductsList() {

        List<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider filterList = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);

        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);

        filteredProducts.setFilters(filterList);

        return filteredProducts;
    }

    //Récupérer un produit par son Id
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {
        Product productAdded = productDao.save(product);

        if (productAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productAdded.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}