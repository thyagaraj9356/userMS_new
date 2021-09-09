package com.infosys.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.infosys.user.dto.BuyerDTO;
import com.infosys.user.dto.CartDTO;
import com.infosys.user.dto.LoginDTO;
import com.infosys.user.dto.ProductDTO;
import com.infosys.user.dto.WishlistDTO;
import com.infosys.user.entity.Buyer;
import com.infosys.user.exception.InfyException;
import com.infosys.user.service.BuyerService;
import com.infosys.user.service.SellerService;

@RestController
@CrossOrigin
@RequestMapping
public class BuyerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	Environment environment;
	@Value("${product.uri}")
	String product;

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	BuyerService buyerservice;
	@Autowired
	SellerService sellerservice;

	@PostMapping(value = "/api/buyer/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) throws InfyException {
		try {
			String successMessage = environment.getProperty("INSERT_SUCCESS");
			logger.info("Registration request for buyer with data {}", buyerDTO);
			buyerservice.saveBuyer(buyerDTO);
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/api/buyers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BuyerDTO>> getAllBuyer() throws InfyException {
		try {
			List<BuyerDTO> buyerDTOs = buyerservice.getAllBuyer();
			return new ResponseEntity<>(buyerDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/buyer/{buyerid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BuyerDTO> getBuyerById(@PathVariable String buyerid) throws InfyException {
		try {
			BuyerDTO buyer = buyerservice.getBuyerById(buyerid);
			return new ResponseEntity<>(buyer, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@PostMapping(value = "/buyer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws InfyException {
		try {
			buyerservice.login(loginDTO);
			logger.info("Login request for buyer {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
			String successMessage = environment.getProperty("LOGIN_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@DeleteMapping(value = "/buyer/{buyerid}")
	public ResponseEntity<String> deleteBuyer(@PathVariable String buyerid) throws InfyException {
		try {
			buyerservice.deleteBuyer(buyerid);
			String successMessage = environment.getProperty("DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}
/*
	@PostMapping(value = "/api/wishlist/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveWishlist(@RequestBody WishlistDTO wishlistDTO) throws InfyException {
		try {
			logger.info("Creation request for customer {} with data {}", wishlistDTO);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prodid", wishlistDTO.getProdid().getProdid());
			System.out.println("adding map" + map);
			ProductDTO projectDTOs = restTemplate.getForObject("http://localhost:8100/api/verifyprod/{prodid}",
					ProductDTO.class, map);
			System.out.println("adding wishlist" + projectDTOs);
			buyerservice.createWishlist(wishlistDTO);
			String successMessage = environment.getProperty("WISHLIST_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}

	}
*/
	@DeleteMapping(value = "/wishlist/{buyerid}")
	public ResponseEntity<String> deleteWishlist(@PathVariable String buyerid) throws Exception {
		try {
			buyerservice.deleteWishlist(buyerid);
			String successMessage = environment.getProperty("DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);

		}

	}
/*	@PostMapping(value = "/api/cart/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveCart(@RequestBody CartDTO cartDTO) throws InfyException {
		try {
			logger.info("Creation request for customer {} with data {}", cartDTO);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("buyerid", cartDTO.getBuyerid().getBuyerid());
			map.put("prodid", cartDTO.getProdid().getProdid());
			System.out.println("adding map" + map);
			ProductDTO projectDTOs = restTemplate.getForObject("http://localhost:8100/api/verifyprod/{prodid}",
					ProductDTO.class, map);
			System.out.println("adding cart" + projectDTOs);
			buyerservice.createCart(cartDTO);
			String successMessage = environment.getProperty("CART_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);

		}

	}
*/
	@DeleteMapping(value = "/api/cart/{buyerid}")
	public ResponseEntity<String> deleteCart(@PathVariable String buyerid) throws Exception {
		try {
			buyerservice.deleteCart(buyerid);
			String successMessage = environment.getProperty("DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);

		}

	}

	@RequestMapping(value = "/api/isprivilege/{buyerid}", method = RequestMethod.PUT)
	public ResponseEntity<Buyer> updateIsprivilege(@RequestBody Buyer buyer, @PathVariable String buyerid)
			throws InfyException {
		try {
			Buyer buyers = buyerservice.updateIsprivilege(buyer, buyerid);
			return new ResponseEntity<>(buyers, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}

	}

}
