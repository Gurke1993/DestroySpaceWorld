package de.bplaced.mopfsoft.entitys;

import java.util.ArrayList;
import java.util.List;

import de.bplaced.mopfsoft.gamechanges.GameChange;
import de.bplaced.mopfsoft.items.Item;
import de.bplaced.mopfsoft.map.Map;

public abstract class ItemUser extends Entity {
	
	protected List <Item> items = new ArrayList<Item>();

	public ItemUser(int id, int x, int y, Map map) {
		super(id, x, y, map);
	}

	/** Calls the use method of the item at that position
	 * @param listId
	 * @return 
	 */
	public List<GameChange> useItem(int listId) {
		return items.get(listId).use(this);
	}
	
	/** Removes all occurrences of that item from the list 
	 * @param listId
	 */
	public void removeItem(int listId) {
		items.remove(listId);
	}
	
	/** Removes all occurrences of that item from the list
	 * @param item
	 */
	public void removeItem(Item item) {
		while (items.remove(item)) {
		}
		
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void addItem(int iid) {
		items.add(Item.getNewItem(iid, this, map));
	}
	
	@Override
	public String toString() {
		String itemString = "";
		int i=0;
		for (Item item: items) {
			if (i!=0) itemString += ",";
			itemString +=item;
			i++;
		}
		if (itemString.equals("")) 
		return super.toString();
		return super.toString()+";"+itemString;
	}

	public void setItems(String[] items) {
		for (String itemString: items) {
			addItem(Integer.parseInt(itemString));
		}
	}
}
