package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * Das Modell, dass die Knoten in der Baumstruktur definiert.
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class SearchTreeModel implements TreeViewModel {

	/**
	 * * Das Model zum definieren der Knoten im Baum.
	 */
	private final List<PartlistEntry> searchResult;

	/**
	 * Dieses Auswahl-Modell ist in allen Astknoten verteilt. Ein Auswahlmodell
	 * Kann auch auf allen Knoten im Baum geteilt werden, oder in jeder Gruppe eines Kindes
	 * Knoten k�nnen eine eigene Instanz haben. Dadurch wird es m�glich zu bestimmen, wie diese Knoten ausgew�hlt sind
	 */
	private final SelectionModel<PartlistEntry> selectionModel;

	private final Cell<PartlistEntry> elementCell;
	private final DefaultSelectionEventManager<PartlistEntry> selectionManager = DefaultSelectionEventManager
			.createDefaultManager();

	public SearchTreeModel(Partlist searchResult,
			final SelectionModel<PartlistEntry> selectionModel) {
		this.selectionModel = selectionModel;
		this.searchResult = searchResult.getAllEntries();
		List<HasCell<PartlistEntry, ?>> hasCells = new ArrayList<HasCell<PartlistEntry, ?>>();

		hasCells.add(new HasCell<PartlistEntry, PartlistEntry>() {

			private Cell cell = new TextCell();

			
			public Cell<PartlistEntry> getCell() {
				return cell;
			}

			public FieldUpdater<PartlistEntry, PartlistEntry> getFieldUpdater() {
				return null;
			}

			public PartlistEntry getValue(PartlistEntry object) {
				return object;
			}
		});
		elementCell = new CompositeCell<PartlistEntry>(hasCells) {
			@Override
			public void render(Context context, PartlistEntry value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			protected PartlistEntry getContainerElement(PartlistEntry parent) {
				// Gebe das erste TR element in der Tabelle zur�ck
				return parent;
			}

			@Override
			protected <X> void render(Context context, PartlistEntry value,
					SafeHtmlBuilder sb, HasCell<PartlistEntry, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");
			}
		};

	}

	/**
	 * 
	 * Hole den {@link NodeInfo} der das Kind des festgelegten Wertes bereitstellt
	 */
	public <T> NodeInfo<?> getNodeInfo(T val) {
		if (val == null) {

			T value = val;
			// LEVEL 0.
			// Wir haben 0 ausgeworfen als Stammwert. Gebe den Verfasser zur�ck

		
			ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>(
					this.searchResult);

			// Erzeugen einer Zelle um einen St�cklisteneintrag dazustellen
			Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						PartlistEntry value, SafeHtmlBuilder sb) {

					if (value != null) {
						sb.appendHtmlConstant("    ");
						sb.appendEscaped(value.getElement().getName());
					}
				}
			};

			// Gibt eine Knoten-Info, die den Datenprovider und die Zelle verbindet.
			// Gebe den neuen DefaultNodeInfo<Element>(dataProvider, cell); zur�ck.
			return new DefaultNodeInfo<PartlistEntry>(dataProvider, cell,
					selectionModel, selectionManager, null);
		} else {

			if (val instanceof PartlistEntry) {

				PartlistEntry value = (PartlistEntry) val;
				if (value.getElement() instanceof Product) {
					ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>(
							((Product) value.getElement()).getPartlist()
									.getAllEntries());
					Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

						@Override
						public void render(
								com.google.gwt.cell.client.Cell.Context context,
								PartlistEntry value, SafeHtmlBuilder sb) {
							if (value != null) {
								sb.appendHtmlConstant("    ");
								sb.appendEscaped(value.getElement().getName());
								sb.appendHtmlConstant("    ");
								sb.appendEscaped("[" + value.getAmount() + "]");
							}
						}
					};
					return new DefaultNodeInfo<PartlistEntry>(dataProvider,
							cell, selectionModel, selectionManager, null);
				} else	if (value.getElement() instanceof Module) {
					// LEVEL 1.
					// . Gebe alle Eintraege der Strukturst�ckliste zur�ck
					ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>(
							((Module) value.getElement()).getPartlist()
									.getAllEntries());
					Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

						@Override
						public void render(
								com.google.gwt.cell.client.Cell.Context context,
								PartlistEntry value, SafeHtmlBuilder sb) {
							if (value != null) {
								sb.appendHtmlConstant("    ");
								sb.appendEscaped(value.getElement().getName());
								sb.appendHtmlConstant("    ");
								sb.appendEscaped("[" + value.getAmount() + "]");
							}
						}
					};
					
					// gebe den neuen DefaultNodeInfo<Element>(dataProvider, cell); zur�ck
					return new DefaultNodeInfo<PartlistEntry>(dataProvider,
							cell, selectionModel, selectionManager, null);
				} else if (value.getElement() instanceof Element) {
					// LEVEL 2 - Blatt.
					// Ausgabe von Name und Anzahl der Elemente
					
					ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>();
					Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {
						@Override
						public void render(
								com.google.gwt.cell.client.Cell.Context context,
								PartlistEntry value, SafeHtmlBuilder sb) {
							// TODO Auto-generated method stub
							if (value != null) {
								sb.appendHtmlConstant("    ");
								sb.appendEscaped(value.getElement().getName());
								sb.appendHtmlConstant("    ");
								sb.appendEscaped("[" + value.getAmount() + "]");
							}
						}
					};

					dataProvider.getList().add(((PartlistEntry) value));

					// Nutzen des geteilten Auswahlmodells
					return new DefaultNodeInfo<PartlistEntry>(dataProvider,
							cell, selectionModel, selectionManager, null);
				}
			}
		}
		return null;
	}

	/**
	 * Pr�fen, ob der festgelegte Wert einen Blattknoten darstellt. Blattknoten kann man nicht �ffnen
	 * @param value
	 * 		Objekt Wert
	 * @return 
	 * 		Aussage ob es sich um ein Produkt oder ein Element handelt
	 */
	public boolean isLeaf(Object value) {
		boolean result = false;
		if (value instanceof PartlistEntry) {
			PartlistEntry pe = (PartlistEntry) value;
			if (pe.getElement() instanceof Module
					|| pe.getElement() instanceof Product) {
				result = false;
			} else if (pe.getElement() instanceof Element) {
				result = true;
			}
		}
		return result;
	}

}