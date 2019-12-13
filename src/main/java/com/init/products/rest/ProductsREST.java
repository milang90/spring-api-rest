package com.init.products.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.init.products.entitys.Product;
import com.init.products.dao.ProductsDAO;

@RestController
@RequestMapping("/products")
public class ProductsREST {
	
	@Autowired
	private ProductsDAO productDAO;
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> products = productDAO.findAll();
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping(value="{productId}", method=RequestMethod.GET)
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		Optional<Product> OptinalProduct = productDAO.findById(productId);
		if(OptinalProduct.isPresent()) {
			return ResponseEntity.ok(OptinalProduct.get());
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Product> createdProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	@DeleteMapping(value="{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productDAO.deleteById(productId);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<Product> UpdateProduct(@RequestBody Product product){
		Optional<Product> optinalProduct = productDAO.findById(product.getId());
		if(optinalProduct.isPresent()) {
			Product updateProduct = optinalProduct.get();
			updateProduct.setName(product.getName());
			productDAO.save(updateProduct);
			return ResponseEntity.ok(optinalProduct.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
