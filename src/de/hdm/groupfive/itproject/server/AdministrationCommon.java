package de.hdm.groupfive.itproject.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.ElementMapper;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.PartlistMapper;
import de.hdm.groupfive.itproject.server.db.ProductMapper;
import de.hdm.groupfive.itproject.server.db.UserMapper;
import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.User;

public class AdministrationCommon extends RemoteServiceServlet implements IAdministrationCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public User registerUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loginUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logoutUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElementMapper getElementMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleMapper getModuleMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductMapper getProductMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartlistMapper getPartlistMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserMapper getUserMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
