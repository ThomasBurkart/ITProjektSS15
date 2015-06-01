package de.hdm.groupfive.itproject.server.db;

import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
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
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Product insert(Product p){
		return null;
	}

	/**
	 * 
	 * @param product
	 */
	public void delete(Product product) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	public Product findById(int id){
		return null;
	}
	
	/**
	 * 
	 */
	public Product update(Product product){
		return null;
	}
	
	/**
	 * 
	 */
	public static ProductMapper productMapper(){
		return null;
	}
	
	/**
	 * 
	 */
	public Product findByName(String name){
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

}
