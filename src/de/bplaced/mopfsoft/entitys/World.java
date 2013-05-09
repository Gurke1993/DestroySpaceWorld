package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.map.Map;

public class World extends Entity{

	public World(int id, int x, int y, Map map) {
		super(id, x, y, map);
	}

	@Override
	public int getHeight() {
		return -1;
	}

	@Override
	public int getWidth() {
		return -1;
	}

	@Override
	public int getEid() {
		return 0;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public void resolveEntityCollisions() {
	}

	@Override
	public int getSpeed() {
		return 0;
	}

	@Override
	protected void prepareImages() {
	}

}
