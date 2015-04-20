package de.hdm.groupfive.itproject.server.db;

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
}
