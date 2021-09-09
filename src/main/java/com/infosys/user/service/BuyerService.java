package com.infosys.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.user.dto.BuyerDTO;
import com.infosys.user.dto.CartDTO;
import com.infosys.user.dto.LoginDTO;
import com.infosys.user.dto.WishlistDTO;
import com.infosys.user.entity.Buyer;
import com.infosys.user.entity.Cart;
import com.infosys.user.entity.Wishlist;
import com.infosys.user.exception.InfyException;
import com.infosys.user.repository.BuyerRepository;
import com.infosys.user.repository.CartRepository;
import com.infosys.user.repository.WishlistRepository;

@Service
@Transactional
public class BuyerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BuyerRepository buyerrepo;

	@Autowired
	WishlistRepository wishrepo;

	@Autowired
	CartRepository cartrepo;

	public void saveBuyer(BuyerDTO buyerDTO) throws InfyException {
		logger.info("Registration request for buyer with data {}", buyerDTO);
		Buyer buyer = buyerDTO.createBuyer();
		buyerrepo.save(buyer);
	}

	public List<BuyerDTO> getAllBuyer() throws InfyException {

		Iterable<Buyer> buyers = buyerrepo.findAll();
		List<BuyerDTO> buyerDTOs = new ArrayList<>();

		buyers.forEach(buyer -> {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOs.add(buyerDTO);
		});
		if (buyerDTOs.isEmpty())
			throw new InfyException("BUYERS_NOT_FOUND");
		logger.info("Buyer Details : {}", buyerDTOs);
		return buyerDTOs;
	}

	public BuyerDTO getBuyerById(String buyerId) throws InfyException {
		BuyerDTO buyerDTO = null;
		logger.info("Profile request for buyer {}", buyerId);
		Optional<Buyer> optBuyer = buyerrepo.findById(buyerId);
		if (optBuyer.isPresent()) {
			Buyer buyer = optBuyer.get();
			buyerDTO = BuyerDTO.valueOf(buyer);
		} else {
			throw new InfyException("BUYERS_NOT_FOUND");
		}
		logger.info("Profile for buyer : {}", buyerDTO);
		return buyerDTO;
	}

	public boolean login(LoginDTO loginDTO) throws InfyException {
		logger.info("Login request for buyer {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
		Buyer buy = buyerrepo.findByEmail(loginDTO.getEmail());
		if (buy != null && buy.getPassword().equals(loginDTO.getPassword())) {
			return true;
		} else {
			throw new InfyException("DETAILS_NOT_FOUND");
		}
	}

	public void deleteBuyer(String buyerid) throws InfyException {
		Optional<Buyer> buyer = buyerrepo.findById(buyerid);
		buyer.orElseThrow(() -> new InfyException("BUYERS_NOT_FOUND"));
		buyerrepo.deleteById(buyerid);
	}

	public void createWishlist(WishlistDTO wishlistDTO) throws InfyException {
		logger.info("Creation request for customer {} with data {}", wishlistDTO);
		Wishlist wishlist = wishlistDTO.createEntity();
		System.out.println("wishlist" + wishlist);
		if (wishlist != null) {
			wishrepo.save(wishlist);
		} else {
			throw new InfyException("NO_WISHLIST");
		}

	}

	public void deleteWishlist(String buyerid) throws InfyException {
		Optional<Wishlist> buyer = wishrepo.findById(buyerid);
		buyer.orElseThrow(() -> new InfyException("Buyer_NOT_FOUND"));
		wishrepo.deleteById(buyerid);
	}
	
	public void createCart(CartDTO cartDTO) throws InfyException {
		logger.info("Adding product to cart request for customer {} with data {}", cartDTO);
		Cart cart = cartDTO.createEntity();
		System.out.println("cart" + cart);
		if (cart != null) {
			cartrepo.save(cart);
		} else {
			throw new InfyException("NO_CART_DETAILS");
		}

	}

	public void deleteCart(String buyerid) throws InfyException {
		Optional<Cart> buyer = cartrepo.findById(buyerid);
		buyer.orElseThrow(() -> new InfyException("Buyer_NOT_FOUND"));
		cartrepo.deleteById(buyerid);
	}
	public Buyer updateIsprivilege(Buyer buyer, String buyerid) throws InfyException{
		Buyer existingBuyer = buyerrepo.findById(buyerid).orElse(null);
		if ((existingBuyer != null) && (existingBuyer.getRewardpoints() >= 10000)) {
			existingBuyer.setIsprivileged(buyer.getIsprivileged());
			return buyerrepo.save(existingBuyer);
		}else {
			throw new InfyException("NO_REWARD_POINTS");
		}
	}

}
