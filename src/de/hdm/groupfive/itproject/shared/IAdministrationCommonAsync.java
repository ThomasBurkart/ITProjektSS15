package de.hdm.groupfive.itproject.shared;

import de.hdm.groupfive.itproject.shared.bo.User;

public interface IAdministrationCommonAsync {
	public void registerUser(String email, String password);
	public void loginUser(String email, String password);
	public void logoutUser();
	public void getElementMapper();
	public void getModuleMapper();
	public void getProductMapper();
	public void getPartlistMapper();
	public void getUserMapper();
	public void setUser(User user);
	public void getUser();
}
