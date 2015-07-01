package de.hdm.groupfive.itproject.server.db;

import java.sql.*;

import com.google.appengine.api.rdbms.AppEngineDriver;

/**
 * Verwalten einer Verbindung zur Datenbank.<p>
 * <b>Vorteil:</b> Sehr einfacher Verbindungsaufbau zur Datenbank.<p>
 * <b>Nachteil:</b> Durch die Singleton-Eigenschaft der Klasse kann nur auf eine
 * fest vorgegebene Datenbank zugegriffen werden.<p>
 * In der Praxis kommen die meisten Anwendungen mit einer einzigen Datenbank 
 * aus. Eine flexiblere Variante für mehrere gleichzeitige Datenbank-Verbindungen
 * wäre sicherlich leistungsfähiger. Dies würde allerdings den Rahmen dieses 
 * Projekts sprengen bzw. die Software unnötig verkomplizieren, da dies für diesen
 * Anwendungsfall nicht erforderlich ist.
 * 
 * @author Thies
 */
public class DBConnection {

	/**
	 * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.<p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @see ElementMapper.elementMapper()
	 * @see ModuleMapper.moduleMapper()
	 * @see ProductMapper.productMapper()
	 * @see Partlist.partlistMapper()
	 */
	private static Connection con = null;
	
	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird. In einer 
	 * professionellen Applikation würde diese Zeichenkette aus einer 
	 * Konfigurationsdatei eingelesen oder über einen Parameter von außen 
	 * mitgegeben, um bei einer Veränderung dieser URL nicht die gesamte 
	 * Software neu komilieren zu müssen.
	 */
	private static String url = "jdbc:google:rdbms://project:project?user=demo&password=demo";
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch 
	 * <code>DBConnection.connection()</code>. Sie stellt die 
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	 * Instanz von <code>DBConnection</code> existiert.<p>
	 * 
	 * <b>Fazit:</b> DBConnection sollte nicht mittels <code>new</code> 
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.<p>
	 * 
	 * <b>Nachteil:</b> Bei Zusammenbruch der Verbindung zur Datenbank - dies kann
	 * z.B. durch ein unbeabsichtigtes Herunterfahren der Datenbank ausgelöst 
	 * werden - wird keine neue Verbindung aufgebaut, so dass die in einem solchen
	 * Fall die gesamte Software neu zu starten ist. In einer robusten Lösung 
	 * würde man hier die Klasse dahingehend modifizieren, dass bei einer nicht
	 * mehr funktionsfähigen Verbindung stets versucht würde, eine neue Verbindung
	 * aufzubauen. Dies würde allerdings ebenfalls den Rahmen dieses Projekts 
	 * sprengen.
	 * 
	 * @return DAS <code>DBConncetion</code>-Objekt.
	 * @throws SQLException
	 * @see con
	 */
	public static Connection connection() throws SQLException {
		// Wenn es bisher keine Conncetion zur DB gab, ... 
		if ( con == null || con.isClosed() ) {
			try {
				// Ersteinmal muss der passende DB-Treiber geladen werden
				DriverManager.registerDriver(new AppEngineDriver());

				/*
				 * Dann erst kann uns der DriverManager eine Verbindung mit den oben
				 * in der Variable url angegebenen Verbindungsinformationen aufbauen.
				 * 
				 * Diese Verbindung wird dann in der statischen Variable con 
				 * abgespeichert und fortan verwendet.
				 */
				con = DriverManager.getConnection(url);
			} 
			catch (SQLException ex) {
				con = null;
				ex.printStackTrace();
			}
		}
		
		// Zurückgegeben der Verbindung
		return con;
	}
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch 
	 * <code>DBConnection.closeConnection()</code>. Sie löst
	 * eine bestehende Verbindung zur Datenbank auf.
	 * 
	 * @throws	RuntimeException - beim "kappen" der DB-
	 * 			Verbindung kann ein Fehler entstehen,
	 * 			welcher mittelbar an die aufrufende Methode
	 * 			weitergeleitet wird
	 */
	
	public static void closeConnection() throws RuntimeException {
		try {
			if (con != null && (!con.isClosed()) ) {
				try {
					con.close();
					con = null;
				}
				catch (SQLException ex) {
					throw new RuntimeException("Fehler beim Trennen der DB-Verbindung aufgetreten:" + ex.getMessage());
			}
		}
		}
		catch (SQLException ex) {
			throw new RuntimeException("Fehler beim Trennen der DBVerbindung aufgetreten:" + ex.getMessage());
		}
		}
}
//Test
