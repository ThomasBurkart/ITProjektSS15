package de.hdm.groupfive.itproject.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Klasse für eine Erfolgs-Nachricht im Showcase z.B. falls etwas erfolgreich gespeichert wurde.
 * @author Thomas Burkart
 *
 */
public class SuccessMsg extends FlowPanel {
	
	
	/**
	 * Mit diser Methode wird eine Erfolgsnachricht ausgegeben
	 * @param msg
	 * @param rp
	 */
	public SuccessMsg(String msg, RootPanel rp) {
		// Falls bereits eine MsgBox vorhanden ist wird diese entfernt um Platz für die neue
		// zu schaffen
		if (RootPanel.get("errorOrInfoBox") != null) {
			RootPanel.get("errorOrInfoBox").getElement().removeFromParent();
		}
		this.getElement().setId("errorOrInfoBox");
		this.setStylePrimaryName("alert alert-success alert-dismissible");
		this.getElement().setAttribute("role", "alert");
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
		this.getElement().setInnerHTML("<i>"+dateFormat.format(new Date()) + "</i> - " + msg);
		rp.insert(this, 0);
	}
	
	public SuccessMsg(String msg) {
		// Falls bereits eine MsgBox vorhanden ist wird diese entfernt um Platz für die neue
		// zu schaffen
		if (RootPanel.get("errorOrInfoBox") != null) {
			RootPanel.get("errorOrInfoBox").getElement().removeFromParent();
		}
		
		this.getElement().setId("errorOrInfoBox");
		this.setStylePrimaryName("alert alert-success alert-dismissible");
		this.getElement().setAttribute("role", "alert");
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
		this.getElement().setInnerHTML("<i>"+dateFormat.format(new Date()) + "</i> - " + msg);
	}
	
}