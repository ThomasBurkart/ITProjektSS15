package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse enthält das Login Widget
 * @author Thomas Burkart
 * @since 28.05.2015
 */
public class LoginLogout {
	
	
	private static DialogBox dialogBox; 
	/**
	 * Die Methode lädt den Login Dialog in die Web-Applikation
	 */
	public static void load() {
		if (dialogBox == null) {
			dialogBox = createLoginDialogBox();
			dialogBox.setGlassEnabled(true);
			dialogBox.setAnimationEnabled(true);
		}
		dialogBox.center();
		dialogBox.show();
	}

	/**
	 * Die Methode erzeugt die Dialog Box mit entsprechenden Feldern für die Anmeldung bzw. die
	 * Registration eines neuen Benutzers.
	 * @return
	 */
	private static DialogBox createLoginDialogBox() {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Anmelden");
		dialogBox.setModal(true);

		final Grid grid = new Grid(3, 2);
		grid.setCellSpacing(50);
		grid.setCellPadding(50);
		grid.setWidget(0, 0, new HTML("E-Mail"));
		final TextBox emailBox = new TextBox();
		emailBox.setFocus(true);
		emailBox.setWidth("220px");
		grid.setWidget(0, 1, emailBox);

		grid.setWidget(1, 0, new HTML("Passwort"));
		final PasswordTextBox passwordBox = new PasswordTextBox();
		passwordBox.setWidth("220px");
		grid.setWidget(1, 1, passwordBox);

		// Anmelde Button im ersten Dialog
		Button loginButton = new Button("anmelden", new ClickHandler() {
			public void onClick(ClickEvent event) {
				String email = emailBox.getValue();
				String pass = passwordBox.getValue();
				// TODO: Methode zum einloggen eines Benutzers aufrufen ->
				// onSuccess/OnFailure Handling?!
				dialogBox.hide();
				
				NavigationBar.load();
				SearchPanel.load();
				RootPanel.get("main").add(new History());
			}
		});
		loginButton.setWidth("100px");
		Button regButton = new Button("registrieren", new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.setText("Registration");
				grid.resizeRows(6);
				grid.setWidget(2, 0, new HTML("Passwort wiederholen"));
				final PasswordTextBox passwordBox2 = new PasswordTextBox();
				passwordBox2.setWidth("220px");
				grid.setWidget(2, 1, passwordBox2);

				grid.setWidget(3, 0, new HTML("Vorname"));
				final TextBox firstNameBox = new TextBox();
				firstNameBox.setWidth("220px");
				grid.setWidget(3, 1, firstNameBox);

				grid.setWidget(4, 0, new HTML("Name"));
				final TextBox nameBox = new TextBox();
				nameBox.setWidth("220px");
				grid.setWidget(4, 1, nameBox);
				// Registrieren Button im Registrations-Dialog
				Button regButton = new Button("registrieren",
						new ClickHandler() {
							public void onClick(ClickEvent event) {
								// TODO: Methode zum registrieren eines neuen
								// Benutzers aufrufen.
								dialogBox.hide();
							}
						});
				regButton.setWidth("220px");
				grid.setWidget(5, 1, regButton);
			}
		});
		regButton.setWidth("100px");
		HorizontalPanel actionPanel = new HorizontalPanel();
		// TODO Reg Button evtl. entfernen
		//actionPanel.add(regButton);
		actionPanel.add(loginButton);
		grid.setWidget(2, 1, actionPanel);
		VerticalPanel dialogContent = new VerticalPanel();
		HTML details = new HTML(
				"Bitte geben Sie Ihre Benutzerdaten ein, um sich<br />am System anzumelden.");
		dialogContent.add(details);
		dialogContent.add(grid);
		dialogBox.setWidget(dialogContent);
		// Return the dialog box
		return dialogBox;
	}
}
