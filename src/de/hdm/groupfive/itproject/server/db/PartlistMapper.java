package de.hdm.groupfive.itproject.server.db;

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
}
