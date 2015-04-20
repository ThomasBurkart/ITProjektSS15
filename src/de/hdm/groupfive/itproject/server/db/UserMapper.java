package de.hdm.groupfive.itproject.server.db;

public class UserMapper {
	/**
	 * 
	 */
	private static UserMapper userMapper = null;
	
	/**
	 * 
	 * @return
	 */
	public static UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}
}
