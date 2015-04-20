package de.hdm.groupfive.itproject.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class AdministrationReportGen  extends RemoteServiceServlet implements IAdministrationReportGen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
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

}
