package de.hdm.groupfive.itproject.shared.bo;

/**
 * 
 * 
 * @author Fesseler
 *
 */
public class User extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Der Username des Benutzers.
	 */
	private String userName = "";

	/**
	 * Der E-Mail des Benutzers.
	 */
	private String email = "";

	/**
	 * Die UserId des Benutzers
	 */
	private String userId;

	/**
	 * Anmelde-URL um den Login per Google durchzuf�hren
	 */
	private String loginUrl;

	/**
	 * Anmeldestatus des Benutzers
	 */
	private boolean isLoggedIn;

	/**
	 * Auslesen der Anmelde-URL
	 * 
	 * @return loginUrl Anmelde-URL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzen der Anmelde-URL
	 * 
	 * @param loginUrl
	 *            Anmelde-URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Setzen des Anmeldestatus
	 * 
	 * @param val
	 *            �bergebener Anmelde-Status
	 */
	public void setIsLoggedIn(boolean val) {
		this.isLoggedIn = val;
	}

	/**
	 * Auslesen ob der Benutzer angemeldet ist
	 * 
	 * @return isLoggedIn Anmeldestatus
	 */
	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	/**
	 * Auslesen der UserId des Benutzers
	 * 
	 * @return userId Benutzer-ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setzden der UserID des Benutzers
	 * 
	 * @param userId
	 *            Benutzer-ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Auslesen der Email des Benutzers
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Auslesen des Vornamens.
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Setzen der E-Mail.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Setzen des Nachnamens.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
	 * Diese besteht aus dem Text, der durch die <code>toString()</code>-Methode
	 * der Superklasse erzeugt wird, erg�nzt durch den Vor- und Nachnamen des
	 * jeweiligen Kunden.
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.userName;
	}
}