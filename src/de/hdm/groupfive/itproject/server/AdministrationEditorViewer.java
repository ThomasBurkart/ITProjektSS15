package de.hdm.groupfive.itproject.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public class AdministrationEditorViewer extends RemoteServiceServlet implements IAdministrationEditorViewer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
