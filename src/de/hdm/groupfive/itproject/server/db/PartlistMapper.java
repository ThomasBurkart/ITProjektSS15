package de.hdm.groupfive.itproject.server.db;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PartlistMapper {
	/**
	 * 
	 */
	private static PartlistMapper partlistMapper = null;

	/**
	 * 
	 * @return
	 */
	public static PartlistMapper getPartlistMapper() {
		if (partlistMapper == null) {
			partlistMapper = new PartlistMapper();
		}
		return partlistMapper;
	}

	
	/**
	 * Liefert vollständige Partlist zu Modul
	 * @return Partlist mit allen Sub-Elementen
	 */
	public Partlist findByModuleId(int id) throws SQLException {
		// DB Verbindung hier holen
		Connection con = DBConnection.connection();

		try {
			Partlist result = new Partlist();
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken

			// Suche alle Modul zu Modul Beziehungen
			ResultSet rs = stmt.executeQuery("SELECT subordinateID, quantity FROM ModuleRelationship "
					+ "WHERE superordinateID =" + id + ";");
			if (rs.next()) {

				Module subModule = ModuleMapper.getModuleMapper().findByElement(rs.getInt("subordinateID"));
				result.add(subModule, rs.getInt("quantity"));
			}

			Statement stmt2 = con.createStatement();
			
			// Suche alle Element zu Modul Beziehungen
			ResultSet rs2 = stmt2.executeQuery("SELECT element_id, quantity FROM ModuleElement "
					+ "WHERE module_id =" + id + ";");
			if (rs2.next()) {
				Element subElement = ElementMapper.getElementMapper().findElementById(rs2.getInt("element_id"));
				result.add(subElement, rs2.getInt("quantity"));
			}

			
			return result;
		} catch (SQLException ex) {
			throw ex;
		}

	}

}
