package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;

/**
 * Mapper-Klasse, die <code>Partlist</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see ModuleMapper, PartlistMapper, ProductMapper, UserMapper
 * @author Jakupi, Samire ; Thies
*/
public class PartlistMapper {
	
	/**
	 * Die Klasse PartlistMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see partlistMapper()
	 */
	private static PartlistMapper partlistMapper = null;

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>PartlistMapper.getPartlistMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>PartlistMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> PartlistMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>PartlistMapper</code>-Objekt. { @link partlistMapper}
	 */
	public static PartlistMapper getPartlistMapper() {
		if (partlistMapper == null) {
			partlistMapper = new PartlistMapper();
		}
		return partlistMapper;
	}

	
	/**
	 * Liefert vollständige Partlist zu Modul, anhand der ModuleId
	 * 
	 * @param id
	 * 		 Primärschlüsselattribut (->DB)
	 * @return Partlist mit allen Sub-Elementen
	 */
	public Partlist findByModuleId(int id) throws SQLException {
		// DB Verbindung holen
		Connection con = DBConnection.connection();

		Partlist result = new Partlist();
		try {
			Statement stmt = con.createStatement();
			
			// Statement ausfuellen und als Query an die DB schicken

			// Suche alle Modul zu Modul Beziehungen
			ResultSet rs = stmt.executeQuery("SELECT subordinateID, quantity FROM ModuleRelationship "
					+ "WHERE superordinateID =" + id + ";");
			while (rs.next()) {

				Module subModule = ModuleMapper.getModuleMapper().findByElement(rs.getInt("subordinateID"));
				result.add(subModule, rs.getInt("quantity"));
			}
			
			Statement stmt2 = con.createStatement();
			
			// Suche alle Element zu Modul Beziehungen
			ResultSet rs2 = stmt2.executeQuery("SELECT element_id, quantity FROM ModuleElement "
					+ "WHERE module_id =" + id + ";");
			while (rs2.next()) {
				Element subElement = ElementMapper.getElementMapper().findElementById(rs2.getInt("element_id"));
				result.add(subElement, rs2.getInt("quantity"));
			}
			
			rs.close();
			rs2.close();
			stmt.close();
			stmt2.close();

		} catch (SQLException ex) {
			throw ex;
		}
		
		return result;
	}
	
	/**
	 * Liefert vollständige Partlist mit zugehörigem Modul zu Produkt
	 * 
	 *@param id
	 *		Attribut von Produkt
	 * @return Partlist mit allen Sub-Elementen
	 */
	public Partlist findByProductId(int id) throws SQLException {
		// DB Verbindung hier holen
		Connection con = DBConnection.connection();
		Partlist result = new Partlist();

		try {
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken

			// Suche alle Modul zu Modul Beziehungen
			ResultSet rs = stmt.executeQuery("SELECT subordinateID, quantity FROM ModuleRelationship "
					+ "WHERE superordinateID =" + id + ";");
			if (rs.next()) {

				Module subModule = ModuleMapper.getModuleMapper().findByElement(rs.getInt("subordinateID"));
				result.add(subModule, rs.getInt("quantity"));
				
				rs.close();
				stmt.close();
				return result;
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			throw ex;
		}
		return null;
		
	}

}
