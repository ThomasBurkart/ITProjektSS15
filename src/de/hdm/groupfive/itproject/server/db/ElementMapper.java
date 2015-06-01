package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

/** 
 * Mapper-Klasse die <code>Element</code> Objekte auf eine relationale 
 * Datenbank abbildet.
 * @author Samson
 *
 */


public class ElementMapper {
	/**
	 * Die Klasse ElementMapper wird nur einmal instantiiert. Man spricht von einem sogenannten 
	 * <b>Singleton</b> Ist durch ihren Bezeichner <code>static</code> für sämtliche eventuellen Instanzen 
	 * dieser Klasse vorhanden. Speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ElementMapper elementMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit mit dem <code>new</code> 
	 * neue Instanzen dieser Klasse zu erzeugen
	 * 
	 * @return ElementMapper 
	 */
	protected ElementMapper () {
		
	}
	/**
	 * Diese statische Methode kann durch <code>ElementMapper.elementMapper()</code> aufgerufen werden
	 * Stellt die Singleton Eigenschaften sicher, indem sie dafür sorgt, das nur eine einzige Instanz von 
	 * <code>ElementMapper</code> existiert.
	 * @return <code>ElementMapper</code> Objekt
	 * @see elementMapper
	 */
	public static ElementMapper getElementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}
	
	public Element findById(int id) {
		//DB Verbindung hier holen
		Connection con = DBConnection.connection();
		
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Statement ausf�llen und als Query an die DB schicken
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT id, name, description, modify_date, creation_date FROM elements"
					+ "WHERE id=" + id + "ORDER BY name, description, creation_date, modify_date");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
try {
	if (rs.next()) {
			Element a = new Element();
			a.setId(rs.getInt("id"));
			a.setName(rs.getString("name"));
			a.setDescription(rs.getString("description"));
			a.setLastUpdate(rs.getDate ("last_update"));
			a.setCreationDate(rs.getDate ("creation_date"));
			return a;
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Element> findByCreator(User creator) {
		// TODO Auto-generated method stub
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


