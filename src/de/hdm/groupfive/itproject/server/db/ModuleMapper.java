package de.hdm.groupfive.itproject.server.db;

import java.sql.SQLException;
import java.util.Vector;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class ModuleMapper {
	/**
	 * 
	 */
	private static ModuleMapper moduleMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static ModuleMapper getModuleMapper() {
		if (moduleMapper == null) {
			moduleMapper = new ModuleMapper();
		}

		return moduleMapper;
	}
	
	/**
	 * 
	 */
	public Module insert(Module module) throws SQLException {
		// TODO: DB Aufruf mit INSERT INTO, nach dem Einfügen die ID des NEU eingefügten Moduls
		// abfragen und diese per setId() der ParameterVariable setzen und dieses per return
		// zurück geben
		
		return module;
	}
	
	/**
	 * 
	 */
	public void delete(Module module) throws SQLException {
		// TODO: 
	}
	
	/**
	 * 
	 */
	public Module findById(int id) throws SQLException {
		Module result = new Module();
		
		// TODO: DB Abfragen
		
		return result;
	}
	
	/**
	 * 
	 */
	public Module update(Module module) throws SQLException {
		// TODO: UPDATE DB bei Erfolg Modul zurück geben, sonst Exception schmeißen
		
		return module;
	}
	
	
	/**
	 * 
	 */
	public Module findByName(String name) throws SQLException {
		Module result = new Module();
		// TODO SELECT Befehl.
		return result;
	}
	
	/**
	 * 
	 */
	public Vector<Product> getAllProducts(Module module) throws SQLException {
		Vector<Product> result = new Vector<Product>();
		// TODO: result befüllen.
		return result;
	}
}
