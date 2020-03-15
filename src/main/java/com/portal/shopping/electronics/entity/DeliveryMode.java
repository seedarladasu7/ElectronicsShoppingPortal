package com.portal.shopping.electronics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_mode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deliveryModeId;

	@Column
	private String deliveryModeName;

}
