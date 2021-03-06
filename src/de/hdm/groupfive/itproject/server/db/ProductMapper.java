package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class ProductMapper {
	
	/**
	 * Die Klasse ProductMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see productMapper()
	 */
	
	private static ProductMapper productMapper = null;
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProductMapper.getProductMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>ProductMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProductMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>ProductMapper</code>-Objekt. { @link productMapper}
	 */
	public static ProductMapper getProductMapper() {
		if (productMapper == null) {
			productMapper = new ProductMapper();
		}
		return productMapper;
	}

	/**
	 * Fügt ein neues Produkt der Datenbank hinzu
	 * 
	 * @param p
	 *            neues Produkt
	 * @return Produkt mit Produkt-Id aus Datenbank
	 */
	public Product insert(Product p) throws SQLException {
		//DB Verbindung holen
		Connection con = DBConnection.connection();

		try {
			ElementMapper em = ElementMapper.getElementMapper();
			p.setName(p.getSalesName());
			Product newP = (Product) em.insert(p);
			p.setId(newP.getId());

			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(product_id) AS maxid FROM product");

			while (rs.next()) {
				/*
				 * p erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				p.setProductId(rs.getInt("maxid") + 1);
				
				PreparedStatement stmt2;
				stmt2 = con
						.prepareStatement("INSERT INTO product "
								+ "(product_id, name, element_id) "
								+ "VALUES (?, ?, ?)");

				stmt2.setInt(1, p.getProductId());
				stmt2.setString(2, p.getName());
				stmt2.setInt(3, p.getId());
				stmt2.executeUpdate();
				stmt2.close();	

				// Historie speichern
				com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
						.getUserService();

				com.google.appengine.api.users.User user = userService
							.getCurrentUser();
				UserMapper.getUserMapper().insertHistory(user.getUserId(),
						user.getNickname(), p.getId(), "erstellt",
						p.getLastUpdate());
				p.setLastUser(user.getNickname());
				rs.close();
				stmt.close();
				return p;
			}
			rs.close();
			stmt.close();


		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}

	/**
	 * Löschen der Daten eines <code>Product</code> - Objekts aus der Datenbank
	 * 
	 * @param p
	 *            das aus der DB zu löschende "Objekt"
	 * @throws SQLException
	 */
	public void delete(Product p) throws SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM product WHERE product_id="
					+ p.getProductId());

			Statement stmt2 = con.createStatement();

			stmt2.executeUpdate("DELETE FROM ModuleRelationship WHERE superordinateID="
					+ p.getId());

			ElementMapper.getElementMapper().delete(p);
			

			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), p.getId(), "erstellt",
					p.getLastUpdate());
			

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 

		
	}
	
	/**
	 * Finden aller Produkte anhand des Namens
	 * @return Partlist-Objekt
	 */
	
	public Partlist findAll() throws SQLException {
		return ElementMapper.getElementMapper().findByName("%", 1000, false, true);
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param p
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Product update(Product p) throws SQLException {
		Connection con = DBConnection.connection();

		try {
			ElementMapper.getElementMapper().update(p);

			PreparedStatement stmt;
			stmt = con
					.prepareStatement("UPDATE product SET name=?, element_id=? WHERE product_id=?");

			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getId());
			stmt.setInt(3, p.getProductId());
			stmt.executeUpdate();
			stmt.close();	
			
			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), p.getId(), "erstellt",
					p.getLastUpdate());
			p.setLastUser(user.getNickname());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		
		return p;
	}
	
	/**
	 * Suchen eines Produktes anhand der ElementID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param elementId 
	 *            Primärschlüsselattribut (->DB)
	 * @return Produkt-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */

	public Product findByElement(int elementId) throws IllegalArgumentException,
			SQLException {
		
		Connection con = DBConnection.connection();

		Product p = new Product();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM product INNER JOIN element "
							+ "ON product.element_id = element.element_id "
							+ "WHERE product.element_id =" + elementId
							+ " ORDER BY element.element_id");

			if (rs.next()) {
				p.setProductId(rs.getInt("product.product_id"));
				p.setId(rs.getInt("element.element_id"));
				p.setPartlist(PartlistMapper.getPartlistMapper()
						.findByProductId(p.getId()));
				if (p.getPartlist() != null) {
					for (PartlistEntry pe : p.getPartlist().getAllEntries()) {
						pe.setSuperModule(p);
					}
				}
				p.setName(rs.getString("element.name"));
				p.setSalesName(rs.getString("product.name"));
				p.setDescription(rs.getString("description"));
				p.setMaterialDescription(rs.getString("material_description"));

				Timestamp timestamp = rs.getTimestamp("creation_date");
				if (timestamp != null) {
					Date creationDate = new java.util.Date(timestamp.getTime());
					p.setCreationDate(creationDate);
				}

				Timestamp timestamp2 = rs.getTimestamp("last_update");
				if (timestamp2 != null) {
					Date lastUpdateDate = new java.util.Date(
							timestamp2.getTime());
					p.setLastUpdate(lastUpdateDate);
				}
				p.setLastUser(UserMapper.getUserMapper().getLastUpdateUserNameByElementId(p.getId()));

				return p;
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}

}
