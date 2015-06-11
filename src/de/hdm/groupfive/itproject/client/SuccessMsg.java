package de.hdm.groupfive.itproject.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class SuccessMsg extends HTML {
	
	public SuccessMsg(String msg, RootPanel rp) {
		this.setHTML("<div class=\"alert alert-success alert-dismissible\" role=\"alert\">" + msg + "</div>");
		rp.insert(this, 0);
	}
	
	public SuccessMsg(String msg) {
		this.setHTML("<div class=\"alert alert-success alert-dismissible\" role=\"alert\">" + msg + "</div>");
	}
	
}