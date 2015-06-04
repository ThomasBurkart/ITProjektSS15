package de.hdm.groupfive.itproject.server.db;

import de.hdm.groupfive.itproject.shared.bo.User;

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
	
	/**
	 * 
	 */
	public User insert(User user){
		return null;
	}
	
	/**
	 * 
	 */
	public void delete(User user){
		
	}
	
	/**
	 * 
	 */
	public User findById(int id){
		return null;
	}
	
	/**
	 * 
	 */
	public User update(User user){
		return null;
	}
	
	/**
	 * 
	 */
	public User authentication(String email, String password){
		return null;
	}
}
