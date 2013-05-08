package de.bplaced.mopfsoft.entitys;

import de.bplaced.mopfsoft.map.Map;

public abstract class ItemUser extends Entity {

	public ItemUser(int id, int x, int y, Map map) {
		super(id, x, y, map);
	}

	public abstract void use(int itemId);
}
