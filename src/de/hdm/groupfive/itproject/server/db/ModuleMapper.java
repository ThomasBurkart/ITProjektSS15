package de.hdm.groupfive.itproject.server.db;


import java.sql.*;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.*;




public class ModuleMapper {
	/**
	 * Die Klasse ModuleMapper wird nur einmal instantiiert. Man
	 * spricht hierbei von einem sogenannten <b>Singleton</b>.
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
	 * @return
	 */
	
	protected ModuleMapper() {
		
	}
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ModuleMapper.moduleMapper()</code>. Sie
	 * stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur
	 * eine einzige Instanz von <code>ModuleMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ModuleMapper sollte nicht mittels
	 * <code>new</code> instantiiert werden, sondern stets durch Aufruf dieser
	 * statischen Methode.
	 * 
	 * @return DAS <code>ModuleMapper</code>-Objekt.
	 * { @link moduleMapper}
	 */
	
	public static ModuleMapper getModuleMapper() {
		if (moduleMapper == null) {
			moduleMapper = new ModuleMapper();
		}

		return moduleMapper;
	}
	
	/**
	 * Suchen eines Modules mit vorgegebener id. Da diese eindeutig
	 * ist, wird genau ein Objekt zurückgegeben.
	 * 
	 * @param  id Primärschlüsselattribut (->DB)
	 * @return Module-Objekt, das dem übergebenen Schlüssel
	 *         entspricht, null bei nicht vorhandenem DB-Tupel.
	 */
	
	public Module findById(int id) throws IllegalArgumentException, SQLException {
		//DB Verbindung holen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			//Statement ausfuellen und als Query an DB schicken
			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
								+ "FROM module"
								+ "WHERE id=" + id
								+ "ORDER BY id");
			
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if(rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				
				return m;
			}
			
		}catch (SQLException e1) {
				throw new IllegalArgumentException(e1.getMessage());
				}
			return null;
		}
	
	
	
	public Module findByElement(int elementId) throws IllegalArgumentException, SQLException {
				Connection con = DBConnection.connection();
				
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT module.id, module.elementId"
									+ "FROM module"
									+ "INNER JOIN element"
									+ "ON module.elementId = element.id"
									+ "WHERE module.elementId ="
									+ elementId
									+ "ORDER BY id");
					
					while (rs.next()) {
						Module m = new Module();
						m.setId(rs.getInt("module.id"));
						
						return m;
					}
				} catch (SQLException e1) {
					throw new IllegalArgumentException(e1.getMessage());
				}
				return null;
	}
	/**
	 * Auslesen aller Modules.
	 * 
	 * @return Ein Vektor mit Module-Objekten, die sämtliche
	 *         Modules repräsentieren. Bei evtl. Exceptions wird ein
	 *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public Vector<Module> findAll() throws IllegalArgumentException, SQLException {
		//DB Verbindung hier holen
		Connection con = DBConnection.connection();
		
		Vector<Module> result = new Vector<Module>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
								+ "FROM module"
								+ "ORDER BY id");
			//Fuer jeden Eintrag im Suchergebnis wird nun ein Module-Objekt erstellt
			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				
				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(m);
			}
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
			}
		return result;
				}
				
	public Vector<Module> findbyElementId(int elementId) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Module> result = new Vector<Module>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
								+ "FROM module"
								+ "WHERE module.elementId ="
								+ elementId
								+ "ORDER BY id");
			
			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				
				result.addElement(m);
			}
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * Einfügen eines <code>Module</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param m das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */

	public Module insert(Module m) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
		
			/*
			 * Zunächst schauen wir nach, welches der momentan höhste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid" + "FROM module");
			
			if (rs.next()) {
				/*
				 * m erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel
				 */
				m.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				// die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO module (id, name, elementId)"
						+ "VALUES ("
						+ m.getId()
						+ ",'"
						+ m.getName());
//						+ "','"
//						+ m.getElementId() + "')");
			}
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
		
		return m;
		
		}

		
		
	public void delete(Module m) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM module"
							+ "WHERE id =" + m.getId());
		} catch (SQLException e1) {
		throw new IllegalArgumentException(e1.getMessage());
	}
  }


		
/**
 * Wiederholtes Schreiben eines Objekts in die Datenbank.
 * 
 * @param m das Objekt, das in die DB geschrieben werden soll
 * @return das als Parameter übergebene Objekt
 */
	public Module update(Module m) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE module SET"
					+ "name = '"
					+ m.getName()
					//+ "',"
//					+ "elementId = "
//					+ "'"
//					+ m.getElementId()
					+ "' "
					+ "WHERE id ="
					+ m.getId());
					
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
		return m;
	}
					
		
	public Vector<Module> findByName(String name) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Module> result = new Vector<Module>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM module"
						 + "WHERE name =" 
						 + name
						 + "ORDER BY id");
			
			while (rs.next()) {
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
			//	m.setElementId(rs.getInt("elementId"));
				
				result.addElement(m);
			}
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
		return result;
	}
	
	public Vector<Module> findByElementId(int elementId) throws IllegalArgumentException, SQLException {
		Connection con = DBConnection.connection();
		
		Vector<Module> result = new Vector<Module>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, elementId"
						 + "FROM module"
						 + "WHERE module.elemetId ="
						 + elementId
						 + "ORDER BY id");
			
			while(rs.next()){
				Module m = new Module();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				
				result.addElement(m);
			}
		} catch (SQLException e1) {
			throw new IllegalArgumentException(e1.getMessage());
		}
		return result;
	}
		
			
	public Vector<Product> getAllProducts(Module module) throws SQLException {
		Vector<Product> result = new Vector<Product>();
		// TODO: result befüllen.
		return result;
	}
}
