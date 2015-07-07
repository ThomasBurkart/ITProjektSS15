package de.hdm.groupfive.itproject.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * ElementForm bietet ein Showcase, in dem ein Formular mit den Daten des bei
 * der Instanziierung übergebenen Bauteil/Baugruppe/Enderzeugnis gefüllt wird.
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 12.06.2015
 */
public class ElementForm extends Showcase {

	/**
	 * Überschrift des Showcase (graue Überschrift)
	 */
	private String headlineText;

	/**
	 * StyleSheet Klasse für die Überschrift des Showcase
	 */
	private String headlineTextStyle;

	/** Das Element, das im Formular geladen wird */
	private Element element;

	/** Die Anzahl des Elements */
	private int amount;

	/** übergeordnetes Modul des Elements */
	private Module superModule;

	/**
	 * Wird verwendet um zu prüfen ob es sich um ein Element handelt, dass neu
	 * angelegt wird.
	 */
	private static boolean newElement;

	/**
	 * Zuletzt verwendetes/gesetztes ElementForm Showcase, dient dazu in den
	 * onClick Funktionen entsprechende Rückmeldungen in das Showcase setzen zu
	 * können.
	 */
	public static Showcase currentShowcase;

	/**
	 * Kontruktor der Klasse ElementForm, erzeugt entsprechend dem übergebenen
	 * Element ein neues Formular und füllt die Formularfelder mit den Werten
	 * des Elements. Dabei wird zwischen Bauteilen, Baugruppen und
	 * Enderzeugnissen unterschieden.
	 * 
	 * @param element
	 *            Bauteil/Baugruppe/Enderzeugnis mit dessen Daten das Formular
	 *            gefüllt wird.
	 */
	public ElementForm(PartlistEntry pe) {
		this(pe.getElement(), pe.getAmount(), pe.getSuperModule());
	}

	/**
	 * 
	 * @param element
	 * @param amount
	 * @param superModule
	 */
	public ElementForm(Element element, int amount, Module superModule) {
		// Übergebenes Element
		this.element = element;

		this.amount = amount;

		this.superModule = superModule;

		// Das es sich um ein neues Element handelt wird vorerst auf "false"
		// gesetzt, kann sich
		// aber noch ändern
		newElement = false;

		// Überprüfung dass das übergebene Element existiert, um keine
		// Null-Pointer Exceptions
		// zu erzeugen
		if (this.element == null) {
			// Sollte das Element NULL sein, wird ein neues erzeugt.
			this.element = new Element();

			// Da es sich nun um ein neues Element handelt, muss das Attribut
			// newElement auf true
			// gesetzt werden
			newElement = true;

			// Überprüfung ob die Id des Elements kleiner oder gleich 0 ist,
			// dies würde bedeuten,
			// dass das Element neu ist.
		} else if (this.element.getId() <= 0) {

			// Da es sich nun um ein neues Element handelt, muss das Attribut
			// newElement auf true
			// gesetzt werden
			newElement = true;
		}

		// Um später wieder darauf zugreifen zu können, wird die HTML-Id des
		// Showcases auf elementForm
		// gesetzt
		this.getElement().setId("elementForm");

		// Style-Klasse für Titel in Main-Hälfte
		this.headlineTextStyle = "formTitle";

		// Um zu späterem Zeitpunkt, z.B. in den onClick Funktionen auf das
		// Showcase zugreifen zu können,
		// muss dieses in einer statischen Variable gesetzt werden.
		currentShowcase = this;
	}

	/**
	   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	@Override
	protected String getHeadlineText() {
		// Zuerst wird der Überschrift ein Leertext zugewiesen.
		this.headlineText = "";

		// Dann wird geprüft ob es sich um ein neues Element handelt
		// Das Attribut newElement wurde entsprechend im Konstruktor
		// gesetzt.
		if (newElement) {

			// Je nachdem, von welcher Klasse unser Element element erzeugt
			// wurde
			// wird eine andere Überschrift gewählt.
			if (this.element instanceof Product) {

				// Überschrift für neue Enderzeugnisse
				this.headlineText = "Neues Enderzeugnis anlegen";
			} else if (this.element instanceof Module) {

				// Überschrift für neue Baugruppen
				this.headlineText = "Neue Baugruppe anlegen";
			} else {

				// Überschrift für neue Bauteile
				this.headlineText = "Neues Bauteil anlegen";
			}
		} else {
			// Überschriften für bereits vorhandene
			// Bauteile/Baugruppen/Enderzeugnisse
			if (this.element instanceof Product) {

				// Überschrift für vorhandenes Enderzeugnis mit Verkaufsnamen.
				this.headlineText = "Enderzeugnis '"
						+ ((Product) this.element).getSalesName()
						+ "' editieren";
			} else if (this.element instanceof Module) {

				// Überschrift für vorhandene Baugruppe mit Name der Baugruppe
				this.headlineText = "Baugruppe '"
						+ ((Module) this.element).getName() + "' editieren";
			} else {

				// Überschrift für vorhandenes Bauteil mit Name des Bauteils
				this.headlineText = "Bauteil '" + this.element.getName()
						+ "' editieren";
			}
		}

		// Überschrift wird zurück gegeben.
		return this.headlineText;
	}

	/**
	   * Jeder Showcase besitzt eine einleitende Überschrift und dazugehörenden StyleSheet, 
	   * der durch diese Methode zu erstellen ist.
	   */
	@Override
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	 /**
	   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	   * eine "Einschubmethode", die von einer Methode der Basisklasse
	   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	   */
	@Override
	protected void run() {
		ClientsideSettings.getLogger().info("Element Formular: wird geladen.");

		// Das Formular wird in Tabellenform aufgebaut, dazu wird ein Grid
		// verwendet
		// Im der ersten Spalte des Grid werden die Bezeichnungen der Textboxen
		// gesetzt. In Spalte werden die Textboxen bzw. Textarea gesetzt.
		Grid grid;

		// Handelt sich bei dem Element um ein Enderzeugnis wird eine Zeile mehr
		// benötigt um den Verkaufsnamen ausgeben zu können.

		if (!newElement) {
			// Tabelle für Bauteile und Baugruppen (5 Zeilen und 2 Spalten)
			grid = new Grid(8, 2);
		} else {
			grid = new Grid(5, 2);
		}

		// Tabelle soll eine Breite von 100% erhalten.
		grid.setWidth("100%");

		String idDesc = "Bauteil Id";
		String nameDesc = "Bezeichnung";
		if (this.element instanceof Product) {
			idDesc = "Enderzeugnis Id";
			nameDesc = "Verkaufs-Bezeichnung";
		} else if (this.element instanceof Module) {
			idDesc = "Baugruppen Id";
		}

		HTML idText = new HTML(idDesc);
		idText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
		grid.setWidget(0, 0, idText);

		HTML nameText = new HTML(nameDesc);
		nameText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
		grid.setWidget(1, 0, nameText);

		HTML descText = new HTML("Beschreibung");
		descText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
		grid.setWidget(2, 0, descText);

		HTML matText = new HTML("Materialbezeichnung");
		matText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
		grid.setWidget(3, 0, matText);
		if (!newElement) {
			HTML creationText = new HTML("Erstellt am");
			creationText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(4, 0, creationText);

			HTML updateText = new HTML("Letzte Bearbeitung am");
			updateText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(5, 0, updateText);

			HTML updateUserText = new HTML("Zuletzt geändert von");
			updateUserText.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(6, 0, updateUserText);
		}

		final TextBox idTb = new TextBox();
		idTb.setName("textbox-id");
		idTb.setValue("wird automatisch gefüllt");
		idTb.setReadOnly(true);
		idTb.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11 textBox");
		grid.setWidget(0, 1, idTb);

		final TextBox nameTb = new TextBox();
		nameTb.setName("textbox-name");
		nameTb.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11 textBox");
		grid.setWidget(1, 1, nameTb);

		final TextArea descTb = new TextArea();
		descTb.setName("textarea-desc");
		descTb.setHeight("80px");
		descTb.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11 textBox");
		grid.setWidget(2, 1, descTb);

		final TextBox matTb = new TextBox();
		matTb.setName("textbox-mat");
		matTb.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11 textBox");
		grid.setWidget(3, 1, matTb);

		if (!newElement) {

			DateTimeFormat dateFormat = DateTimeFormat
					.getFormat("dd.MM.yyyy HH:mm:ss");

			HTML creationValue = new HTML(
					this.element.getCreationDate() != null ? dateFormat
							.format(this.element.getCreationDate()) : "");
			creationValue.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(4, 1, creationValue);

			HTML updateValue = new HTML(
					this.element.getLastUpdate() != null ? dateFormat
							.format(this.element.getLastUpdate()) : "");
			updateValue.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(5, 1, updateValue);

			HTML updateUserValue = new HTML(
					this.element.getLastUser() != null ? this.element
							.getLastUser() : "");
			updateUserValue
					.setStylePrimaryName("col-md-11 col-sm-11 col-xs-11");
			grid.setWidget(6, 1, updateUserValue);
		}
		// ÜBERSCHRIFT ANFANG
		// Noch keine Id vorhanden, deswegen handelt es sich um ein neues
		// Element. Entsprechend wird der Titel gesetzt.
		if (this.element.getId() > 0) {
			if (this.element instanceof Product) {
				Product p = (Product) this.element;
				idTb.setValue(p.getProductId() > 0 ? "" + p.getProductId()
						: "keine Id vorhanden");
				nameTb.setValue(p.getName() != null ? p.getName() : "");
				descTb.setValue(p.getDescription() != null ? p.getDescription()
						: "");
				matTb.setValue(p.getMaterialDescription() != null ? p
						.getMaterialDescription() : "");
			} else if (this.element instanceof Module) {
				Module m = (Module) this.element;
				idTb.setValue(m.getModuleId() > 0 ? "" + m.getModuleId()
						: "keine Id vorhanden");
				nameTb.setValue(m.getName() != null ? m.getName() : "");
				descTb.setValue(m.getDescription() != null ? m.getDescription()
						: "");
				matTb.setValue(m.getMaterialDescription() != null ? m
						.getMaterialDescription() : "");
			} else {
				Element e = this.element;
				idTb.setValue(e.getId() > 0 ? "" + e.getId()
						: "keine Id vorhanden");
				nameTb.setValue(e.getName() != null ? e.getName() : "");
				descTb.setValue(e.getDescription() != null ? e.getDescription()
						: "");
				matTb.setValue(e.getMaterialDescription() != null ? e
						.getMaterialDescription() : "");
			}

		}

		// ÜBERSCHRIFT ENDE

		// BREADCRUMB ANFANG
		UlListPanel breadcrumb = new UlListPanel();
		breadcrumb.setStylePrimaryName("breadcrumb");
		HTML assignText = new HTML("Zuordnung");
		assignText.setStylePrimaryName("bclink assignbclink");
		breadcrumb.add(assignText);
		if (superModule != null) {
			Button superModulBtn = new Button(superModule.getName());
			superModulBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					RootPanel.get("main").clear();
					SearchPanel.currentSearchPanel.getSearchResult()
							.getSelectionModel().clear();
					RootPanel.get("main").add(
							new ElementForm(superModule, 1, null));
				}
			});

			// StyleSheet festlegen
			superModulBtn.setStylePrimaryName("btn btn-link bclink");
			breadcrumb.add(superModulBtn);
		}

		Button thisElementBtn = new Button(element.getName());
		thisElementBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("main").add(
						new ElementForm(element, amount, superModule));

			}
		});

		// StyleSheet festlegen
		thisElementBtn.setStylePrimaryName("btn btn-link bclink");

		breadcrumb.add(thisElementBtn);
		this.add(breadcrumb);
		// BREADCRUMB ENDE

		this.add(grid);

		// ACTION BUTTONS für mögliche Aktionen ANFANG
		FlowPanel panel = new FlowPanel();
		panel.getElement().setId("actionBox");
		panel.setStylePrimaryName("actionBox");

		Button cancelBtn = new Button("abbrechen");
		cancelBtn.setStylePrimaryName("btn btn-warning createBtn");
		cancelBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				if (newElement) {
					// Handelt es sich um ein neues Element, wird bei Klick auf
					// abbrechen
					// wieder die Auswahl zum erstellen von Elementen angezeigt
					RootPanel.get("main").add(new CreateElement());
				} else {
					// Wird in einem bereits bestehenden Element auf abbrechen
					// geklickt,
					// wird einfach das gleiche Element erneut geladen, damit
					// Änderungen
					// verworfen werden
					RootPanel.get("main").add(
							new ElementForm(element, amount, superModule));
				}
			}
		});
		panel.add(cancelBtn);

		if (!newElement) {
			FlowPanel btnGroup = new FlowPanel();
			btnGroup.setStylePrimaryName("btn-group");
			Button deleteBtn = new Button();
			deleteBtn.setHTML("löschen <span class=\"caret\"></span>");
			deleteBtn
					.setStylePrimaryName("btn btn-danger createBtn dropdown-toggle");
			deleteBtn.getElement().setAttribute("data-toggle", "dropdown");
			deleteBtn.getElement().setAttribute("aria-haspopup", "true");
			deleteBtn.getElement().setAttribute("aria-expanded", "false");

			btnGroup.add(deleteBtn);
			UlListPanel ulList = new UlListPanel();
			ulList.addStyleName("dropdown-menu");
			Button deleteElem;
			if (this.element instanceof Product) {
				deleteElem = new Button("Enderzeugnis löschen");
			} else if (this.element instanceof Module) {
				deleteElem = new Button("Baugruppe löschen");
			} else {
				deleteElem = new Button("Bauteil löschen");
			}
			deleteElem.setStylePrimaryName("btn btn-link");
			deleteElem.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();

					if (element instanceof Product) {
						administration.deleteProduct((Product) element,
								new ElementDeleteCallback(currentShowcase));
					} else if (element instanceof Module) {
						administration.deleteModule((Module) element,
								new ElementDeleteCallback(currentShowcase));
					} else {
						administration.deleteElement(element,
								new ElementDeleteCallback(currentShowcase));
					}

				}
			});

			ulList.add(deleteElem);

			Button deleteAssign = new Button("Zuordnung löschen");
			deleteAssign.setEnabled(superModule != null);
			deleteAssign.setStylePrimaryName("btn btn-link");
			deleteAssign.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					administration.deleteAssignment(new PartlistEntry(element,
							amount, superModule), new DeleteAssignCallback());
				}
			});
			ulList.add(deleteAssign);

			btnGroup.add(ulList);
			panel.add(btnGroup);
		}
		Button saveBtn = new Button("speichern");
		saveBtn.setStylePrimaryName("btn btn-success createBtn");
		saveBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				// Prüfen ob die Felder Bezeichnung und Beschreibung gefüllt
				if (nameTb.getValue().trim() == ""
						|| descTb.getValue().trim() == "") {
					ClientsideSettings
							.getLogger()
							.severe("Error: Element Formular - Bitte gebe eine Bezeichnung und eine Beschreibung ein!");
					currentShowcase
							.insert(new ErrorMsg(
									"<b>Error:</b> Bitte gebe eine Bezeichnung und eine Beschreibung ein!"),
									1);
				} else {
					element.setName(nameTb.getValue().trim());
					element.setDescription(descTb.getValue().trim());
					element.setMaterialDescription(matTb.getValue().trim());
					element.setLastUpdate(new Date());
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					if (newElement) {
						element.setCreationDate(new Date());
						if (element instanceof Product) {
							Product newProduct = (Product) element;
							newProduct.setSalesName(nameTb.getValue().trim());
							administration.createProduct(newProduct,
									new ElementSaveCallback());
						} else if (element instanceof Module) {
							administration.createModule((Module) element,
									new ElementSaveCallback());
						} else {
							administration.createElement(element,
									new ElementSaveCallback());
						}
					} else {

						if (element instanceof Product) {
							Product newProduct = (Product) element;
							newProduct.setSalesName(nameTb.getValue().trim());
							administration.editProduct(newProduct,
									new ProductSaveCallback());
						} else if (element instanceof Module) {
							administration.editModule((Module) element,
									new ModuleSaveCallback());
						} else {
							administration.editElement(element,
									new ElementSaveCallback());
						}
					}
				}
			}
		});
		panel.add(saveBtn);
		if (!newElement) {
			Button assignBtn = new Button("zuordnen");
			assignBtn.setStylePrimaryName("btn btn-success createBtn");
			assignBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					RootPanel.get("main").clear();
					RootPanel.get("main").add(
							new AssignPanel(new PartlistEntry(element, amount,
									superModule)));
				}
			});
			panel.add(assignBtn);
		}
		this.add(panel);
		// ACTION BUTTONS ENDE

	}

	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn ein Element
	 * gespeichert wird.
	 * 
	 * @author Thomas Burkart
	 */
	class ElementSaveCallback implements AsyncCallback<Element> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ElementSaveCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			currentShowcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Element result) {
			RootPanel.get("main").clear();
			RootPanel.get("main").add(new ElementForm(result, 1, null));
			if (result instanceof Product) {
				currentShowcase.insert(new SuccessMsg(
						"Enderzeugnis erfolgreich gespeichert!"), 1);

			} else if (result instanceof Module) {
				currentShowcase.insert(new SuccessMsg(
						"Baugruppe erfolgreich gespeichert!"), 1);

			} else {
				currentShowcase.insert(new SuccessMsg(
						"Bauteil erfolgreich gespeichert!"), 1);

			}
		}
	}

	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn eine Zuordnung
	 * gelöscht wird.
	 * 
	 * @author Thomas Burkart
	 */
	class DeleteAssignCallback implements AsyncCallback<Void> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public DeleteAssignCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			currentShowcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Void result) {
			currentShowcase.insert(new SuccessMsg(
					"Zuordnung zu Baugruppe erfolgreich gelöscht!"), 1);
		}
	}

	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn ein Element
	 * gespeichert wird.
	 * 
	 * @author Thomas Burkart
	 */
	class ModuleSaveCallback implements AsyncCallback<Module> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ModuleSaveCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			currentShowcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Module result) {
			RootPanel.get("main").clear();
			RootPanel.get("main").add(new ElementForm(result, 1, null));
			currentShowcase.insert(new SuccessMsg(
					"Baugruppe erfolgreich gespeichert!"), 1);
		}
	}

	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn ein Element
	 * gespeichert wird.
	 * 
	 * @author Thomas Burkart
	 */
	class ProductSaveCallback implements AsyncCallback<Product> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ProductSaveCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			currentShowcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Product result) {
			RootPanel.get("main").clear();
			RootPanel.get("main").add(new ElementForm(result, 1, null));
			currentShowcase.insert(new SuccessMsg(
					"Enderzeugnis erfolgreich gespeichert!"), 1);
		}
	}

	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn ein Element gelöscht
	 * wird.
	 * 
	 * @author Thomas Burkart
	 */
	class ElementDeleteCallback implements AsyncCallback<Void> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */
		private Showcase showcase = null;

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ElementDeleteCallback(Showcase c) {
			this.showcase = c;
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			showcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Void result) {
			showcase.insert(new SuccessMsg("Löschvorgang erfolgreich!"), 1);
			// Damit nach dem Löschvorgang nichts mehr mit dem Formular
			// angestellt werden kann,
			// werden die Buttons entfernt.
			if (RootPanel.get("actionBox") != null) {
				RootPanel.get("actionBox").getElement().removeFromParent();
			}
		}
	}
}
