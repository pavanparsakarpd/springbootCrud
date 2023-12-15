package com.pp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pp.exceptions.ResourceNotFoundException;
import com.pp.model.Product;
import com.pp.repository.ProductRepository;

@RestController
@RequestMapping("/api/pp")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("prodcts")
	public List<Product> getAllProducts(){
		return this.productRepository.findAll();
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException{
		Product p=productRepository.findById(productId)
				.orElseThrow(()->new ResourceNotFoundException("Product id not found"+productId) );
		return ResponseEntity.ok().body(p);
		
	}
	@PostMapping("/products")
	public Product createProduct( @RequestBody Product employee) {
		return productRepository.save(employee);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateEmployee(@PathVariable(value = "id") Long productId,
			 @RequestBody Product productDetails) throws ResourceNotFoundException {
		Product p = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + productId));

		p.setName(productDetails.getName());
		p.setDescription(productDetails.getDescription());
		p.setPrice(productDetails.getPrice());
		final Product updatedProduct = productRepository.save(p);
		return ResponseEntity.ok(productDetails);
	}

	@DeleteMapping("/products/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId)
			throws ResourceNotFoundException {
		Product p = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + productId));

		productRepository.delete(p);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
