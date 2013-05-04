package de.bplaced.mopfsoft.entitys;

public abstract class ItemUser extends Entity {

	public ItemUser(int x, int y, int width, int height, int[][] gamefield) {
		super(x, y, width, height, gamefield);
	}

	public abstract void use(int itemId);
}
