package com.portal.shopping.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portal.shopping.electronics.entity.DeliveryMode;

@Repository
public interface DeliveryModeRepository extends JpaRepository<DeliveryMode, Integer>{

}
