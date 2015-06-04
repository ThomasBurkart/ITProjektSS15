package de.hdm.groupfive.itproject.server.db;

import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class ModuleMapper {
	/**
	 * 
	 */
	private static ModuleMapper moduleMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static ModuleMapper getModuleMapper() {
		if (moduleMapper == null) {
			moduleMapper = new ModuleMapper();
		}

		return moduleMapper;
	}
	
	/**
	 * 
	 */
	public Partlist insert(Partlist partlist){
		return null;
	}
	
	/**
	 * 
	 */
	public void delete(Partlist partlist){
		
	}
	
	/**
	 * 
	 */
	public Module findById(int id){
		return null;
	}
	
	/**
	 * 
	 */
	public Module update(Partlist partlist){
		return null;
	}
	
	/**
	 * 
	 */
	public static PartlistMapper partlistMapper(){
		return null;
	}
	
	/**
	 * 
	 */
	public Partlist findByName(String name){
		return null;
	}
	
	/**
	 * 
	 */
	public Vector<Product> getAllProducts(Module module){
		return null;
	}
}
