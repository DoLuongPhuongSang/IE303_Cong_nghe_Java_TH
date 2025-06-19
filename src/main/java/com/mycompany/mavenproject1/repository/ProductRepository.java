package com.mycompany.mavenproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.mavenproject1.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
