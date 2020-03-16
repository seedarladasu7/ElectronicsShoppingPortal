package com.portal.shopping.electronics.model;

import java.util.List;

import com.portal.shopping.electronics.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
	
	private List<ProductDTO> products;

}
