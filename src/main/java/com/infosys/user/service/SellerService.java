package com.infosys.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.user.dto.LoginDTO;
import com.infosys.user.dto.SellerDTO;
import com.infosys.user.entity.Seller;
import com.infosys.user.exception.InfyException;
import com.infosys.user.repository.SellerRepository;

@Service
@Transactional
public class SellerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SellerRepository sellerrepo;

	public void saveSeller(SellerDTO sellerDTO) throws InfyException {
		logger.info("Registration request for seller with data {}", sellerDTO);
		Seller seller = sellerDTO.createSeller();
		sellerrepo.save(seller);
	}

	public List<SellerDTO> getAllSeller() throws InfyException {

		Iterable<Seller> sellers = sellerrepo.findAll();
		List<SellerDTO> sellerDTOs = new ArrayList<>();

		sellers.forEach(seller -> {
			SellerDTO sellerDTO = SellerDTO.valueOf(seller);
			sellerDTOs.add(sellerDTO);
		});
		if (sellerDTOs.isEmpty())
			throw new InfyException("SELLERS_NOT_FOUND");
		logger.info("Seller Details : {}", sellerDTOs);
		return sellerDTOs;
	}

	public boolean login(LoginDTO loginDTO) throws InfyException {
		logger.info("Login request for selelr {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
		Seller sell = sellerrepo.findByEmail(loginDTO.getEmail());
		if (sell != null && sell.getPassword().equals(loginDTO.getPassword())) {
			return true;
		} else {
			throw new InfyException("Service.DETAILS_NOT_FOUND");
		}
	}

	public void deleteSeller(String sellerid) throws InfyException {
		Optional<Seller> seller = sellerrepo.findById(sellerid);
		seller.orElseThrow(() -> new InfyException("SELLERS_NOT_FOUND"));
		sellerrepo.deleteById(sellerid);
	}
}
