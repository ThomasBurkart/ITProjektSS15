package de.hdm.groupfive.itproject.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * Home bietet ein Showcase um die Begrüßungsfläche (rechtes Panel)
 * darstellen zu können.
 * 
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class Home extends Showcase {

	/**
	 * Überschrift des Showcase (graue Überschrift)
	 */
	private String headlineText;
	
	/**
	 * StyleSheet Klasse für die Überschrift des Showcase
	 */
	private String headlineTextStyle;

	/**
	 * Konstruktor der Klasse Home
	 * erzeugt die Willkommens-Fläche in der, 
	 * der eingeloggt Benutzer mit dem von Google 
	 * übergebenen Username begrüßt wird.
	 */
	public Home() {
		this.headlineText = "Herzlich Willkommen "
				+ ClientsideSettings.getCurrentUser().getUserName() + " :)";
		this.headlineTextStyle = "formTitle";
	}

	/**
	 * Auslesen des Titels von jeweiligen Showcase (graue Überschrift)
	 */
	protected String getHeadlineText() {
		return this.headlineText;
	}

	/**
	 * Auslesen der Formatierung des Titels
	 */
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

    /**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 * Aufbau des Willkommen-Panels (rechte Fläche), 
	 * in welcher eine kurze Beschreibung der Appplikation steht.
	 */
	protected void run() {
		HTML welcomeText = new HTML(
				"Dies ist ein verteiltes System zum Management von Strukturstücklisten, "
						+ "wie sie z.B. in der Produktionsplanung und -steuerung Anwendung finden. Das System "
						+ "ermöglicht das Erstellen, Verwalten und Anzeigen von Bauteilen, Baugruppen, sowie "
						+ "Enderzeugnissen.<br /><br />Zum Anlegen neuer Elemente verwenden Sie bitte den grünen "
						+ "'anlegen'-Button in der rechten oberen Ecke. Zum finden von bereits bestehenden Elementen, "
						+ "dient Ihnen die Suche in der linken Hälfte der Applikation. Hier können Sie durch Aufklappen, "
						+ "mit Hilfe des Pfeiles vor dem Eintrag, die entsprechende Stücklistenstruktur erkennen. Die "
						+ "Anzahl der untergeordneten Elemente steht immer in eckigen Klammern hinter der jeweiligen Bezeichung "
						+ "des Bauteils bzw. der Baugruppe.");
		this.add(welcomeText);

	}

}
