package de.hdm.groupfive.itproject.server.db;

import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Product;

public class ProductMapper {
	/**
	 * 
	 */
	private static ProductMapper productMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static ProductMapper getProductMapper() {
		if (productMapper == null) {
			productMapper = new ProductMapper();
		}
		return productMapper;
	}
	
	public Product insert(Product p){
		return null;
	}

	public Vector<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Product product) {
		// TODO Auto-generated method stub
		
	}
}
