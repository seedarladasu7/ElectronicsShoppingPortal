package com.portal.shopping.electronics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portal.shopping.electronics.entity.Purchase;
import com.portal.shopping.electronics.entity.User;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

	List<Purchase> findByUser(User user);

}
