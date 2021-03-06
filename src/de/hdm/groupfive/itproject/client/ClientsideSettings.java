package de.hdm.groupfive.itproject.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.groupfive.itproject.shared.AdministrationCommon;
import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.CommonSettings;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen
 * relevant sind.
 * 
 * @author Peter Thies, Timo Fesseler
 * @version 1.1
 * @since 30.06.2015
 * 
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>AdministrationCommon</code>.
	 */
	private static AdministrationCommonAsync administration = null;

	/**
	 * Der momentane Benutzer
	 */
	private static User currentUser = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ClientsideSettings.getLogger();
	 * </pre>
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * <pre>
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * </pre>
	 * 
	 * oder
	 * 
	 * <pre>
	 * logger.info(&quot;Lege neues Bauteil an.&quot;);
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! Severe und info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfügen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen künftig
	 * sämtliches Logging im Code und können ohne abermaliges Kompilieren den
	 * Log Level "von außen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmäßig vorgegebene Log Level ist <code>WARN</code>. Dies
	 * würde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl aber
	 * <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn Sie
	 * also auch Log des Levels <code>INFO</code> wollten, müssten Sie in dieser
	 * Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen
	 * AdministrationCommon. Diese Methode erstellt die AdministrationCommon,
	 * sofern sie noch nicht existiert. Bei wiederholtem Aufruf dieser Methode
	 * wird stets das bereits zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>AdministrationCommonAsync administration = ClientsideSettings.getAdministration()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs
	 *         <code>AdministrationCommonAsync</code>
	 * @author Thomas Burkart, Peter Thies
	 * @since 01.07.2015
	 */
	public static AdministrationCommonAsync getAdministration() {
		// Gab es bislang noch keine Administration-Instanz, dann...
		if (administration == null) {
			// Zunächst instantiieren wir Administration
			administration = GWT.create(AdministrationCommon.class);

			final AsyncCallback<Void> initAdministrationCallback = new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					ClientsideSettings
							.getLogger()
							.severe("Der Administration konnte nicht initialisiert werden!");
				}

				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info(
							"Der Administration wurde initialisiert.");
				}
			};

			administration.init(initAdministrationCallback);
		}

		// So, nun brauchen wir die Administration nur noch zurückzugeben.
		return administration;
	}

	/**
	 * + * Auslesen des momentanen Benutzers + * @return Momentaner Benutzer +
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * + * Setzen des momentanen Benutzers + * @param currentUser Momentaner
	 * Benutzer +
	 */
	public static void setCurrentUser(User currentUser) {
		ClientsideSettings.currentUser = currentUser;
	}

}
