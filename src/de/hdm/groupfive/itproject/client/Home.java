package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.User;

public class Home extends Showcase {

	private String headlineText;
	private String headlineTextStyle;

	public Home() {
		this.headlineText = "Herzlich Willkommen "
				+ ClientsideSettings.getCurrentUser().getUserName() + " :)";
		this.headlineTextStyle = "formTitle";
	}

	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}

	@Override
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	@Override
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
