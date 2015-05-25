package de.hdm.groupfive.itproject.shared.bo;

public class User extends BusinessObject {

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
	 * der Superklasse erzeugt wird, ergänzt durch den Vor- und Nachnamen des
	 * jeweiligen Kunden.
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.userName;
	}
}
