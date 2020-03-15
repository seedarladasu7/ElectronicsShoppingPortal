package com.portal.shopping.electronics.model;

import java.util.List;

import lombok.Data;

@Data
public class ProductToCartRequest {

	private int userId;

	private List<ProductAndQuantity> productsList;

}
