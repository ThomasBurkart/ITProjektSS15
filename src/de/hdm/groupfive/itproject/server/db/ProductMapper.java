package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import de.hdm.groupfive.itproject.shared.bo.*;

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
	 * Fügt ein neues Produkt der Datenbank hinzu
	 * 
	 * @param p
	 *            neues Produkt
	 * @return Produkt mit Produkt-Id aus Datenbank
	 */
	public Product insert(Product p) throws SQLException {
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
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				p.setProductId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO product (product_id, name, element_id) VALUES ("
						+ p.getProductId()
						+ ",'"
						+ p.getSalesName()
						+ "',"
						+ p.getId() + ")");

				return p;
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param product
	 */
	public void delete(Product p) throws SQLException {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			// TODO: alle zuordnungen zum Modul löschen
			// alle Produkte von Modul löschen.

			stmt.executeUpdate("DELETE FROM product WHERE product_id="
					+ p.getProductId());

			Statement stmt2 = con.createStatement();

			// TODO: alle zuordnungen zum Modul löschen
			// alle Produkte von Modul löschen.

			stmt2.executeUpdate("DELETE FROM ModuleRelationship WHERE superordinateID="
					+ p.getId());

			ElementMapper.getElementMapper().delete(p);
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		
	}

	/**
	 * 
	 */
	public Product findById(int id) throws SQLException {
		return null;
	}

	
	public Partlist findAll() throws SQLException {
		return ElementMapper.getElementMapper().findByName("%", 1000, false, true);
	}

	/**
	 * 
	 */
	public Product update(Product p) throws SQLException {
		Connection con = DBConnection.connection();

		try {
			ElementMapper.getElementMapper().update(p);

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE product SET name = '" + p.getSalesName()
					+ "'," + "element_id=" + p.getId() + " WHERE product_id ="
					+ p.getProductId());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		
		return p;
	}

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

			while (rs.next()) {
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
				return p;
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}

	/**
	 * 
	 */
	public Product findByName(String name) throws SQLException {
		return null;
	}

}
