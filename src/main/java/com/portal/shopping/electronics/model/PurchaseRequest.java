package com.portal.shopping.electronics.model;

import lombok.Data;

@Data
public class PurchaseRequest {

	private int cartId;
	private int paymentModeId;
	private int deliveryModeId;
	private int userId;
}
