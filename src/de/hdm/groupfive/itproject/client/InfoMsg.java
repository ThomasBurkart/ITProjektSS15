package de.hdm.groupfive.itproject.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class InfoMsg extends HTML {
	
	public InfoMsg(String msg, RootPanel rp) {
		this.setHTML("<div class=\"alert alert-info alert-dismissible\" role=\"alert\">" + msg + "</div>");
		rp.insert(this, 0);
	}
	
	public InfoMsg(String msg) {
		this.setHTML("<div class=\"alert alert-info alert-dismissible\" role=\"alert\">" + msg + "</div>");
	}
	
}
