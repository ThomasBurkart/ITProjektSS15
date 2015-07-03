package de.hdm.groupfive.itproject.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;

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
