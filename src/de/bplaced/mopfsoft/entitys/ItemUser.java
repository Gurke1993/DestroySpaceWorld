package de.bplaced.mopfsoft.entitys;

import de.bplaced.mopfsoft.blocks.Block;

public abstract class ItemUser extends Entity {

	public ItemUser(int id, int x, int y, Block[][] gamefield) {
		super(id, x, y, gamefield);
	}

	public abstract void use(int itemId);
}
