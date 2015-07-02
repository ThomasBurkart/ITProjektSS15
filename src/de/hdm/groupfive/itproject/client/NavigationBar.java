package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;

/**
 * Hier werden Elemente der Navigationsleiste erzeugt
 * @author Thomas Burkart
 *
 */
public class NavigationBar {
	
	/**
	 * Diese Methode ladet die Navigationsleiste, mit Buttons etc.
	 */
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
				//LoginLogout.load();
				AdministrationCommonAsync administration = ClientsideSettings
						.getAdministration();
				administration.logoutUser(new LogoutCallback());
			}
		});
		RootPanel.get("navbar").add(logoutBtn);
	}
	
	/**
	 * Erzeugt den "anlegen"-Button
	 * @return Button-Widget
	 */
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
	
	/**
	 * Diese Methode ladet die Navigationsleiste für den Report Generator,
	 * hier ist kein "anlegen"-Button enthalten.
	 */
	public static void loadForReportGen() {
		// Anlegen-Button einfügen
		
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
				//LoginLogout.load();
				AdministrationCommonAsync administration = ClientsideSettings
						.getAdministration();
				administration.logoutUser(new LogoutCallback());
			}
		});
		RootPanel.get("navbar").add(logoutBtn);
	}	
}

/**
 * Callback Klasse für den Logout eines Benutzers
 * @author Thomas Burkart
 *
 */
class LogoutCallback implements AsyncCallback<String> {

	/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

	/**
	 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
	 * das übergebene Showcase fest.
	 * 
	 * @param c
	 *            Showcase an das die Rückmeldung ausgegeben wird.
	 */
	public LogoutCallback() {
	}

	/**
	 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
	 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
	 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
	 * verzeichnet.
	 */
	@Override
	public void onFailure(Throwable caught) {

		ClientsideSettings.getLogger().severe(
				"Error: " + caught.getMessage());
	}

	/**
	 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
	 * wird eine SuccessMsg im Showcase eingefügt.
	 */
	@Override
	public void onSuccess(String result) {
		
		Window.open(result, "_self", "");
		
	}
}