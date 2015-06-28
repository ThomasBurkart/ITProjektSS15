package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * Klasse SearchResult ist ein Showcase für eine Suchanfrage über die
 * Suchleiste. Die Klasse startet bei der Instanziierung eine asynchrone Suche
 * und erzeugt ein Baum mit den gefundenen Ergebnissen.
 * 
 * @author Thomas Burkart
 *
 */
public class SearchResult extends Showcase {

	private String headlineText;
	private String headlineTextStyle;
	private String searchWord;
	private int searchId;
	private boolean onlyModules;
	private boolean allProducts;
	private boolean searchById;

	private PartlistEntry selectedEntry;
	private MultiSelectionModel<PartlistEntry> selectionModel;
	private boolean disableLoadElementForm;

	/**
	 * Standard-Konstruktor der Klasse SearchResult. Wird beim ersten Start der
	 * Anwendung verwendet, um alle Produkte in der Navigator-Hälfte anzuzeigen.
	 */
	public SearchResult() {
		// Text der angezeigt wird, wenn noch keine Suche gestartet wurde.
		this.headlineText = "Bitte starten Sie eine Suche!";

		// Style-Klasse für Titel der Suche
		this.headlineTextStyle = "resultInfo";

		// alle Enderzeugnisse anzeigen
		this.allProducts = true;

		// TODO: Erstes Suchergebnis sollen immer alle Endprodukte sein.
	}

	public SearchResult(String searchWord, boolean onlyModules) {
		this.searchWord = searchWord;
		
		this.onlyModules = onlyModules;

		// Text der angezeigt wird, wenn ein Suche gestartet wurde.
		this.headlineText = "Suchergebnisse für '" + searchWord + "'";

		// Style-Klasse für Titel der Suche
		this.headlineTextStyle = "resultInfo";

		this.allProducts = false;
	}

	public SearchResult(String searchWord, int id) {
		this.searchWord = searchWord;
		this.searchId = id;
		// Text der angezeigt wird, wenn ein Suche gestartet wurde.
		this.headlineText = "Suchergebnisse für '" + searchWord + "'";

		// Style-Klasse für Titel der Suche
		this.headlineTextStyle = "resultInfo";

		this.searchById = true;

		this.allProducts = false;
		
		this.onlyModules = false;
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
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();
		if (this.allProducts) {
			// Suche nach allen Endprodukten für Start-Ansicht
			administration.getAllProducts(new SearchAllProductsCallback(this));
		} else if (this.searchById) {
			administration.findElementById(this.searchId,
					new SearchElementCallback(this));
		} else if (this.onlyModules) {
			administration.findModulesByName(searchWord,
					new SearchElementCallback(this));
		} else {
			administration.findElementsByName(this.searchWord,
					new SearchElementCallback(this));

		}
	}

	public PartlistEntry getSelectedEntry() {
		return selectedEntry;
	}

	private void setSelectedEntry(PartlistEntry e) {
		selectedEntry = e;
	}

	public void disableLoadElementForm() {
		disableLoadElementForm = true;
	}

	public void enableLoadElementForm() {
		disableLoadElementForm = false;
	}

	private void setSelectionModel(MultiSelectionModel<PartlistEntry> msm) {
		selectionModel = msm;
	}

	public MultiSelectionModel<PartlistEntry> getSelectionModel() {
		return selectionModel;
	}

	class SearchElementCallback implements AsyncCallback<Partlist> {
		private Showcase showcase = null;

		public SearchElementCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			showcase.add(new ErrorMsg("<b>Error:</b> " + caught.getMessage()));
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Partlist result) {
			if (result != null) {
				if (result.isEmpty()) {
					showcase.add(new InfoMsg(
							"<b>Die Suche ergab leider kein Ergebnis!</b> Bitte probieren Sie es erneut."));
				} else {
					final MultiSelectionModel<PartlistEntry> selectionModel = new MultiSelectionModel<PartlistEntry>();
					selectionModel
							.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
								public void onSelectionChange(
										SelectionChangeEvent event) {

									List<PartlistEntry> selected = new ArrayList<PartlistEntry>(
											selectionModel.getSelectedSet());

									setSelectedEntry(selected.get(0));

									// createForm(selected.get(0));
									if (!disableLoadElementForm) {
										RootPanel.get("main").clear();
										RootPanel.get("main")
												.add(new ElementForm(selected
														.get(0)));
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

					showcase.add(dynamicTreeWrapper);
				}
			} else {
				// Fehler ausgeben und ins Log schreiben
				showcase.add(new ErrorMsg("<b>Error:</b> result == null"));
				ClientsideSettings.getLogger().severe("Error: result == null");
			}
		}

	}

	class SearchAllProductsCallback implements AsyncCallback<Vector<Product>> {
		private Showcase showcase = null;

		public SearchAllProductsCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			showcase.add(new ErrorMsg("<b>Error:</b> " + caught.getMessage()));
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Product> result) {
			if (result != null) {
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
									RootPanel.get("main").add(
											new ElementForm(selected.get(0)));
								}
							}
						});
				Partlist result2 = new Partlist();
				for (Product p : result) {
					result2.add(p, 1);
				}
				setSelectionModel(selectionModel);
				SearchTreeModel model = new SearchTreeModel(result2,
						selectionModel);

				CellTree cellTree = new CellTree(model, null);

				cellTree.setAnimationEnabled(true);
				ScrollPanel dynamicTreeWrapper = new ScrollPanel(cellTree);
				dynamicTreeWrapper.getElement().setId("searchPane");
				dynamicTreeWrapper
						.setStylePrimaryName("tree col-md-11 col-sm-11 col-xs-11");

				showcase.add(dynamicTreeWrapper);
			} else {
				// Fehler ausgeben und ins Log schreiben
				showcase.add(new ErrorMsg("<b>Error:</b> result == null"));
				ClientsideSettings.getLogger().severe("Error: result == null");
			}
		}
	}
}
