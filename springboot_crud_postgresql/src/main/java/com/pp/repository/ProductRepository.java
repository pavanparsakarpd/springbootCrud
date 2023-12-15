package com.pp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
