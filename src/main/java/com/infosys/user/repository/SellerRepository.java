package com.infosys.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.infosys.user.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, String> {

	Seller findByEmail(String Email);
	
}

