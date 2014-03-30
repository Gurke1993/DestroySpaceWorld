package de.bplaced.mopfsoft.material;

import org.newdawn.slick.Color;

import de.bplaced.mopfsoft.items.Item;

public class Air extends Material{

	private static Color color;


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
		return Air.color;
	}

	@Override
	public int getMid() {
		return 0;
	}

	public static void setColor(Color color) {
		Air.color = color;
	}

}
