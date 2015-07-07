package de.hdm.groupfive.itproject.server;

import java.util.logging.Logger;

import de.hdm.groupfive.itproject.shared.CommonSettings;


public class ServerSettings extends CommonSettings {
	  private static final String LOGGER_NAME = "Server";
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);
	  public static final String PAGE_URL_EDITOR = "http://alien-array-93415.appspot.com";
	  public static final String PAGE_URL_REPORT = "http://alien-array-93415.appspot.com/ReportGen.html";

	  /**
	   * <p>
	   * Auslesen des applikationsweiten (Server-seitig!) zentralen Loggers.
	   * </p>
	   * 
	   * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	   * 
	   * <pre>
	   * Logger logger = ServerSettings.getLogger();
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
	   * logger.info(&quot;Lege neuen Kunden an.&quot;);
	   * </pre>
	   * 
	   * <p>
	   * Bitte auf <em>angemessene Log Levels</em> achten! <em>severe</em> und
	   * <em>info</em> sind nur Beispiele.
	   * </p>
	   * 
	   * <h2>HINWEIS:</h2>
	   * <p>
	   * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	   * bedarfsweise Einf�gen und Auskommentieren etwa von
	   * <code>System.out.println(...);</code> steuern. Sie belassen k�nftig
	   * s�mtliches Logging im Code und k�nnen ohne abermaliges Kompilieren den Log
	   * Level "von au�en" durch die Datei <code>logging.properties</code> steuern.
	   * Sie finden diese Datei in dem <code>war/WEB-INF</code>-Ordner Ihres
	   * Projekts. Der dort standardm��ig vorgegebene Log Level ist
	   * <code>WARN</code>. Dies w�rde bedeuten, dass Sie keine <code>INFO</code>
	   * -Meldungen wohl aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen
	   * erhielten. Wenn Sie also auch Log des Levels <code>INFO</code> wollten,
	   * müssten Sie in dieser Datei <code>.level = INFO</code> setzen.
	   * </p>
	   * 
	   * Weitere Infos siehe Dokumentation zu Java Logging.
	   * 
	   * @return die Logger-Instanz für die Server-Seite
	   */
	  public static Logger getLogger() {
	    return log;
	  }

}
