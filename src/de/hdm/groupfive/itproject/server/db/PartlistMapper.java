package de.hdm.groupfive.itproject.server.db;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

import java.util.Vector;

public class PartlistMapper {
	/**
	 * 
	 */
	private static PartlistMapper partlistMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static PartlistMapper getPartlistMapper() {
		if (partlistMapper == null) {
			partlistMapper = new PartlistMapper();
		}
		return partlistMapper;
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
	public Partlist findById(int id){
		return null;
	}
	
	/**
	 * 
	 */
	public Partlist update(Partlist partlist){
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
	public Vector<Partlist> getAllPartlists(){
		return null;
	}
	
}
