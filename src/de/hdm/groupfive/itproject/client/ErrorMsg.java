package de.hdm.groupfive.itproject.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class ErrorMsg extends HTML {
	
	public ErrorMsg(String msg, RootPanel rp) {
		this.setHTML("<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">" + msg + "</div>");
		rp.insert(this, 0);
	}
	
	public ErrorMsg(String msg) {
		this.setHTML("<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">" + msg + "</div>");
	}
	
}
