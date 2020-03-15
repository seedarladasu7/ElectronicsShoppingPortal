package com.portal.shopping.electronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
	
	private String productName;
	private Integer quantity;
	private Double price;

}
