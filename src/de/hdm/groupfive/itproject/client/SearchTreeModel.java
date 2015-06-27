package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import de.hdm.groupfive.itproject.shared.bo.Product;

public class SearchTreeModel implements TreeViewModel {

	/**
	 * The model that defines the nodes in the tree.
	 */

	private final List<Element> searchResult;

	/**
	 * This selection model is shared across all leaf nodes. A selection model
	 * can also be shared across all nodes in the tree, or each set of child
	 * nodes can have its own instance. This gives you flexibility to determine
	 * how nodes are selected.
	 */
	private final SelectionModel<Element> selectionModel;

	private final Cell<Element> elementCell;
	private final DefaultSelectionEventManager<Element> selectionManager = DefaultSelectionEventManager
			.createDefaultManager();

	public SearchTreeModel(List<Element> searchResult,
			final SelectionModel<Element> selectionModel) {
		this.selectionModel = selectionModel;
		this.searchResult = searchResult;
		List<HasCell<Element, ?>> hasCells = new ArrayList<HasCell<Element, ?>>();

		hasCells.add(new HasCell<Element, Element>() {

			private Cell cell = new TextCell();

			public Cell<Element> getCell() {
				return cell;
			}

			public FieldUpdater<Element, Element> getFieldUpdater() {
				return null;
			}

			public Element getValue(Element object) {
				return object;
			}
		});
		elementCell = new CompositeCell<Element>(hasCells) {
			@Override
			public void render(Context context, Element value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			protected Element getContainerElement(Element parent) {
				// Return the first TR element in the table.
				return parent;
			}

			@Override
			protected <X> void render(Context context, Element value,
					SafeHtmlBuilder sb, HasCell<Element, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");
			}
		};

	}

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified
	 * value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			// We passed null as the root value. Return the composers.

			// Create a data provider that contains the list of composers.
			ListDataProvider<Element> dataProvider = new ListDataProvider<Element>(
					this.searchResult);

			// Create a cell to display a composer.
			Cell<Element> cell = new AbstractCell<Element>() {

				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						Element value, SafeHtmlBuilder sb) {

					if (value != null) {
						sb.appendHtmlConstant("    ");
						sb.appendEscaped(value.getName());
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			// return new DefaultNodeInfo<Element>(dataProvider, cell);
			return new DefaultNodeInfo<Element>(dataProvider, cell,
					selectionModel, selectionManager, null);
		} else if (value instanceof Module) {
			// LEVEL 1.
			// We want the children of the composer. Return the playlists.
			ListDataProvider<Element> dataProvider = new ListDataProvider<Element>(
					((Module) value).getPartlist().getAllElements());
			Cell<Element> cell = new AbstractCell<Element>() {

				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						Element value, SafeHtmlBuilder sb) {
					// TODO Auto-generated method stub
					if (value != null) {
						sb.appendHtmlConstant("    ");
						sb.appendEscaped(value.getName());
					}
				}
			};
			// return new DefaultNodeInfo<Element>(dataProvider, cell);
			return new DefaultNodeInfo<Element>(dataProvider, cell,
					selectionModel, selectionManager, null);
		} else if (value instanceof Element) {
			// LEVEL 2 - LEAF.
			// We want the children of the playlist. Return the songs.
			ListDataProvider<Element> dataProvider = new ListDataProvider<Element>();
			Cell<Element> cell = new AbstractCell<Element>() {
				@Override
				public void render(
						com.google.gwt.cell.client.Cell.Context context,
						Element value, SafeHtmlBuilder sb) {
					// TODO Auto-generated method stub
					if (value != null) {
						sb.appendHtmlConstant("    ");
						sb.appendEscaped(value.getName());
					}
				}
			};

			dataProvider.getList().add(((Element) value));

			// Use the shared selection model.
			// return new DefaultNodeInfo<Element>(dataProvider, cell);
			return new DefaultNodeInfo<Element>(dataProvider, cell,
					selectionModel, selectionManager, null);
		}

		return null;
	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	public boolean isLeaf(Object value) {
		boolean result = false;
		if (value instanceof Module || value instanceof Product) {
			result = false;
		} else if (value instanceof Element) {
			result = true;
		}
		return result;
	}

//	public Vector<Element> getParentNodes() {
//		return getParentNodes(this.searchResult);
//	}
//	
//	public Vector<Element> getParentNodes(List<Element> list) {
//		Vector<Element> result = new Vector<Element>();
//		for (Element node : this.searchResult) {
//			if (node instanceof Module) {
//				if (this.selectionModel.isSelected(node)) {
//					result.add(node);
//					break;
//				} else {
//					
//					return getParentNodes(((Module)node).getPartlist().getAllElements());
//				}
//				
//			} else {
//				if (this.selectionModel.isSelected(node)) {
//					result.add(node);
//					break;
//				} else {
//					result.clear();
//				}
//			}
//		}
//		return result;
//	}

}
