package de.bplaced.mopfsoft.entitys;

import de.bplaced.mopfsoft.blocks.Block;

public abstract class ItemUser extends Entity {

	public ItemUser(int x, int y, Block[][] gamefield) {
		super(x, y, gamefield);
	}

	public abstract void use(int itemId);
}
