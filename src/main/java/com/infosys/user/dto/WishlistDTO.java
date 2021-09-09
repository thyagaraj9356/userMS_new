package com.infosys.user.dto;


import com.infosys.user.entity.Wishlist;

public class WishlistDTO {

	String buyerid;
	ProductDTO prodid;
	public String getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}
	public ProductDTO getProdid() {
		return prodid;
	}
	public void setProdid(ProductDTO prodid) {
		this.prodid = prodid;
	}
	@Override
	public String toString() {
		return "WishlistDTO [buyerid=" + buyerid + ", prodid=" + prodid + "]";
	}
	public static WishlistDTO valueOf(Wishlist wishlist) {
		WishlistDTO wishlistDTO = new WishlistDTO();
		wishlistDTO.setBuyerid(wishlist.getBuyerid());
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProdid(wishlist.getProdid());
    return wishlistDTO;
	}
	public Wishlist createEntity() {
		Wishlist wish = new Wishlist();
		wish.setBuyerid(this.getBuyerid());
		wish.setProdid(this.getProdid().prodid);
		return wish;
		
	}
}
