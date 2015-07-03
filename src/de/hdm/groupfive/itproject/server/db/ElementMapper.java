package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

//** @ author Jakupi, Samire ; Thies

public class ElementMapper {

	private static ElementMapper elementMapper = null;
	
	private static Partlist cachePartlist = new Partlist();

	protected ElementMapper() {
	}

	public static ElementMapper getElementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}
		return elementMapper;
	}

	public Element findElementById(int id) throws IllegalArgumentException,
			SQLException {
		return this.findById(id, false, false).getElementById(id);
	}

	/**
	 * Suchen eines Elements mit vorgegebener id. Da diese eindeutig ist, wird
	 * genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut (->DB)
	 * @return Element-Objekt, das dem übergebenen Schlüssel entspricht, null
	 *         bei nicht vorhandenem DB-Tupel.
	 * @throws Bei
	 *             der Kommunikation mit der DB kann es zu Komplikationen
	 *             kommen, die entstandene Exception wird an die aufrufende
	 *             Methode weitergereicht
	 */

	public Partlist findById(int id, boolean onlyModules, boolean onlyProducts)
			throws IllegalArgumentException, SQLException {

		Partlist result = new Partlist();
		
		Element elementFromCache = cachePartlist.getElementById(id);
		if (elementFromCache != null) {
			result.add(elementFromCache, 1);
			return result;
		}
		
		// DB Verbindung hier holen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken

			ResultSet rs = stmt.executeQuery("SELECT * FROM element "
					+ "WHERE element_id=" + id + " ORDER BY element_id");
			while (rs.next()) {
				
				Product p = ProductMapper.getProductMapper().findByElement(
						rs.getInt("element_id"));
				
				if (p != null) {
					result.add(p, 1);
					cachePartlist.add(p, 1);
				} else if (!onlyProducts) {

					// Zuerst nachschauen ob es sich bei dem Element um ein
					// Modul handelt.
					Module m = ModuleMapper.getModuleMapper().findByElement(
							rs.getInt("element_id"));

					// Wenn es sich um ein Modul handelt, dieses hinzufügen
					// ansonsten, das Element als
					// Bauteil hinzufügen.
					if (m != null) {
						result.add(m, 1);
						cachePartlist.add(m, 1);
					} else {
						if (!onlyModules && !onlyProducts) {

							Element e = new Element();
							e.setId(rs.getInt("element_id"));

							e.setName(rs.getString("name"));
							e.setDescription(rs.getString("description"));
							e.setMaterialDescription(rs
									.getString("material_description"));

							Timestamp timestamp = rs
									.getTimestamp("creation_date");
							if (timestamp != null) {
								Date creationDate = new java.util.Date(
										timestamp.getTime());
								e.setCreationDate(creationDate);
							}

							Timestamp timestamp2 = rs
									.getTimestamp("last_update");
							if (timestamp2 != null) {
								Date lastUpdateDate = new java.util.Date(
										timestamp2.getTime());
								e.setLastUpdate(lastUpdateDate);
							}
							e.setLastUser(UserMapper.getUserMapper().getLastUpdateUserNameByElementId(e.getId()));

							// Hinzufuegen des neuen Objekts zum
							// Ergebnisvektor
							result.add(e, 1);
							cachePartlist.add(e, 1);
						}
					}
				}
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return result;
	}

	public Partlist findByName(String searchWord, int maxResults)
			throws IllegalArgumentException, SQLException {
		return findByName(searchWord, maxResults, false);
	}

	public Partlist findByName(String searchWord, int maxResults,
			boolean onlyModules) throws IllegalArgumentException, SQLException {
		return findByName(searchWord, maxResults, onlyModules, false);
	}

	public Partlist findByName(String searchWord, int maxResults,
			boolean onlyModules, boolean onlyProducts)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		Partlist result = new Partlist();
		String whereQuery = "";
		if (!searchWord.isEmpty()) {
			String[] words = searchWord.split(" ");
			for (String word : words) {
				if (word.length() > 3) {
					Vector<String> fuzzySearchWords = getLevenshtein1(word);
					for (String fuzzyWord : fuzzySearchWords) {
						whereQuery += "name LIKE '%" + fuzzyWord + "%' OR ";
						whereQuery += "description LIKE '%" + fuzzyWord
								+ "%' OR ";
						whereQuery += "material_description LIKE '%"
								+ fuzzyWord + "%' OR ";
					}
				} else {
					// Fuzzy Suche nur bei Wörtern die mehr als 3 Buchstaben
					// haben
					whereQuery += "name LIKE '%" + word + "%' OR ";
					whereQuery += "description LIKE '%" + word + "%' OR ";
					whereQuery += "material_description LIKE '%" + word
							+ "%' OR ";
				}
			}
			if (whereQuery.length() > 5) {
				// Letztes OR aus Query entfernen
				whereQuery = whereQuery.substring(0, whereQuery.length() - 4);
			}
			try {
				Statement stmt = con.createStatement();
				String sqlQuery = "SELECT * FROM element WHERE " + whereQuery
						+ " ORDER BY name LIMIT " + maxResults;

				ResultSet rs = stmt.executeQuery(sqlQuery);

				// Für jeden Eintrag im Suchergebnis wird nun ein Element-Objekt
				// erstellt.
				while (rs.next()) {
					int elementId = rs.getInt("element_id");
					Element elementFromCache = cachePartlist.getElementById(elementId);
					if (elementFromCache != null && !onlyProducts && !onlyModules) {
						result.add(elementFromCache, 1);
						continue;						
					}
					
					
					Product p = ProductMapper.getProductMapper().findByElement(
							elementId);

					if (p != null) {
						result.add(p, 1);
					} else if (!onlyProducts) {

						// Zuerst nachschauen ob es sich bei dem Element um ein
						// Modul handelt.
						Module m = ModuleMapper.getModuleMapper()
								.findByElement(elementId);

						// Wenn es sich um ein Modul handelt, dieses hinzufügen
						// ansonsten, das Element als
						// Bauteil hinzufügen.
						if (m != null) {
							result.add(m, 1);
						} else {
							if (!onlyModules && !onlyProducts) {

								Element e = new Element();
								e.setId(elementId);

								e.setName(rs.getString("name"));
								e.setDescription(rs.getString("description"));
								e.setMaterialDescription(rs
										.getString("material_description"));

								Timestamp timestamp = rs
										.getTimestamp("creation_date");
								if (timestamp != null) {
									Date creationDate = new java.util.Date(
											timestamp.getTime());
									e.setCreationDate(creationDate);
								}

								Timestamp timestamp2 = rs
										.getTimestamp("last_update");
								if (timestamp2 != null) {
									Date lastUpdateDate = new java.util.Date(
											timestamp2.getTime());
									e.setLastUpdate(lastUpdateDate);
								}
								
								e.setLastUser(UserMapper.getUserMapper().getLastUpdateUserNameByElementId(e.getId()));

								// Hinzufuegen des neuen Objekts zum
								// Ergebnisvektor
								result.add(e, 1);
							}
						}
					}
				}
			} catch (SQLException ex) {
				throw new IllegalArgumentException(ex.getMessage());
			}
		}

		return result;
	}

	/**
	 * 
	 * Einfügen eines <code>Element</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param e
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Element insert(Element e) throws IllegalArgumentException,
			SQLException {
		if (e == null) {
			throw new IllegalArgumentException(
					"Übergebenes Objekt an insert() ist NULL.");
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
						+ "VALUES (" + e.getId() + ",'" + e.getName() + "','"
						+ e.getDescription() + "','"
						+ e.getMaterialDescription() + "','"
						+ getSqlDateFormat(e.getCreationDate()) + "','"
						+ getSqlDateFormat(e.getLastUpdate()) + "')";

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(sqlQuery);

				// Historie speichern
				com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
						.getUserService();

				com.google.appengine.api.users.User user = userService
							.getCurrentUser();
				UserMapper.getUserMapper().insertHistory(user.getUserId(),
						user.getNickname(), e.getId(), "erstellt",
						e.getLastUpdate());
			}

		} catch (SQLException ex) {

			throw new IllegalArgumentException(ex.getMessage());
		}

		cachePartlist.deleteById(e.getId());
		cachePartlist.add(e, 1);
		return e;
	}

	/*
	 * Rückgabe, des evtl. korrigierten Elements.
	 * 
	 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	 * Objekte übergeben werden, ware die Anpassung des Element-Objekts auch
	 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	 * explizite Rückgabe von e ist eher ein Stilmittel, um zu signalisieren,
	 * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	 */

	/**
	 * Wiederholtes Schreiben eines Objektes in die Datenbank
	 * 
	 * @param e
	 *            das Objekt, das in die Datenbank geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */

	public Element update(Element e) throws IllegalArgumentException,
			SQLException {
		if (e == null) {
			throw new IllegalArgumentException(
					"Übergebenes Objekt an update() ist NULL.");
		}

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE element SET name='" + e.getName() + "',"
					+ "description='" + e.getDescription() + "',"
					+ "material_description='" + e.getMaterialDescription()
					+ "'," + "last_update='"
					+ getSqlDateFormat(e.getLastUpdate()) + "'"
					+ " WHERE element_id = " + e.getId() + ";");


			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), e.getId(), "geändert",
					e.getLastUpdate());
			e.setLastUser(user.getNickname());
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		cachePartlist.deleteById(e.getId());
		cachePartlist.add(e, 1);
		// Um die Analogie zu insert(Element e) zu wahren, geben wir e zurück
		return e;
	}

	/**
	 * Löschen der Daten eines <code>Element</code> - Objekts aus der Datenbank
	 * 
	 * @param e
	 *            das aus der DB zu löschende "Objekt"
	 * @throws SQLException
	 */

	public void delete(Element e) throws IllegalArgumentException, SQLException {
		if (e == null) {
			throw new IllegalArgumentException(
					"Übergebenes Objekt an delete() ist NULL.");
		}
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM element WHERE element_id="
					+ e.getId());
			
			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), e.getId(), "gelöscht",
					new Date());


			cachePartlist.deleteById(e.getId());
		}

		catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

	}

	/**
	 * Löschen der Daten eines <code>Element</code> - Objekts aus der Datenbank
	 * anhand seiner Element Id
	 * 
	 * @param e
	 *            das aus der DB zu löschende "Objekt"
	 * @throws SQLException
	 */

	public void deleteById(int eId) throws IllegalArgumentException,
			SQLException {
		if (eId > -1) {
			throw new IllegalArgumentException(
					"Übergebene Id an delete() ist kleiner 0.");
		}
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM element WHERE element_id=" + eId);
			

			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), eId, "gelöscht",
					new Date());


			cachePartlist.deleteById(eId);
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

	/**
	 * Methode um eine Fuzzy Suche zu implementieren.
	 * 
	 * @param word
	 *            Wort nachdem gesucht wird.
	 * @return Varianten des Wortes als Vektor<String>
	 */
	private Vector<String> getLevenshtein1(String word) {
		Vector<String> words = new Vector<String>();
		for (int i = 0; i < word.length(); i++) {
			// insertions
			words.add(word.substring(0, i) + '_' + word.substring(i));
			// deletions
			words.add(word.substring(0, i) + word.substring(i + 1));
			// substitutions
			words.add(word.substring(0, i) + '_' + word.substring(i + 1));
		}
		// last insertion
		words.add(word + '_');
		return words;
	}

}
