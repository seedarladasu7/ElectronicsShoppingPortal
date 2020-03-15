package com.portal.shopping.electronics.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.shopping.electronics.dto.ProductsDTO;
import com.portal.shopping.electronics.model.ProductToCartRequest;
import com.portal.shopping.electronics.model.PurchaseDetails;
import com.portal.shopping.electronics.model.PurchaseRequest;
import com.portal.shopping.electronics.service.ElectronicsShoppingService;

@RestController
public class ElectronicsShoppingPortalController {

	@Autowired
	ElectronicsShoppingService service;

	@GetMapping(value = { "/products", "/products/{productName}" })
	public ResponseEntity<List<ProductsDTO>> getProducts(
			@PathVariable(name = "productName") Optional<String> productName) {
		return new ResponseEntity<>(service.getElectronicProducts(productName), HttpStatus.OK);

	}

	@PostMapping("/addToCart")
	public ResponseEntity<String> addProductsToCart(@RequestBody ProductToCartRequest request) {
		service.addElectronicProductsToCart(request);
		return new ResponseEntity<>("Product(s) added to cart successfully...", HttpStatus.OK);
	}

	@PostMapping("/purchageCart")
	public ResponseEntity<String> purchageCartProducts(@RequestBody PurchaseRequest request) {
		service.confirmPurchase(request);
		return new ResponseEntity<>("Order placed successfully...", HttpStatus.OK);
	}

	@GetMapping(value = { "/orders/{userId}" })
	public ResponseEntity<PurchaseDetails> getUserOrders(@PathVariable(name = "userId") Integer userId) {
		return new ResponseEntity<>(service.getUserOrders(userId), HttpStatus.OK);

	}
}
