package de.hdm.groupfive.itproject.server.db;

import java.sql.*;
import java.util.Date;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.*;

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
			ResultSet rs = stmt.executeQuery("SELECT module_id, name, element_id "
					+ "FROM module WHERE module_id=" + id + " ORDER BY element_id");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			while (rs.next()) {
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				return m;
			}

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

			while (rs.next()) {
				m.setModuleId(rs.getInt("module.module_id"));
				m.setId(rs.getInt("element.element_id"));
				m.setPartlist(PartlistMapper.getPartlistMapper().findByModuleId(m.getModuleId()));
				
				m.setName(rs.getString("element.name"));
				m.setDescription(rs.getString("description"));
				m.setMaterialDescription(rs
						.getString("material_description"));
				
				Timestamp timestamp = rs.getTimestamp("creation_date");
				if (timestamp != null) {
					Date creationDate = new java.util.Date(timestamp.getTime());
					m.setCreationDate(creationDate);
				}
				
				Timestamp timestamp2 = rs.getTimestamp("last_update");
				if (timestamp2 != null) {
					Date lastUpdateDate = new java.util.Date(timestamp2.getTime());
					m.setLastUpdate(lastUpdateDate);
				}
				return  m;
			}
		} catch (SQLException ex) {
			
			throw new IllegalArgumentException(ex.getMessage());
		}
		return null;		
	}
	
	

	/**
	 * Auslesen aller Modules.
	 * 
	 * @return Ein Vektor mit Module-Objekten, die sämtliche Modules
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public Vector<Module> findAll() throws IllegalArgumentException,
			SQLException {
		// DB Verbindung hier holen
		Connection con = DBConnection.connection();

		Vector<Module> result = new Vector<Module>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
					+ "FROM module ORDER BY id");
			// Fuer jeden Eintrag im Suchergebnis wird nun ein Module-Objekt
			// erstellt
			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(m);
			}
		} catch (SQLException ex) {
			
			throw new IllegalArgumentException(ex.getMessage());
		}
		

		
		return result;
	}

	public Partlist findbyElementId(int elementId)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		Partlist result = new Partlist();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM module WHERE module.elementId =" + elementId
					+ " ORDER BY id");
			
			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				result.add(m, 1);
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		

		
		return result;
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
			ResultSet rs = stmt.executeQuery("SELECT MAX(module_id) AS maxid FROM module");

			if (rs.next()) {
				/*
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				m.setModuleId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO module (module_id, name, element_id) VALUES ("
						+ m.getModuleId() + ",'"+ m.getName()+ "',"+ m.getId() + ")");
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		
		return m;

	}

	public void delete(Module m) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			// TODO: alle zuordnungen zum Modul löschen
			// alle Produkte von Modul löschen.

			stmt.executeUpdate("DELETE FROM module WHERE module_id="
					+ m.getModuleId());
			
			Statement stmt2 = con.createStatement();

			// Zuordnungen zwischen dem zu löschenden Modul und anderen Modulen löschen
			stmt2.executeUpdate("DELETE FROM ModuleRelationShip WHERE superordinateID="
					+ m.getModuleId() + " OR subordinateID="+ m.getModuleId());
			
			Statement stmt3 = con.createStatement();

			// Zuordnungen zwischen dem zu löschenden Modul und anderen Elementen löschen

			stmt3.executeUpdate("DELETE FROM ModuleElement WHERE module_id="
					+ m.getModuleId());
			
			ElementMapper.getElementMapper().delete(m);
			
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

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE module SET name = '" + m.getName()
					+ "'," + "element_id=" + m.getId() + " WHERE module_id ="
					+ m.getModuleId());

		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		
		
		return m;
	}

	public Partlist findByName(String name, int maxResults)
			throws IllegalArgumentException, SQLException {
		return ElementMapper.getElementMapper().findByName(name, maxResults, true);
	}

	public Vector<Module> findByElementId(int elementId)
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();

		Vector<Module> result = new Vector<Module>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
					+ "FROM module" + "WHERE module.elemetId =" + elementId
					+ "ORDER BY id");

			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));

				result.addElement(m);
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}

		return result;
	}
	
	public void assignModule(Module superMod, Module subMod, int amount) 
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(ModuleRelationship_id) AS maxid FROM ModuleRelationship");

			if (rs.next()) {
				/*
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				int id = (rs.getInt("maxid") + 1);
				
				stmt = con.createStatement();

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO ModuleRelationship (ModuleRelationship_id, quantity, superordinateID, subordinateID) VALUES ("
						+ id + ","+ amount + ","+ superMod.getId() + ","+subMod.getId()+")");
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}			
	}
	
	public void assignElement(Module m, Element e, int amount) 
			throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(ModuleElement_id) AS maxid FROM ModuleElement");

			if (rs.next()) {
				/*
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				int id = (rs.getInt("maxid") + 1);
				
				stmt = con.createStatement();

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO ModuleElement (ModuleElement_id, quantity, module_id, element_id) VALUES ("
						+ id + ","+ amount + ","+ m.getId() + ","+e.getId()+")");
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
}
