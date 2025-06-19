/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.mavenproject1.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
