package de.hdm.groupfive.itproject.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * ErrorMsg bietet ein Showcase, um einen Fehler in der GUI mit Datum und
 * Fehlertext anzeigen zu können.
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class ErrorMsg extends FlowPanel {

	/**
	 * Konstruktor durch den eine ErrorMessageBox erzeugt wird
	 * 
	 * @param msg
	 *            Fehlermeldung
	 * @param rp
	 *            vorgegebenes Panel in dem die NAchricht angezeigt wird.
	 */
	public ErrorMsg(String msg, RootPanel rp) {
		// Falls bereits eine MsgBox vorhanden ist wird diese entfernt um Platz
		// für die neue
		// zu schaffen
		if (RootPanel.get("errorOrInfoBox") != null) {
			RootPanel.get("errorOrInfoBox").getElement().removeFromParent();
		}
		this.getElement().setId("errorOrInfoBox");
		this.setStylePrimaryName("alert alert-danger alert-dismissible");
		this.getElement().setAttribute("role", "alert");
		DateTimeFormat dateFormat = DateTimeFormat
				.getFormat("dd.MM.yyyy HH:mm:ss");
		this.getElement().setInnerHTML(
				"<i>" + dateFormat.format(new Date()) + "</i> - " + msg);
		rp.insert(this, 0);
	}

	/**
	 * Konstruktor durch den eine ErrorMessageBox erzeugt wird
	 * 
	 * @param msg
	 *            Fehlermeldung
	 * 
	 */
	public ErrorMsg(String msg) {
		// Falls bereits eine MsgBox vorhanden ist wird diese entfernt um Platz
		// für die neue
		// zu schaffen
		if (RootPanel.get("errorOrInfoBox") != null) {
			RootPanel.get("errorOrInfoBox").getElement().removeFromParent();
		}

		this.getElement().setId("errorOrInfoBox");
		this.setStylePrimaryName("alert alert-danger alert-dismissible");
		this.getElement().setAttribute("role", "alert");
		DateTimeFormat dateFormat = DateTimeFormat
				.getFormat("dd.MM.yyyy HH:mm:ss");
		this.getElement().setInnerHTML(
				"<i>" + dateFormat.format(new Date()) + "</i> - " + msg);
	}

}
