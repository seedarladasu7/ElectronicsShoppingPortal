package com.portal.shopping.electronics.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDataDTO {

	private String orderPlacedOn;
	private String paymentMode;
	private String deliveryMode;
	private List<CartProductDTO> cartProducts;

}
