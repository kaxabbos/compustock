package com.compustock.service;

import com.compustock.model.Product;
import com.compustock.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void add(Product product) {
        productRepo.save(product);
    }

    public Product addAndFlush(Product product) {
        return productRepo.saveAndFlush(product);
    }

    public void update(Product product) {
        productRepo.save(product);
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public void delete(Product product) {
        productRepo.delete(product);
    }

    public Product find(Long id) {
        return productRepo.findProductById(id);
    }

    public List<Product> findAllByOrderByName() {
        return productRepo.findAllByOrderByName();
    }

    public Product findByName(String name) {
        return productRepo.findByName(name);
    }

    public List<Product> findAllByOrderByIdDesc() {
        return productRepo.findAllByOrderByIdDesc();
    }

}
