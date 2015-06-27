package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;

import de.hdm.groupfive.itproject.client.ClientsideSettings;
import de.hdm.groupfive.itproject.server.ServerSettings;
import de.hdm.groupfive.itproject.shared.bo.Element;
//** @ author Jakupi, Samire ; Thies

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

	  /**
	   * Suchen eines Elements mit vorgegebener id. Da diese eindeutig ist,
	   * wird genau ein Objekt zurueckgegeben.
	   * 
	   * @param id Primärschlüsselattribut (->DB)
	   * @return Element-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   * @throws Bei der Kommunikation mit der DB kann es zu Komplikationen kommen,
	   *  		 die entstandene Exception wird an die aufrufende Methode weitergereicht
	   */

	public Element findById(int id) throws IllegalArgumentException, SQLException {
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
				e.setMaterialDescription(rs.getString("material_description"));
				e.setCreationDate(rs.getDate("creation_date"));
				e.setLastUpdate(rs.getDate("last_update"));

				return e;

			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
			}
		return null;
	}
		/**

		// Verbindung sollte immer wieder geschlossen werden.
		try {
			con.close();
		} catch (SQLException ex) {
			throw ex;
		}
		return null;
		*/


	

	/**
	 * Auslesen aller Elements
	 * 
	 * @return Ein Vektor mit Element-Objekten, die sämtliche Elemente
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public Vector<Element> findAll() throws IllegalArgumentException, SQLException {
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
				e.setMaterialDescription(rs.getString("material_description"));
				e.setCreationDate(rs.getDate("creation_date"));
				e.setLastUpdate(rs.getDate("last_update"));

				result.addElement(e);
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return result;
	}
	
	public Vector<Element>findByName(String name) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Element> result = new Vector<Element>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery ("SELECT * FROM element"
							+ "WHERE name=" + name + "ORDER BY name");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Element-Objekt erstellt.
		while (rs.next()) {
			Element e = new Element();
			e.setId(rs.getInt ("id"));
			e.setName(rs.getString ("name"));
			e.setDescription(rs.getString("description"));
			e.setMaterialDescription(rs.getString("material_description"));
			e.setCreationDate(rs.getDate ("creation_date"));
			e.setLastUpdate(rs.getDate ("last_update"));
			
			// Hinzufuegen des neuen Objekts zum Ergebnisvektor
			result.addElement(e);
		}
	}
		catch (SQLException ex){
			throw new IllegalArgumentException(ex.getMessage()); 
			
		}
		return result;
	}
	
	

	/**
	 * 
	 * Einfügen eines <code>Element</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param e das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Element insert(Element e) throws IllegalArgumentException, SQLException {
		if (e == null) {
			throw new IllegalArgumentException("Übergebenes Objekt an insert() ist NULL.");
		}
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(element_id) AS maxid "
					+ "FROM element");
			// Wenn wir etwas zurückhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * e erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				int newId = rs.getInt("maxid") + 1;
				e.setId(newId);
				
				stmt = con.createStatement();
				String sqlQuery = "INSERT INTO element " 
						+ "(element_id, name, description, material_description, creation_date, last_update) "
						+ "VALUES ("
						+ e.getId()
						+ ",'"
						+ e.getName()
						+ "','"
						+ e.getDescription()
						+ "','"
						+ e.getMaterialDescription()
						+ "','"
						+ getSqlDateFormat(e.getCreationDate())
						+ "','" 
						+ getSqlDateFormat(e.getLastUpdate())
						+ "')";
				
				
				
				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(sqlQuery);
				
			}
			
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		  return e;
	}
		/*
		 * Rückgabe, des evtl. korrigierten Elements.
		 * 
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, ware die Anpassung des Element-Objekts auch
		 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
		 * explizite Rückgabe von e ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verändert hat.
		 */
		
	/**
	 * Wiederholtes Schreiben eines Objektes in die Datenbank
	 * 
	 * @param e das Objekt, das in die Datenbank geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */

	public Element update(Element e) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE elements SET " + "name= '" + e.getName()
					+ "'," + "description= '" + e.getDescription() + "',"
					+ "material_description= '" + e.getMaterialDescription()
					+ "'," + "creation_date= '" + getSqlDateFormat(e.getCreationDate()) + "',"
					+ "last_update= '" + getSqlDateFormat(e.getLastUpdate()) + "'," + " WHERE id="
					+ e.getId());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage()); 
		}

		// Um die Analogie zu insert(Element e) zu wahren, geben wir e zurück
		return e;
	}

	/**
	 * Löschen der Daten eines <code>Element</code> - Objekts aus der Datenbank
	 * 
	 * @param e das aus der DB zu löschende "Objekt"
	 * @throws SQLException
	 */

	public void delete(Element e) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM element" + "WHERE id=" + e.getId());
		}

		catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage()); 
		}
	}

	/**
	 * Wandelt aus einem Date Objekt einen String in passendem SQL Übergabe Format.
	 * @param date Date das konvertiert werden soll
	 * @return String mit Date im Format yyyy-MM-dd HH:mm:ss
	 */
	private String getSqlDateFormat(Date date) {
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = dateFormat.format(date);
		return result;
	}
	 
}


