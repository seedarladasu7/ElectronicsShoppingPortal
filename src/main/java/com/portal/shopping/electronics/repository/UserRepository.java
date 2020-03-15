package com.portal.shopping.electronics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.shopping.electronics.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
