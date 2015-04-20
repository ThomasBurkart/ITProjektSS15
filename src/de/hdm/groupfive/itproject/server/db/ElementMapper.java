package de.hdm.groupfive.itproject.server.db;


public class ElementMapper {
	/**
	 * 
	 */
	private static ElementMapper elementMapper = null;
	
	/**
	 * 
	 * @return ElementMapper 
	 */
	public static ElementMapper getElementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}
}
