package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;
//** @ author Jakupi, Samire ; Thies


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
	
	public Element findByKey(int id) {
		//DB Verbindung hier holen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
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
	 * @return  Ein Vektor mit Element-Objekten, die sämtliche Elemente
     *          repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
     *          oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public Vector<Element> findAll() {
		Connection con = DBConnection.connection();
		
		Vector<Element> result = new Vector<Element>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery ("SELECT id, name, description, material_description, creation_date, last_update FROM element" 
					+ "ORDER BY id");
			// Für jeden Eintrag im Suchergebnis wird nun ein Element Objekt erstellt
			
			while (rs.next()) {
				Element e = new Element();
				e.setId (rs.getInt ("id"));
				e.setName (rs.getString ("name"));
				e.setDescription (rs.getString ("description"));
				e.setMaterialDescription (rs.getString ("material_description"));
				e.setCreationDate (rs.getDate ("creation_date"));
				e.setLastUpdate (rs.getDate ("last_update"));
				
				result.addElement(e);
			}
		} 
		    catch (SQLException e2) {
			e2.printStackTrace();
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
	}

	
}
//Nochmals anschauen


