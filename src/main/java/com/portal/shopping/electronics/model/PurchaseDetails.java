package com.portal.shopping.electronics.model;

import java.util.List;

import com.portal.shopping.electronics.dto.PurchaseDataDTO;

import lombok.Data;

@Data
public class PurchaseDetails {
	
	private String userName;
	private List<PurchaseDataDTO> purchaseData;

}
