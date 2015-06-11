package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;


public class ElementMapper {
	
	private static ElementMapper elementMapper = null;
	
	protected ElementMapper () {
		
	}

	public static ElementMapper getElementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}
	
	public Element findById(int id) {
		//DB Verbindung hier holen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			//Statement ausfuellen und als Query an die DB schicken
			
			ResultSet rs = stmt.executeQuery("SELECT id,name, description, material description, creation date, last update FROM element" 
					+ "WHERE id =" + id + "ORDER BY element");
			if ( rs.next()){
				
				Element e = new Element ();
				e.setId (rs.getInt ("id"));
				e.setName (rs.getString ("name"));
				e.setDescription (rs.getString ("description"));
				e.setMaterialDescription(rs.getString ("material description"));
				e.setCreationDate (rs.getDate ("int columnIndex, Calendar cal"));
				e.setLastUpdate (rs.getDate ("int columnIndex, Calendar cal"));
				
				return e;
			
			}
			
				
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return null;
		}
		
		return null;
	
	}
	
	/**
	 * Auslesen aller Elements
	 * @return
	 */
	public Vector<Element> findAll() {
		Connection con = DBConnection.connection();
		
		Vector<Element> result = new Vector<Element>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery ("SELECT id, name, description, material description, creation date, last update FROM element" 
					+ "ORDER BY id");
			// Für jeden Eintrag im Suchergebnis wird nun ein Element Objekt erstellt
			
			while (rs.next()) {
				Element e = new Element();
				e.setId (rs.getInt ("id"));
				e.setName (rs.getString ("name"));
				e.setDescription (rs.getString ("description"));
				e.setMaterialDescription (rs.getString ("material description"));
				e.setCreationDate (rs.getDate ("int columnIndex, Calendar cal"));
				e.setLastUpdate (rs.getDate ("int columnIndex, Calendar cal"));
				
				result.addElement(e);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Element insert(Element e){
		return null;
	}

	/**
	 * 
	 * @param product
	 */
	public void delete(Element e) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 
	 */
	public Element update(Element e){
		return null;
	}
	
	/**
	 * 
	 */
	public static ElementMapper elementMapper(){
		return null;
	}
	
	/**
	 * 
	 */
	public Element findByName(String name){
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Element> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
//Nochmals anschauen


