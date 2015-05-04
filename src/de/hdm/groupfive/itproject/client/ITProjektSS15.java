package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS15 implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	    /*
	     * Ab hier bauen wir sukzessive den Navigator mit seinen Buttons aus.
	     */

	    /*
	     * Neues Button Widget erzeugen und eine Beschriftung festlegen.
	     */
	    final Hyperlink homeLabel = new Hyperlink("Startseite", "mainpage");
	   
	    RootPanel.get("homelabel").add(homeLabel);

	}
}
