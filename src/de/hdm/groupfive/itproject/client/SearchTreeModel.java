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
 * SearchTreeModel bietet ein TreeViewModel um die Ergebnisse der Suche in 
 * einem Baum anzuzeigen.
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class SearchTreeModel implements TreeViewModel {

	/**
	 * Das Model zum definieren der Knoten im Baum
	 */
	private final List<PartlistEntry> searchResult;

	/**
	 * Das SelectionModel ist über alle äußersten Knoten verteilt. Ein SelectionModel
	 * kann auch auch über alle Knoten im Baum verteit sein, 
	 * oder jeder Satz von SubKnoten kann seine eigene Instanz haben.
	 * Das gibt Flexibilität durch, welches man bestimmen kann,
	 * welche Knoten ausgewählt sind.
	 * 
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

			public void render(Context context, PartlistEntry value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			protected PartlistEntry getContainerElement(PartlistEntry parent) {
				// Return the first TR element in the table.
				return parent;
			}

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
	 * Auslesen der {@link NodeInfo} zur Bereitstellung der Children
	 *  der spezifischen Werte
	 */
	public <T> NodeInfo<?> getNodeInfo(T val) {
		if (val == null) {

			T value = val;
			// LEVEL 0.
			// We passed null as the root value. Return the composers.

			// Create a data provider that contains the list of composers.
			ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>(
					this.searchResult);

			// Create a cell to display a composer.
			Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						PartlistEntry value, SafeHtmlBuilder sb) {

					if (value != null) {
						sb.appendHtmlConstant("    ");
						sb.appendEscaped(value.getElement().getName());
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			// return new DefaultNodeInfo<Element>(dataProvider, cell);
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
				} else if (value.getElement() instanceof Module) {
					// LEVEL 1.
					// We want the children of the composer. Return the
					// playlists.
					ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>(
							((Module) value.getElement()).getPartlist()
									.getAllEntries());
					Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

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
					// return new DefaultNodeInfo<Element>(dataProvider, cell);
					return new DefaultNodeInfo<PartlistEntry>(dataProvider,
							cell, selectionModel, selectionManager, null);
				} else if (value.getElement() instanceof Element) {
					// LEVEL 2 - LEAF.
					// We want the children of the playlist. Return the songs.
					ListDataProvider<PartlistEntry> dataProvider = new ListDataProvider<PartlistEntry>();
					Cell<PartlistEntry> cell = new AbstractCell<PartlistEntry>() {

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

					// Use the shared selection model.
					// return new DefaultNodeInfo<Element>(dataProvider, cell);
					return new DefaultNodeInfo<PartlistEntry>(dataProvider,
							cell, selectionModel, selectionManager, null);
				}
			}
		}
		return null;
	}

	/**
	 * Überprüfen, ob die spezialisierten Werte die äußersten Knoten repräsentieren.
	 * Die äußersten Knoten können nicht geöffnet werden.
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
