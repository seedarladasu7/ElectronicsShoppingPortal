package com.portal.shopping.electronics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer purchaseId;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cart_id")
	@JsonBackReference
	private Cart cart;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_mode_id")
	@JsonBackReference
	private PaymentMode paymentMode;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "delivery_mode_id")
	@JsonBackReference
	private DeliveryMode deliveryMode;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@Column
	private String purchaseOn;

}
