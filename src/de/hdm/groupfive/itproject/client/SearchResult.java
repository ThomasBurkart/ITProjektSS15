package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

/**
 * Klasse SearchResult ist ein Showcase fÃ¼r eine Suchanfrage Ã¼ber die
 * Suchleiste. Die Klasse startet bei der Instanziierung eine asynchrone Suche
 * und erzeugt ein Baum mit den gefundenen Ergebnissen.
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 *
 */
public class SearchResult extends Showcase {

	/**
	 * Ãœberschrift des Showcase (graue Ãœberschrift)
	 */
	private String headlineText;
	/**
	 * StyleSheet fÃ¼r die Ãœberschrift des Showcase
	 */
	private String headlineTextStyle;
	/**
	 * eingegebenes Wort des Users, nach diesem Wort wird gesucht
	 */
	private String searchWord;
	/**
	 * interne suche der id
	 */
	private int searchId;
	/**
	 * Suche soll nur in Modulen stattfinden
	 */
	private boolean onlyModules;
	/**
	 * Suche nach allen Produkten 
	 */
	private boolean allProducts;
	/**
	 * Interne Suche nach id
	 */
	private boolean searchById;
	/**
	 * ausgewählter Eintrag
	 */
	private PartlistEntry selectedEntry;
	/**
	 * Mehrauswahlmodel eines Stücklisteneintrags
	 */
	private MultiSelectionModel<PartlistEntry> selectionModel;
	/**
	 * Deaktivieren von Laden des Elements
	 */
	private boolean disableLoadElementForm;
	/**
	 * Überprüfen ob es sich um eine Report Generator handelt
	 */
	private boolean isReportGen;
	/**
	 * Standard-Konstruktor der Klasse SearchResult. Wird beim ersten Start der
	 * Anwendung verwendet, um alle Produkte in der Navigator-HÃ¤lfte anzuzeigen.
	 */
	public SearchResult() {
		// Text der angezeigt wird, wenn noch keine Suche gestartet wurde.
		this.headlineText = "Bitte starten Sie eine Suche!";

		// Style-Klasse fÃ¼r Titel der Suche
		this.headlineTextStyle = "resultInfo";

		// alle Enderzeugnisse anzeigen
		this.allProducts = true;

		this.isReportGen = false;
	}

	/**
	 * Methode zur Textausgabe der Suche
	 * @param searchWord
	 * 		Wort als String, welches gesucht werden soll
	 * @param onlyModules
	 * 			überprüft ob es sich um eine Suche nur von Modulen handelt
	 */
	public SearchResult(String searchWord, boolean onlyModules) {
		this.searchWord = searchWord;

		this.onlyModules = onlyModules;

		// Text der angezeigt wird, wenn ein Suche gestartet wurde.
		this.headlineText = "Suchergebnisse fÃ¼r '" + searchWord + "'";

		// Style-Klasse fÃ¼r Titel der Suche
		this.headlineTextStyle = "resultInfo";

		this.allProducts = false;

		this.isReportGen = false;
	}
	
	/**
	 * intern wird eine Id vergeben mit der Suche
	 * @param searchWord
	 * 		Wort als String, welches gesucht werden soll
	 * @param id
	 * 		Suche mit id
	 */
	public SearchResult(String searchWord, int id) {
		this.searchWord = searchWord;

		this.searchId = id;
		// Text der angezeigt wird, wenn ein Suche gestartet wurde.
		this.headlineText = "Suchergebnisse fÃ¼r '" + searchWord + "'";

		// Style-Klasse fÃ¼r Titel der Suche
		this.headlineTextStyle = "resultInfo";

		this.searchById = true;

		this.allProducts = false;

		this.isReportGen = false;

		this.onlyModules = false;
	}

	@Override
	/**
	   * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	protected String getHeadlineText() {
		return this.headlineText;
	}

	@Override
	/**
	   * Jeder Showcase besitzt eine einleitende Ãœberschrift und dazugehÃ¶renden StyleSheet, 
	   * der durch diese Methode zu erstellen ist.
	   */
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	@Override
	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 *
	 * Aufbau des Assign-Panels (ZuordnungsflÃ¤che)
	 */
	protected void run() {
		HTML preloader = new HTML("<img src=\"img/preloader.gif\" border=\"0\" />");
		preloader.setStylePrimaryName("preloader");
		preloader.getElement().setId("preloader");
		this.add(preloader);
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();
		if (this.allProducts) {
			// Suche nach allen Endprodukten fÃ¼r Start-Ansicht
			administration.getAllProducts(new SearchAllProductsCallback(this));
			ClientsideSettings.getLogger().info(
					"Suche nach allen Produkten gestartet!");
		} else if (this.searchById) {
			administration.findElementById(this.searchId, false, false,
					new SearchElementCallback(this));

			ClientsideSettings.getLogger().info(
					"Suche nach Id '" + this.searchId + "' gestartet!");
		} else if (this.onlyModules) {
			administration.findModulesByName(searchWord,
					new SearchElementCallback(this));
			ClientsideSettings.getLogger().info(
					"Suche nach '" + this.searchWord
							+ "' in Modulen (only) gestartet!");
		} else {
			administration.findElementsByName(this.searchWord,
					new SearchElementCallback(this));

			ClientsideSettings.getLogger().info(
					"Suche nach '" + this.searchWord
							+ "' in allen Elementen gestartet!");
		}

	}
	/**
	 * Methode, gibt den ausgewählten Eintrag bzw. das Element wieder
	 * @return
	 * 	ausgewählter Eintrag
	 */
	public PartlistEntry getSelectedEntry() {
		return selectedEntry;
	}

	/**
	 * Eintrag wird gesetzt
	 * @param e
	 * 		Eintrag vom Typ PartlistEntry
	 */
	private void setSelectedEntry(PartlistEntry e) {
		selectedEntry = e;
	}

	/**
	 * Setzt den wert von <code>disableloadelementform</code> auf <code>true</code>, damit
	 * wird das Laden des Elementes deaktiviert
	 */
	public void disableLoadElementForm() {
		disableLoadElementForm = true;
	}

	/**
	 * Setzt den wert von <code>disableloadelementform</code> auf <code>false>, damit
	 * wird das Laden des Elementes aktiviert
	 */
	public void enableLoadElementForm() {
		disableLoadElementForm = false;
	}

	/**
	 * aktivieren des Report Generators
	 */
	public void enableReportGen() {
		isReportGen = true;
	}

	/**
	 * Ermöglicht das Setzen eines Auswahlmodels
	 * @param msm
	 * 		Auswahlmodel
	 */
	private void setSelectionModel(MultiSelectionModel<PartlistEntry> msm) {
		selectionModel = msm;
	}

	/**
	 * Gibt das Auswahlmodel zurück
	 * @return
	 * 	Auswahlmodel
	 */
	public MultiSelectionModel<PartlistEntry> getSelectionModel() {
		return selectionModel;
	}

	/**
	 * 
	 * Klasse um Fehlermeldung auszugeben falls die Suche fehlgeschlagen ist
	 *
	 */
	class SearchElementCallback implements AsyncCallback<Partlist> {
		private Showcase showcase = null;

		/**
		 * Suche eines Elements
		 * @param c
		 * 		showcase
		 */		
		public SearchElementCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		/**
		 * Im Fehlerfall wird eine Fehlermeldung ausgegeben
		 */
		public void onFailure(Throwable caught) {
			if (RootPanel.get("preloader") != null) {
				RootPanel.get("preloader").getElement().removeFromParent();
			}
			showcase.add(new ErrorMsg("<b>Error:</b> Suche fehlgeschlagen - "
					+ caught.getMessage()));
			ClientsideSettings.getLogger().severe(
					"Error: Suche fehlgeschlagen - " + caught.getMessage());
		}

		@Override
		/**
		 * Falls die Suche keine Ergebnis liefert, wird eine Fehlermeldung
		 * ausgegeben.
		 */
		public void onSuccess(Partlist result) {
			if (result != null) {
				if (result.isEmpty()) {
					if (RootPanel.get("preloader") != null) {
						RootPanel.get("preloader").getElement().removeFromParent();
					}
					ClientsideSettings.getLogger().info(
							"Suche - Die Suche ergab keine Ergebnisse.");
					showcase.add(new InfoMsg(
							"<b>Die Suche ergab leider kein Ergebnis!</b> Bitte probieren Sie es erneut."));
				} else {

					ClientsideSettings.getLogger().info(
							"Suche - Suchergebnisse werden geladen.");
					final MultiSelectionModel<PartlistEntry> selectionModel = new MultiSelectionModel<PartlistEntry>();
					selectionModel
							.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
								public void onSelectionChange(
										SelectionChangeEvent event) {

									List<PartlistEntry> selected = new ArrayList<PartlistEntry>(
											selectionModel.getSelectedSet());

									setSelectedEntry(selected.get(0));

									if (!disableLoadElementForm) {
										RootPanel.get("main").clear();
										if (!isReportGen) {
											RootPanel.get("main").add(
													new ElementForm(selected
															.get(0)));
										} else {
											RootPanel.get("main").add(
													new CalculateMaterial(
															selected.get(0)));
										}
									}
								}
							});
					setSelectionModel(selectionModel);
					SearchTreeModel model = new SearchTreeModel(result,
							selectionModel);

					CellTree cellTree = new CellTree(model, null);
					cellTree.setAnimationEnabled(true);
					ScrollPanel dynamicTreeWrapper = new ScrollPanel(cellTree);
					dynamicTreeWrapper.getElement().setId("searchPane");
					dynamicTreeWrapper
							.setStylePrimaryName("tree col-md-11 col-sm-11 col-xs-11");

					if (RootPanel.get("preloader") != null) {
						RootPanel.get("preloader").getElement().removeFromParent();
					}
					
					showcase.add(dynamicTreeWrapper);

					if (searchById) {
						for (PartlistEntry peById : result.getAllEntries()) {
							if (searchId == peById.getElement().getId()) {
								selectionModel.setSelected(peById, true);
								break;
							}
						}
					}
				}
			} else {
				if (RootPanel.get("preloader") != null) {
					RootPanel.get("preloader").getElement().removeFromParent();
				}
				// Fehler ausgeben und ins Log schreiben
				showcase.add(new ErrorMsg("<b>Error:</b> result == null"));
				ClientsideSettings.getLogger().severe("Error: result == null");
			}
		}

	}

	/**
	 * 
	 * Suche aller Produkte mit Fehlermeldung bei nicht erfolgreicher Suche
	 *
	 */
	class SearchAllProductsCallback implements AsyncCallback<Partlist> {
		private Showcase showcase = null;

		/**
		 * 	Suche aller Produkte
		 * @param c
		 * 		showcase im falle eines Produkts
		 */
		public SearchAllProductsCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		/**
		 * Fehlerausgabe bei erfolgloser Suche
		 */
		public void onFailure(Throwable caught) {
			if (RootPanel.get("preloader") != null) {
				RootPanel.get("preloader").getElement().removeFromParent();
			}
			showcase.add(new ErrorMsg("<b>Error:</b> Suche fehlgeschlagen - "
					+ caught.getMessage()));
			ClientsideSettings.getLogger().severe(
					"Error: Suche fehlgeschlagen - " + caught.getMessage());
		}

		@Override
		/**
		 * Suche war erfolgreich, nun wird eine Erfolgsmeldung ausgegeben
		 */
		public void onSuccess(Partlist result) {
			if (result != null) {
				ClientsideSettings.getLogger().info(
						"Suche - Suchergebnisse werden geladen.");

				final MultiSelectionModel<PartlistEntry> selectionModel = new MultiSelectionModel<PartlistEntry>();
				selectionModel
						.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
							public void onSelectionChange(
									SelectionChangeEvent event) {

								List<PartlistEntry> selected = new ArrayList<PartlistEntry>(
										selectionModel.getSelectedSet());
								setSelectedEntry(selected.get(0));

								if (!disableLoadElementForm) {
									RootPanel.get("main").clear();
									if (!isReportGen) {
										RootPanel.get("main").add(
												new ElementForm(selected
														.get(0)));
									} else {
										RootPanel.get("main").add(
												new CalculateMaterial(
														selected.get(0)));
									}
								}
							}
						});

				setSelectionModel(selectionModel);
				SearchTreeModel model = new SearchTreeModel(result,
						selectionModel);

				CellTree cellTree = new CellTree(model, null);

				cellTree.setAnimationEnabled(true);
				ScrollPanel dynamicTreeWrapper = new ScrollPanel(cellTree);
				dynamicTreeWrapper.getElement().setId("searchPane");
				dynamicTreeWrapper
						.setStylePrimaryName("tree col-md-11 col-sm-11 col-xs-11");
				if (RootPanel.get("preloader") != null) {
					RootPanel.get("preloader").getElement().removeFromParent();
				}
				showcase.add(dynamicTreeWrapper);
			} else {
				if (RootPanel.get("preloader") != null) {
					RootPanel.get("preloader").getElement().removeFromParent();
				}
				// Fehler ausgeben und ins Log schreiben
				showcase.add(new ErrorMsg("<b>Error:</b> result == null"));
				ClientsideSettings.getLogger().severe("Error: result == null");
			}
		}
	}
}
