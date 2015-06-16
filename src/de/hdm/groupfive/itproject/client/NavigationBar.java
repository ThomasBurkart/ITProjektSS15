package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavigationBar {
	public static void load() {
		// Anlegen-Button einfügen
		RootPanel.get("navbar").add(createCreateButton());
		Button infoBtn = new Button(
				"<img src=\"img/info.png\" style=\"width: 19px\" />");
		infoBtn.setStylePrimaryName("btn btn-link");
		infoBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoggingDialog.getDialogBox().show();
			}
		});
		RootPanel.get("navbar").add(infoBtn);
		Button logoutBtn = new Button(
				"<img src=\"img/logout.png\" style=\"width: 19px\" />");
		logoutBtn.setStylePrimaryName("btn btn-link");
		logoutBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("navigator").clear();
				RootPanel.get("navbar").clear();
				LoginLogout.load();
			}
		});
		RootPanel.get("navbar").add(logoutBtn);
	}
	
	private static Widget createCreateButton() {
		Button createBtn = new Button("anlegen");
		createBtn.setStylePrimaryName("btn btn-success navBarCreateBtn");
		createBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new CreateElement());
			}
		});
		return createBtn;
	}
}
