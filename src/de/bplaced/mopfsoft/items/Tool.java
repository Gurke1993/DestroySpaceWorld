package de.bplaced.mopfsoft.items;

import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.map.Map;

public abstract class Tool extends Item implements EnergyUser{

	public Tool(ItemUser owner, Map map) {
		super(owner, map);
	}

}
