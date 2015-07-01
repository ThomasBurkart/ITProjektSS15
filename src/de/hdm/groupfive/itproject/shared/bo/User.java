package de.hdm.groupfive.itproject.shared.bo;

public class User extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Der Username des Benutzers.
	 */
	private String userName = "";
	
	private String userId;
	
	private String loginUrl;
	
	private boolean isLoggedIn;
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	public void setIsLoggedIn(boolean val) {
		this.isLoggedIn = val;
	}
	
	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	


	/**
	 * Der E-Mail des Benutzers.
	 */
	private String email = "";

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
