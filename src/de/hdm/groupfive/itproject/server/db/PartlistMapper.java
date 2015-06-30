package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Vector;
import com.ibm.icu.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.client.ClientsideSettings;
import de.hdm.groupfive.itproject.server.ServerSettings;


public class PartlistMapper {
	/** Die Klasse PartlistMapper wird nur einmal instantiiert. 
	 * Man spricht hier von einem sogenannten <b>Singleton</b>. 
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für sämtliche
	 * eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz dieser Klasse
	 * 
	 * @see partlistMapper()
	 */
	
	private static PartlistMapper partlistMapper = null;
	
	/** Geschützer Konstruktor - verhindert die Möglichkeit,
	 * mit <code>new</code>  neue Instanzen dieser Klasse zu erzeugen
	 */
	protected PartlistMapper () {
	}
	/** Diese statische Methode kann aufgerufen werden durch
	 * <code>PartlistMapper.partlistMapper()</code>. Sie stellt die Singleton-Eigenschaften sicher,
	 * indem sie dafür sorgt, das nur eine einzige Instanz von <code>PartlistMapper</code> existiert.
	 * @return DAS <code>PartlistMapper</code>-Objekt
	 */
	public static PartlistMapper getPartlistMapper() {
		if (partlistMapper == null) {
			partlistMapper = new PartlistMapper();
		}
		return partlistMapper;
	}
	
	/** Einfügen eines <code>Partlist</code> - Objekts in die DB.
	 * Dabei wird auch der Primärschlüssel des übergebenen Objekts geprüft und ggf. berichtigt.
	 * 
	 * @param p des zu speichernden Objektes 
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter 
	 * <code>id</code>
	 */
	public Partlist insert(Partlist p) throws IllegalArgumentException, SQLException {
		if ( p == null) {
			throw new IllegalArgumentException ("Übergebenes Objekt an insert() ist NULL. ");
		}
		
		Connection con = DBConnection.connection();
		
		try{
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(partlist_id) AS maxid "
							+ "FROM partlist");
			//Wenn wir etwas zurückhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/* p erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel
				 */
				int newId = rs.getInt("maxid") + 1;
				p.setId(newId);
				
				stmt = con.createStatement();
				String sqlQuery = "INSERT INTO partlist " 
							+ "(partlist_id, name, creation_date) "
							+ "VALUES ("
							+ p.getId()
							+ ",'"
							+ p.getName()
							+ "','"
							+ getSqlDateFormat(p.getCreationDate())
							+ "')";
				
				// die tatsächliche Einfügeoperation
				stmt.executeUpdate(sqlQuery);
			}
		}catch (SQLException ex){
				throw new IllegalArgumentException(ex.getMessage());
			}
			return p;
			}
	
	/** Wiederholtes Schreiben eines Objektes in die DB
	 * 
	 * @param p das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Partlist update(Partlist p) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE partlists SET " + "name= '" + p.getName()
							+ "'," + "creation_date= '" + getSqlDateFormat(p.getCreationDate()) + "',"
							+ " WHERE id="
							+ p.getId());
			
		}
		catch (SQLException ex){
			throw new IllegalArgumentException(ex.getMessage());
		}
		return p;
	}
		
				 
	
	/** Löschen der Daten eines <code>Partlist</code> Objektes aus der Datenbank
	 * @param p das aus der DB zu löschende "Objekt"
	 * @throws SQLException
	 */
	
	public void delete(Partlist p) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM partlist" + "WHERE id =" + p.getId());
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
		
	
	/** Suche einer Partlist mit vorgegebener id. Da diese eindeutig ist, wird genau ein Objekt zurückgegeben
	 * @param id Primärschlüsselattribut (-> DB)
	 * @return Partlist-Objekt, das dem übergebenen Schlüssel entspricht, null bei nicht vorhandener DB Tupel
	 * @throws Bei der Kommunikation mit der DB kann es zu Komplikationen kommen,
	 *         die entstandene Exception wird an die aufrufene Methode weitergereicht.
	 */
	public Partlist findPartlistById(int id) throws IllegalArgumentException, SQLException {
		//DB Verbindung hier holen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			//Statement ausfüllen und als Query an die DB schicken
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM partlist "
						 + "WHERE id= " + id 
						 + "ORDER BY partlist");
			if (rs.next()) {
				Partlist p = new Partlist();
				
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCreationDate(rs.getDate("creation_date"));
				
				return p;
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}
	
	
	/**
	 * Auslesen aller Partlist-Objekte mit gegebenen Namen
	 * @param name Name der Partlist, die ausgegeben werden sollen
	 * @return Ein Vetor mit Partlist-Objekten, welches die Partlist mit dem gesuchten Namen repräsentiert
	 */
	
	public Vector <Partlist> findByName(String name) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Partlist> result = new Vector<Partlist>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM partlist " 
								+ "WHERE name= " + name 
								+ "ORDER BY name");
			// Für jeden Eintrag im Suchergebnis wird nun ein Partlist-Objekt erzeugt
			
			while (rs.next()) {
				Partlist p = new Partlist();
				
				p.setId (rs.getInt("id"));
				p.setName (rs.getString("name"));
				p.setCreationDate (rs.getDate("creation_date"));
				
				
				result.add(p);
			}
		}       catch (SQLException ex) {
				throw new IllegalArgumentException(ex.getMessage());
			}
			return result;
		}
			
	
	/** Auslesen aller Partlists
	 * 
	 * @return Ein Vektor mit Partlist-Objekten, die sämtliche Partlists repräsentieren.
	 * 		   Bei evtl. Exceptions wird ein partiell gefüllter oder ggf. auch leerer Vector zurückgeliefert
	 */
	
	public Vector<Partlist> findAll() throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Partlist> result = new Vector<Partlist>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs   = stmt.executeQuery("SELECT * FROM partlist " 
						   + "ORDER BY id ");
			// Für jeden Eintrag im Suchergebnis wird nun ein Partlist Objekt erstellt
			while (rs.next()) {
				
				Partlist p = new Partlist();
				
				p.setId (rs.getInt("id"));
				p.setName (rs.getString("name"));
				p.setCreationDate (rs.getDate("creation_date"));
				
				result.add(p);
			}
		}       catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return result;
	}
    /** Wandelt aus einem Date Objekt einen String in passendem SQL Übergabe Format.
     * 
     * @param date Date das konvertiert werden soll
     * @return String mit Date im Format yyyy-MM-dd HH:mm:ss
     */
	private String getSqlDateFormat(Date date) {
		String result = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		result = dateFormat.format(date);
		return result;
	}
}

			
