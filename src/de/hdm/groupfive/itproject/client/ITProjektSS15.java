package de.hdm.groupfive.itproject.client;

import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS15 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();

		// lädt den Login Dialog
		//LoginLogout.load();
		
		// Inhalt zur Sicherheit nochmal entfernen.
		RootPanel.get("main").clear();
		RootPanel.get("clientTitle").clear();

		
		// Titel des Clients
		Button clientTitle = new Button("&nbsp;Editor & Viewer");
		clientTitle.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new History());
						SearchPanel sp = new SearchPanel();
						sp.load();
					}
				});
		// Tooltip des Titels
		clientTitle.setTitle("Zurück zur Startseite");
		
		// StyleSheet festlegen
		clientTitle.setStylePrimaryName("btn btn-link navbar-brand");
		
		// Titel-Button dem entsprechenden Layer hinzufügen.
		RootPanel.get("clientTitle").add(clientTitle);
		
		
		NavigationBar.load();
		SearchPanel sp = new SearchPanel();
		sp.load();
		RootPanel.get("main").add(new History());
		
		
	}

	
	

}
