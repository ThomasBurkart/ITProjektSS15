package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * EntryPoint für alle Klassen durch <code>onModuleLoad()</code>.
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class ITProjektSS15 implements EntryPoint {
	

	/**
	 * Das ist die EntryPoint-Methode.
	 */
	public void onModuleLoad() {
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();
		// lädt den Login Dialog

		administration.loginUser(false, new LoginCallback());
		// LoginLogout.load();

		

	}

	/**
	 * Asynchrone Anmelde-Klasse. 
	 * Showcase in dem die Antwort des Callbacks eingefügt wird.
	 * @author Timo Fesseler
	 *
	 */
	class LoginCallback implements AsyncCallback<User> {

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 */
		public LoginCallback() {
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
		public void onSuccess(User result) {
			if (result.isLoggedIn()) {
				ClientsideSettings.getLogger().severe(
						"User " + result.getUserName()
								+ " erfolgreich eingeloggt.");
				ClientsideSettings.setCurrentUser(result);
				// Inhalt zur Sicherheit nochmal entfernen.
				RootPanel.get("main").clear();
				RootPanel.get("clientTitle").clear();

				// Titel des Clients
				Button clientTitle = new Button("&nbsp;Editor & Viewer");
				clientTitle.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new Home());
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
				RootPanel.get("main").add(new Home());
				
			} else {
				Window.open(result.getLoginUrl(), "_self", "");
			}
		}
	}

}
