package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

public class ModuleMapper {
	/**
	 * Die Klasse ModuleMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see moduleMapper()
	 */
	private static ModuleMapper moduleMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 * @return
	 */

	protected ModuleMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ModuleMapper.moduleMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>ModuleMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ModuleMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>ModuleMapper</code>-Objekt. { @link moduleMapper}
	 */

	public static ModuleMapper getModuleMapper() {
		if (moduleMapper == null) {
			moduleMapper = new ModuleMapper();
		}

		return moduleMapper;
	}

	/**
	 * Suchen eines Modules mit vorgegebener id. Da diese eindeutig ist, wird
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut (->DB)
	 * @return Module-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */

	public Module findById(int id) throws IllegalArgumentException,
			SQLException {
		// DB Verbindung holen
		
		Connection con = DBConnection.connection();
		Module m = new Module();
		try {
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT module_id, name, element_id "
							+ "FROM module WHERE module_id=" + id
							+ " ORDER BY element_id");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			while (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				return m;
			}
			
			rs.close();
			stmt.close();

		} catch (SQLException ex) {

			throw new IllegalArgumentException(ex.getMessage());
		}

		return null;
	}

	public Module findByElement(int elementId) throws IllegalArgumentException,
			SQLException {
		Connection con = DBConnection.connection();
		
		Module m = new Module();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM module INNER JOIN element "
							+ "ON module.element_id = element.element_id "
							+ "WHERE module.element_id =" + elementId
							+ " ORDER BY element.element_id");

			if (rs.next()) {
				m.setModuleId(rs.getInt("module.module_id"));
				m.setId(rs.getInt("element.element_id"));
				m.setPartlist(PartlistMapper.getPartlistMapper()
						.findByModuleId(m.getId()));
				if (m.getPartlist() != null) {
					for (PartlistEntry pe : m.getPartlist().getAllEntries()) {
						pe.setSuperModule(m);
					}
				}
				m.setName(rs.getString("element.name"));
				m.setDescription(rs.getString("description"));
				m.setMaterialDescription(rs.getString("material_description"));

				Timestamp timestamp = rs.getTimestamp("creation_date");
				if (timestamp != null) {
					Date creationDate = new java.util.Date(timestamp.getTime());
					m.setCreationDate(creationDate);
				}

				Timestamp timestamp2 = rs.getTimestamp("last_update");
				if (timestamp2 != null) {
					Date lastUpdateDate = new java.util.Date(
							timestamp2.getTime());
					m.setLastUpdate(lastUpdateDate);
				}
				m.setLastUser(UserMapper.getUserMapper().getLastUpdateUserNameByElementId(m.getId()));

				
				rs.close();
				stmt.close();
				return m;
			}
		} catch (SQLException ex) {

			throw new IllegalArgumentException(ex.getMessage());
		} 
		return null;
	}

	
	/**
	 * 
	 * Einfügen eines <code>Module</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param m
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */

	public Module insert(Module m) throws IllegalArgumentException,
			SQLException {
		Connection con = DBConnection.connection();

		try {
			ElementMapper em = ElementMapper.getElementMapper();
			m = (Module) em.insert(m);

			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(module_id) AS maxid FROM module");

			if (rs.next()) {
				/*
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				m.setModuleId(rs.getInt("maxid") + 1);

				
				PreparedStatement stmt2;
				stmt2 = con
						.prepareStatement("INSERT INTO module "
								+ "(module_id, name, element_id) "
								+ "VALUES (?, ?, ?)");

				stmt2.setInt(1, m.getModuleId());
				stmt2.setString(2, m.getName());
				stmt2.setInt(3, m.getId());
				stmt2.executeUpdate();
				stmt2.close();				

				// Historie speichern
				com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
						.getUserService();

				com.google.appengine.api.users.User user = userService
							.getCurrentUser();
				UserMapper.getUserMapper().insertHistory(user.getUserId(),
						user.getNickname(), m.getId(), "erstellt",
						m.getLastUpdate());
				m.setLastUser(user.getNickname());
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		return m;

	}

	public void delete(Module m) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM module WHERE module_id="
					+ m.getModuleId());

			
			Statement stmt2 = con.createStatement();

			// Zuordnungen zwischen dem zu löschenden Modul und anderen Modulen
			// löschen
			stmt2.executeUpdate("DELETE FROM ModuleRelationship WHERE superordinateID="
					+ m.getId() + " OR subordinateID=" + m.getId());
			
			Statement stmt3 = con.createStatement();

			// Zuordnungen zwischen dem zu löschenden Modul und anderen
			// Elementen löschen

			stmt3.executeUpdate("DELETE FROM ModuleElement WHERE module_id="
					+ m.getId());

			stmt.close();
			stmt2.close();
			stmt3.close();
			ElementMapper.getElementMapper().delete(m);
			

			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), m.getId(), "gelöscht",
					new Date());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param m
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Module update(Module m) throws IllegalArgumentException,
			SQLException {
		Connection con = DBConnection.connection();

		try {
			ElementMapper.getElementMapper().update(m);

			PreparedStatement stmt;
			stmt = con
					.prepareStatement("UPDATE module SET name=?, element_id=?, WHERE module_id=?");

			stmt.setString(1, m.getName());
			stmt.setInt(2, m.getId());
			stmt.setInt(3, m.getModuleId());
			stmt.executeUpdate();
			stmt.close();				

			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), m.getId(), "geändert",
					m.getLastUpdate());
			m.setLastUser(user.getNickname());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		return m;
	}

	public Partlist findByName(String name, int maxResults)
			throws IllegalArgumentException, SQLException {
		return ElementMapper.getElementMapper().findByName(name, maxResults,
				true);
	}


	public void assignModule(Module superMod, Module subMod, int amount)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			// Gibt es bereits einen Eintrag zur Zuordnung?
			ResultSet rs2 = stmt
					.executeQuery("SELECT ModuleRelationship_id AS id FROM ModuleRelationship WHERE "
							+ "superordinateID="
							+ superMod.getId()
							+ " AND subordinateID=" + subMod.getId());

			if (rs2.next()) {
				// Es gibt einen Eintrag, also wird diesem die neue Anzahl
				// (amount) zugeordnet
				Statement stmt2 = con.createStatement();

				stmt2.executeUpdate("UPDATE ModuleRelationship SET quantity="
						+ amount + " WHERE ModuleRelationship_id="
						+ rs2.getInt("id"));

			} else {
				// Es gibt noch keinen Eintrag, deswegen einen neuen anlegen.

				/*
				 * Zunächst schauen wir nach, welches der momentan höhste
				 * Primärschlüsselwert ist.
				 */
				ResultSet rs = stmt
						.executeQuery("SELECT MAX(ModuleRelationship_id) AS maxid FROM ModuleRelationship");

				if (rs.next()) {
					/*
					 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
					 * Primaerschluessel
					 */
					int id = (rs.getInt("maxid") + 1);

					stmt = con.createStatement();

					// die tatsaechliche Einfuegeoperation
					stmt.executeUpdate("INSERT INTO ModuleRelationship (ModuleRelationship_id, quantity, superordinateID, subordinateID) VALUES ("
							+ id
							+ ","
							+ amount
							+ ","
							+ superMod.getId()
							+ ","
							+ subMod.getId() + ")");
				}
				rs.close();
			}
			rs2.close();
			stmt.close();
			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), subMod.getId(), "zugeordnet",
					new Date());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}

	public void assignElement(Module m, Element e, int amount)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			// Gibt es bereits einen Eintrag zur Zuordnung?
			ResultSet rs2 = stmt
					.executeQuery("SELECT ModuleElement_id AS id FROM ModuleElement WHERE "
							+ "module_id="
							+ m.getId()
							+ " AND element_id=" + e.getId());

			if (rs2.next()) {
				// Es gibt einen Eintrag, also wird diesem die neue Anzahl
				// (amount) zugeordnet
				Statement stmt2 = con.createStatement();

				stmt2.executeUpdate("UPDATE ModuleElement SET quantity="
						+ amount + " WHERE ModuleElement_id="
						+ rs2.getInt("id"));

			} else {
				// Es gibt noch keinen Eintrag, deswegen einen neuen anlegen.

				/*
				 * Zunächst schauen wir nach, welches der momentan höhste
				 * Primärschlüsselwert ist.
				 */
				ResultSet rs = stmt
						.executeQuery("SELECT MAX(ModuleElement_id) AS maxid FROM ModuleElement");

				if (rs.next()) {
					/*
					 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
					 * Primaerschluessel
					 */
					int id = (rs.getInt("maxid") + 1);

					stmt = con.createStatement();

					// die tatsaechliche Einfuegeoperation
					stmt.executeUpdate("INSERT INTO ModuleElement (ModuleElement_id, quantity, module_id, element_id) VALUES ("
							+ id
							+ ","
							+ amount
							+ ","
							+ m.getId()
							+ ","
							+ e.getId() + ")");
				}
				rs.close();
			}
			rs2.close();
			stmt.close();
			// Historie speichern
			com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
					.getUserService();

			com.google.appengine.api.users.User user = userService
						.getCurrentUser();
			UserMapper.getUserMapper().insertHistory(user.getUserId(),
					user.getNickname(), e.getId(), "zugeordnet",
					e.getLastUpdate());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * Löscht Zuordnung zwischen Modulen aus den Datenbanken
	 * 
	 * @param superM
	 *            Übergeordnetes Modul
	 * @param subM
	 *            Untergeordnetes Modul
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	public void deleteModuleRelationshipAssign(Module superM, Module subM)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM ModuleRelationship WHERE superordinateID="
					+ superM.getId() + " AND subordinateID=" + subM.getId());
			stmt.close();
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 

	}

	/**
	 * Löscht Zuordnung zwischen Modulen aus den Datenbanken
	 * 
	 * @param superM
	 *            Übergeordnetes Modul
	 * @param subE
	 *            Untergeordnetes Modul
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	public void deleteModuleElementAssign(Module superM, Element subE)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM ModuleElement WHERE module_id="
						+ superM.getId() + " AND element_id=" + subE.getId());
			stmt.close();
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}
}
