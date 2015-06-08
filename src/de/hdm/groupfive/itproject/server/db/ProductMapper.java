package de.hdm.groupfive.itproject.server.db;

import java.sql.Date;
import java.util.Vector;

<<<<<<< HEAD
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
=======
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
>>>>>>> refs/heads/master
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

<<<<<<< HEAD
	/**
	 * 
	 * @param product
	 */
=======
	public Vector<Product> findAll() {
		// TODO ÜBER DATENBANK ABFRAGEN ... NUR TESTWEISE statisch EREZUGT:
		
		Vector<Product> result = new Vector<Product>();
		Product m1 = new Product();
		m1.setName("Produkt 1");
		m1.setDescription("Baugruppe 1 - Irgendeine Beschreibung");
		m1.setId(12);
		m1.setCreationDate(new Date(2015, 6, 7));
		m1.setMaterialDescription("Unterschiedlich");
		result.add(m1);

		Module e1 = new Module();
		e1.setName("Module 2");
		e1.setDescription("Baugruppe 2 - Noch Irgendeine Beschreibung");
		e1.setId(232);
		e1.setCreationDate(new Date(2015, 6, 3));
		e1.setMaterialDescription("Metall");
		m1.getPartlist().add(e1, 1);

		Element e2 = new Element();
		e2.setName("Element 1");
		e2.setDescription("Bauteil 1 - Beschreibung meines ersten Bauteils :D");
		e2.setId(132);
		e2.setCreationDate(new Date(2015, 6, 3));
		e2.setMaterialDescription("Eisen");
		e1.getPartlist().add(e2, 1);

		Element e3 = new Element();
		e3.setName("Element 2");
		e3.setDescription("Bauteil 2 - Beschreibung meines zweiten Bauteils ;D");
		e3.setId(132);
		e3.setCreationDate(new Date(2015, 6, 3));
		e3.setMaterialDescription("Holz");
		e1.getPartlist().add(e3, 1);

		Element e4 = new Element();
		e4.setName("Element 4");
		m1.getPartlist().add(e4, 1);
		// Create a model for the tree.

		Product module = new Product();
		module.setName("Produkt 3");
		result.add(module);

		Product e5 = new Product();
		e5.setName("Produkt 5");
		e5.setDescription("Produkt 5 - Beschreibung meines 5. Bauteils ;D");
		e5.setId(132);
		e5.setCreationDate(new Date(2015, 6, 3));
		e5.setMaterialDescription("Holz");
		result.add(e5);
		module.getPartlist().add(e1, 1);
		module.getPartlist().add(e2, 1);
		module.getPartlist().add(e3, 1);
		module.getPartlist().add(e4, 1);
		return result;
	}

>>>>>>> refs/heads/master
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
