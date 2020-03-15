package com.portal.shopping.electronics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portal.shopping.electronics.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public List<Product> findByProductNameContainingIgnoreCase(String productName);
	
	public List<Product> findByProductNameContainingIgnoreCaseOrderByProductName(String productName);
	
	public List<Product> findByProductIdIn(List<Integer> productIds);

}
