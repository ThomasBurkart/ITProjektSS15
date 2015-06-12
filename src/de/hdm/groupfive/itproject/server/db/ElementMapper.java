package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;

import com.google.protos.cloud.sql.Client.SqlException;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public class ElementMapper {

	private static ElementMapper elementMapper = null;

	protected ElementMapper() {

	}

	public static ElementMapper getElementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}

	public Element findById(int id) throws SQLException {
		Element result = null;
		// DB Verbindung hier holen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			// Statement ausfuellen und als Query an die DB schicken

			ResultSet rs = stmt.executeQuery("SELECT * FROM element"
					+ "WHERE id =" + id + " ORDER BY element");
			if (rs.next()) {

				Element e = new Element();
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDescription(rs.getString("description"));
				e.setMaterialDescription(rs.getString("material description"));
				e.setCreationDate(rs.getDate("int columnIndex, Calendar cal"));
				e.setLastUpdate(rs.getDate("int columnIndex, Calendar cal"));

				result = e;

			}
		} catch (SQLException ex) {
			throw ex;
		}

		// Verbindung sollte immer wieder geschlossen werden.
		try {
			con.close();
		} catch (SQLException ex) {
			throw ex;
		}
		return result;

	}

	/**
	 * Auslesen aller Elements
	 * 
	 * @return
	 */
	public Vector<Element> findAll() throws SQLException {
		Connection con = DBConnection.connection();

		Vector<Element> result = new Vector<Element>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM element"
					+ "ORDER BY id");
			// Für jeden Eintrag im Suchergebnis wird nun ein Element Objekt
			// erstellt

			while (rs.next()) {
				Element e = new Element();
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDescription(rs.getString("description"));
				e.setMaterialDescription(rs.getString("material description"));
				e.setCreationDate(rs.getDate("int columnIndex, Calendar cal"));
				e.setLastUpdate(rs.getDate("int columnIndex, Calendar cal"));

				result.addElement(e);
			}
		} catch (SQLException ex) {
			throw ex;
		}

		return result;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public Element insert(Element e) throws SQLException {
		return null;
	}

	/**
	 * Löscht ein Element aus der Datenbank
	 * @param Element e
	 */
	public void delete(Element e) throws SQLException {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM element " + "WHERE id=" + e.getId());
		} catch (SQLException ex) {
			throw ex;
		}
	}

	/**
	 * 
	 */
	public Element update(Element e) throws SQLException {
		Connection con = DBConnection.connection();
		// TODO: evtl. weitere Spalten namen + Werte einfügen / anpassen
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE element SET name=\"" + e.getName()
					+ "\", description=\"" + e.getDescription()
					+ "\", materialdescriptio=\"" + e.getMaterialDescription()
					+ "\" WHERE id=" + e.getId());
		} catch (SQLException ex) {
			throw ex;
		}

		return e;
	}

	/**
	 * 
	 */
	public Element findByName(String name) throws SQLException {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		Element result = null;
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM element "
					+ "WHERE name=" + name + " ORDER BY id");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Element e = new Element();
				e.setId(rs.getInt("id"));
				// TODO befüllen
				result = e;
			}
		} catch (SQLException ex) {
			throw ex;
		}

		return result;
	}

}
// Nochmals anschauen

