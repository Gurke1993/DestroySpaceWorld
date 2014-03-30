package de.bplaced.mopfsoft.items;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.gamechanges.GameChange;
public abstract class Item {
	
	protected ItemUser owner;
	protected de.bplaced.mopfsoft.map.Map map;

	public Item (ItemUser owner, de.bplaced.mopfsoft.map.Map map) {
		this.map = map;
		this.owner = owner;
	}
	
	public ItemUser getOwner() {
		return this.owner;
	}
	
	
	
	
	
	private static final Map<Integer,String> itemMap = setupItemMap();
	
	private static Map<Integer, String> setupItemMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        
        result.put(1, "Drill");
        
        
        return Collections.unmodifiableMap(result);
	}
	
	/** Returns a new entity of the given id
	 * @param args
	 * @param gamefield
	 * @return
	 */
//	public static Item getNewItem(String[] args, ItemUser owner, de.bplaced.mopfsoft.map.Map map) {
//		return getNewItem(Integer.parseInt(args[0]), owner ,map);
//	}
	
	@SuppressWarnings("unchecked")
	public static Item getNewItem(int iid, ItemUser owner, de.bplaced.mopfsoft.map.Map map) {
		Item item = null;

		Object [] args = {owner,map};
		@SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[] { ItemUser.class, de.bplaced.mopfsoft.map.Map.class };
		
		@SuppressWarnings("rawtypes")
		Constructor argsConstructor;
		
	    try {
	    	@SuppressWarnings("rawtypes")
			Class blockDef = Class.forName("de.bplaced.mopfsoft.items."+itemMap.get(iid));
	    	argsConstructor = blockDef.getConstructor(argsClass);
	    	item = (Item)argsConstructor.newInstance(args);
	      
	      return item;
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	      System.out.println("Could not create new Item from iid!!"+iid+" "+owner);
	    }
	    return null;
	  }
	
	
	

	public abstract int getId();
	
	public abstract int getStrength();

	public abstract List<GameChange> use (ItemUser user);
	
	@Override
	public String toString() {
		return this.getId()+"";
	}
	
}
