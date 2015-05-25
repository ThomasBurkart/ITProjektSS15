package de.hdm.groupfive.itproject.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.User;

public class AdministrationCommon extends RemoteServiceServlet implements AdministrationCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Aktuell angemeldeter Benutzer
	 */
	private User currentUser = null;

	/**
	 * 
	 */
	private UserMapper userMapper = null;

	/**
	 * 
	 */
	private ElementMapper elementMapper = null;

	/**
	 * 
	 */
	private ModuleMapper moduleMapper = null;

	/**
	 * 
	 */
	private PartlistMapper partlistMapper = null;

	/**
	 * 
	 */
	private ProductMapper productMapper = null;

	/**
	 * 
	 */
	public AdministrationCommon() {
		this.userMapper = UserMapper.getUserMapper();
		this.elementMapper = ElementMapper.getElementMapper();
		this.moduleMapper = ModuleMapper.getModuleMapper();
		this.productMapper = ProductMapper.getProductMapper();
		this.partlistMapper = PartlistMapper.getPartlistMapper();
	}

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
		return this.elementMapper;
	}

	@Override
	public ModuleMapper getModuleMapper() {
		return this.moduleMapper;
	}

	@Override
	public ProductMapper getProductMapper() {
		return this.productMapper;
	}

	@Override
	public PartlistMapper getPartlistMapper() {
		return this.partlistMapper;
	}

	@Override
	public UserMapper getUserMapper() {
		return this.userMapper;
	}

	@Override
	public void setUser(User user) {
		this.currentUser = user;
	}

	@Override
	public User getUser() {
		return this.currentUser;
	}

}
