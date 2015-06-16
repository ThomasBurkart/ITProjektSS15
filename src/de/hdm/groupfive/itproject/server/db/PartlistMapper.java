package de.hdm.groupfive.itproject.server.db;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

import java.sql.SQLException;
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
	public Partlist insert(Partlist partlist) throws SQLException {
		return null;
	}
	
	/**
	 * 
	 */
	public void delete(Partlist partlist) throws SQLException {
		
	}
	
	/**
	 * 
	 */
	public Partlist findById(int id) throws SQLException {
		return null;
	}
	
	/**
	 * 
	 */
	public Partlist update(Partlist partlist) throws SQLException {
		return null;
	}
	
	/**
	 * 
	 */
	public Partlist findByName(String name) throws SQLException {
		return null;
	}
	
	/**
	 * 
	 */
	public Vector<Partlist> getAllPartlists() throws SQLException {
		return null;
	}
	
}
