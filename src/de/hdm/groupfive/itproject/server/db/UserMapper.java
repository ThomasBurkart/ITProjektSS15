package de.hdm.groupfive.itproject.server.db;

import java.sql.ResultSet;

import com.google.cloud.sql.jdbc.Connection;
import com.google.cloud.sql.jdbc.Statement;
import com.hdm.stundenplantool2.server.db.Bei;

import de.hdm.groupfive.itproject.shared.bo.User;


/**
 * Mapper-Klasse, die <code>User</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 */
//Test 

public class UserMapper {
	/**
	 * 
	 */
	private static UserMapper userMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}
	
	/** Geschuetzter Konstruktor-verhindert die Moeglichkeit,
	 * mit <code>new</code> neue Instanzen dieser Klasse zu erzeugen
	 */
	
	protected UserMapper() {
		
	}
	
	/**
	 * Einfügen eines <code>User</code>-Objekts in die Datenbank. Dabei
	 * wird auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param u das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	 
	public User insert(User u) throws IllegalArgumentException{
		Connection con = (Connection) DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid"
								+ "FROM user");
			
			if(rs.next()) {
				u.setId(rs.getInt("maxid") + 1);
				
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO user (id)"
								+ "VALUES ("
								+ u.getId() + ")");
				
			} 
	        	}catch (SQLException ex) {
				throw new IllegalArgumentException(ex.getMessage());
			}
			return u;
			}
		
	
	/**
	 * Löschen der Daten eines <code>Users</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param u das aus der DB zu löschende "Objekt"
	 */
	
	public void delete(User u) throws IllegalArgumentException {
		Connection con = (Connection) DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM user"
							+ "WHERE id = " + u.getId());
		} catch (SQLException ex){
			throw new IllegalArgumentException(ex.getMessage());
		}
		}

	
	/**
	 * Suchen eines Users mit vorgegebener id. Da diese eindeutig ist,
	 * wird genau ein Objekt zurückgegeben.
	 * 
	 * @param id Primärschlüsselattribut (->DB)
	 * @return User-Objekt, das dem übergebenen Schlüssel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */
	
	public User findById(int id) throws IllegalArgumentException {
		//DB Verbindung hier holen
		Connection con = (Connection) DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			//Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT id"
								+ "FROM user"
								+ "WHERE id=" + id
								+ "ORDER BY id");
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				
				return u;
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;
	}
		
	
	/**
	 * Methode um einen User in der DB zu aktualisieren
	 * 
	 * @param	user - Objekt welches aktualisiert werden soll 			
	 * @return	User-Objekt
	 * @throws	Bei der Kommunikation mit der DB kann es zu Komplikationen kommen,
	 * 			die entstandene Exception wird an die aufrufende Methode weitergereicht
	 */
	 
	public User update(User u) throws IllegalArgumentException {
		Connection con = (Connection) DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE user SET"
							+ "WHERE id="
							+ u.getId());
		}
		catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return u;
	}
	
	/**
	 * 
	 */
	public User authentication(String email, String password){
		return null;
	}
}
