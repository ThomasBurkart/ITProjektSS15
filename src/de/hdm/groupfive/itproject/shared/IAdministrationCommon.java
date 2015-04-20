package de.hdm.groupfive.itproject.shared;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.bo.*;

public interface IAdministrationCommon {
	public User registerUser(String email, String password);
	public User loginUser(String email, String password);
	public void logoutUser();
	public ElementMapper getElementMapper();
	public ModuleMapper getModuleMapper();
	public ProductMapper getProductMapper();
	public PartlistMapper getPartlistMapper();
	public UserMapper getUserMapper();
	public void setUser(User user);
	public User getUser();
	
}
