package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;

import com.google.protos.cloud.sql.Client.SqlException;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;
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
<<<<<<< HEAD
	
	public Element findByKey(int id) {
		//DB Verbindung hier holen
=======

	public Element findById(int id) throws SQLException {
		Element result = null;
		// DB Verbindung hier holen
>>>>>>> refs/heads/Thomas
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
<<<<<<< HEAD
			//Statement ausfuellen und als Query an die DB schicken
			
			ResultSet rs = stmt.executeQuery("SELECT id,name, description, material_description, creation_date, last_update FROM element" 
					+ "WHERE id =" + id);
			//ORDER BY nochmal anschauen und schauen ob es eingebaut werden muss
			
			if ( rs.next()){
				
				Element e = new Element ();
				e.setId (rs.getInt ("id"));
				e.setName (rs.getString ("name"));
				e.setDescription (rs.getString ("description"));
				e.setMaterialDescription(rs.getString ("material_description"));
				e.setCreationDate (rs.getDate ("creation_date"));
				e.setLastUpdate (rs.getDate ("last_update"));
				
				return e;
			
=======
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

>>>>>>> refs/heads/Thomas
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
<<<<<<< HEAD
	 * @return  Ein Vektor mit Element-Objekten, die sämtliche Elemente
     *          repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
     *          oder ggf. auch leerer Vetor zurückgeliefert.
=======
	 * 
	 * @return
>>>>>>> refs/heads/Thomas
	 */
	public Vector<Element> findAll() throws SQLException {
		Connection con = DBConnection.connection();

		Vector<Element> result = new Vector<Element>();

		try {
			Statement stmt = con.createStatement();
<<<<<<< HEAD
			
			ResultSet rs = stmt.executeQuery ("SELECT id, name, description, material_description, creation_date, last_update FROM element" 
=======

			ResultSet rs = stmt.executeQuery("SELECT * FROM element"
>>>>>>> refs/heads/Thomas
					+ "ORDER BY id");
			// Für jeden Eintrag im Suchergebnis wird nun ein Element Objekt
			// erstellt

			while (rs.next()) {
				Element e = new Element();
<<<<<<< HEAD
				e.setId (rs.getInt ("id"));
				e.setName (rs.getString ("name"));
				e.setDescription (rs.getString ("description"));
				e.setMaterialDescription (rs.getString ("material_description"));
				e.setCreationDate (rs.getDate ("creation_date"));
				e.setLastUpdate (rs.getDate ("last_update"));
				
=======
				e.setId(rs.getInt("id"));
				e.setName(rs.getString("name"));
				e.setDescription(rs.getString("description"));
				e.setMaterialDescription(rs.getString("material description"));
				e.setCreationDate(rs.getDate("int columnIndex, Calendar cal"));
				e.setLastUpdate(rs.getDate("int columnIndex, Calendar cal"));

>>>>>>> refs/heads/Thomas
				result.addElement(e);
			}
<<<<<<< HEAD
		} 
		    catch (SQLException e2) {
			e2.printStackTrace();
=======
		} catch (SQLException ex) {
			throw ex;
>>>>>>> refs/heads/Thomas
		}
<<<<<<< HEAD
		
=======

>>>>>>> refs/heads/Thomas
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
<<<<<<< HEAD
	public Element insert(Element e){
		Connection con = DBConnection.connection ();
		
		try {
			Statement stmt = con.createStatement ();
			
			/*
			 * Zunächst schauen wir nach, welches der momentan höhste 
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery ("SELECT MAX(id) AS maxid" + "FROM element");
			// Wenn wir etwas zurückhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * e erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				e.setId (rs.getInt ("maxid") + 1);
				stmt = con.createStatement ();
				
				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO element (id, name, description, material_description, creation_date, last_update)"
								  + "VALUES ("
						          + e.getId()
						          + ",'"
						          + e.getName()
						          + "','"
						          + e.getDescription()
						          + "','"
						          + e.getMaterialDescription()
						          + "',"
						          + e.getCreationDate()
						          + ","
						          + e.getLastUpdate() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace ();
		}
		/*
		 * Rückgabe, des evtl. korrigierten Elements.
		 * 
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, ware die Anpassung des Dozent-Objekts auch
		 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar.
		 * Die explizite Rückgabe von d ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verändert hat.
		 */
			return e;
	}
	
		/** Wiederholtes Schreiben eines Objektes in die Datenbank
		 * 
		 * @param e  das Objekt, das in die Datenbank geschrieben werden soll
		 * @return das als Parameter übergebene Objekt
		 */
	
		public Element update(Element e) {
			Connection con = DBConnection.connection ();
			
			try {
				Statement stmt = con.createStatement ();
				
				stmt.executeUpdate ("UPDATE elements SET" + "name= '"
								   + e.getName() + "'," + "description= '"
								   + e.getDescription() + "'," + "material_description= '"
								   + e.getMaterialDescription() + "'," + "creation_date= '"
								   + e.getCreationDate() + "'," + "last_update= '"
								   + e.getLastUpdate() + "'," + "WHERE id="
								   + e.getId());
								   
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			// Um die Analogie zu insert(Element e) zu wahren, geben wir e zurück
			return e;
		}
		
		/** Löschen der Daten eines <code>Element</code> - Objekts aus der Datenbank
		 * 
		 * @param e das aus der DB zu löschende "Objekt"
		 */
		
		public void delete(Element e) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM element" + "WHERE id=" + e.getId());
				}

			catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		/**  
		 * Löschen sämtlicher (Bauteile) Elements (<code>Element</code>-Objekte) einer Baugruppe.
         * Diese Methode sollte aufgerufen werden, bevor ein <code>Baugruppe</code>
         * -Objekt gelöscht wird.
         * 
         * @param b das <code>Baugruppe</code>-Objekt, zu dem die Bauteile gehören
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
=======
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
>>>>>>> refs/heads/Thomas
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

