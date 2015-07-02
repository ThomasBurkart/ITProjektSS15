package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * Mapper-Klasse, die <code>User</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und gelöscht werden
 * können. Das Mapping ist bidirektional. D.h., Objekte können in DB-Strukturen
 * und DB-Strukturen in Objekte umgewandelt werden.
 */
// Test

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
	 * @param u
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public void insertHistory(String userId, String username, int elementId,
			String updateText, Date lastUpdate)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(history_id) AS maxid FROM history");

			if (rs.next()) {
				int newId = rs.getInt("maxid") + 1;

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO history (history_id,user_id,username,element_id,updatetext,last_update) "
						+ "VALUES ("
						+ newId
						+ ", '"
						+ userId
						+ "','"
						+ username
						+ "', "
						+ elementId
						+ ",'"
						+ updateText
						+ "', '" + getSqlDateFormat(lastUpdate) + "')");

			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	public ArrayList<String[]> getLastUpdatesForHistory() 
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		ArrayList<String[]> result = new ArrayList<String[]>();
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM history ORDER BY history_id DESC LIMIT 12");
			
			String[] resultSet = new String[4];
			while (rs.next()) {
				resultSet[0] = rs.getString("username");
				resultSet[1] = rs.getString("updatetext");
				Timestamp timestamp = rs
						.getTimestamp("last_update");
				if (timestamp != null) {
					Date lastUpdate = new java.util.Date(
							timestamp.getTime());
					DateTimeFormat dateFormat = DateTimeFormat
							.getFormat("dd.MM.yyyy HH:mm:ss");
					resultSet[2] = dateFormat.format(lastUpdate);
				}
				Element e = ElementMapper.getElementMapper().findElementById(rs.getInt("element_id"));
				if (e != null) {
					resultSet[3] = e.getName();
				} else {
					resultSet[3] = "Id "+rs.getInt("element_id")+" gelöscht";
				}
				result.add(resultSet);
			}
			return result;
		
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
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
