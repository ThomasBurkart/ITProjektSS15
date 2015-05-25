package de.hdm.groupfive.itproject.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public class AdministrationCommonImpl extends RemoteServiceServlet implements AdministrationCommon {

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
	public AdministrationCommonImpl() {
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

	public Partlist findPartlistByModuleName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistByModuleId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistByModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object calculateMaterial(Partlist partlist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element createElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element editElement(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Module assignElement(Module module, Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Module createModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Module editModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteModule(Module module) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Module assignModule(Module module, Module subModule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product createProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product editProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element findElementById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Element> findElementsByCreator(User creator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Element> findElementsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
