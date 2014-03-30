package de.bplaced.mopfsoft.material;

import org.newdawn.slick.Color;

import de.bplaced.mopfsoft.items.Item;

public class Empty extends Material {


	@Override
	public int getStrength(Item item) {
		return -1;
	}

	@Override
	public int getDrop() {
		return -1;
	}

	@Override
	public Color getColor() {
		return Color.transparent;
	}

	@Override
	public int getMid() {
		return -1;
	}

}
