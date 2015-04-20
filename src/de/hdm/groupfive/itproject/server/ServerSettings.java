package de.hdm.groupfive.itproject.server;

import java.util.logging.Logger;

import de.hdm.groupfive.itproject.shared.CommonSettings;


public class ServerSettings extends CommonSettings {
	  private static final String LOGGER_NAME = "It-Projekt Server";
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);

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
	   * bedarfsweise Einfügen und Auskommentieren etwa von
	   * <code>System.out.println(...);</code> steuern. Sie belassen künftig
	   * sämtliches Logging im Code und können ohne abermaliges Kompilieren den Log
	   * Level "von außen" durch die Datei <code>logging.properties</code> steuern.
	   * Sie finden diese Datei in dem <code>war/WEB-INF</code>-Ordner Ihres
	   * Projekts. Der dort standardmäßig vorgegebene Log Level ist
	   * <code>WARN</code>. Dies würde bedeuten, dass Sie keine <code>INFO</code>
	   * -Meldungen wohl aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen
	   * erhielten. Wenn Sie also auch Log des Levels <code>INFO</code> wollten,
	   * mÃ¼ssten Sie in dieser Datei <code>.level = INFO</code> setzen.
	   * </p>
	   * 
	   * Weitere Infos siehe Dokumentation zu Java Logging.
	   * 
	   * @return die Logger-Instanz fÃ¼r die Server-Seite
	   */
	  public static Logger getLogger() {
	    return log;
	  }

}
