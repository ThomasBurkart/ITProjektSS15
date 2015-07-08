package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.groupfive.itproject.shared.bo.Element;

/**
 * Mapper-Klasse, die <code>User</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und gelöscht werden
 * können. Das Mapping ist bidirektional. D.h., Objekte können in DB-Strukturen
 * und DB-Strukturen in Objekte umgewandelt werden.
 */

public class UserMapper {
	/**
	 * 
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see userMapper()
	 */
	private static UserMapper userMapper = null;

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>UserMapper.getUserMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>UserMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> UserMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>UserMapper</code>-Objekt. { @link userMapper}
	 */
	public static UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	/**
	 * Geschuetzter Konstruktor-verhindert die Moeglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen
	 */
	protected UserMapper() {

	}

	/**
	 * Einfügen eines <code>User</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param userId
	 *           zu �bergebende userId          
	 * @param username
	 * 			Name des Benutzers 
	 * @param elementId
	 * 			Id des Elements 
	 * @param updateText 
	 * 			Text als String f�r das Update
	 * @param lastUpdate
	 *        	letztes Update-Datum
	 */
	public void insertHistory(String userId, String username, int elementId,
			String updateText, Date lastUpdate)
			throws IllegalArgumentException, SQLException {
		//DB Verbindung holen
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/*
			 * Zunaechst wird festgestellt, welches der h�chste Prim�rschluessel ist
			 * 
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(history_id) AS maxid FROM history");

			if (rs.next()) {
				int newId = rs.getInt("maxid") + 1;
				
				PreparedStatement stmt2;
				stmt2 = con
						.prepareStatement("INSERT INTO history "
								+ "(history_id,user_id,username,element_id,updatetext,last_update) "
								+ "VALUES (?, ?, ?, ?, ?, ?)");

				stmt2.setInt(1, newId);
				stmt2.setString(2, userId);
				stmt2.setString(3, username);
				stmt2.setInt(4, elementId);
				stmt2.setString(5, updateText);
				stmt2.setString(6, getSqlDateFormat(lastUpdate));
				stmt2.executeUpdate();
				stmt2.close();			

				rs.close();
				stmt.close();

			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * Der Name des Benutzers, welcher das letzte mal dieses Element bearbeitet hat, wird als String ausgegeben
	 * @param id
	 * 		Id des Elements
	 * @return
	 * 			<code>""</code> Name des Benutzers
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	public String getLastUpdateUserNameByElementId(int id) 
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT username FROM history WHERE element_id="+id+" ORDER BY history_id DESC LIMIT 1");
			
			
			if (rs.next()) {
				return rs.getString("username");
				
			}
			return "";
		
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * Wandelt aus einem Date Objekt einen String in passendem SQL Übergabe
	 * Format.
	 * 
	 * @param date
	 *            Date das konvertiert werden soll
	 * @return String mit Date im Format yyyy-MM-dd HH:mm:ss
	 */
	private String getSqlDateFormat(Date date) {
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = dateFormat.format(date);
		return result;
	}

}
