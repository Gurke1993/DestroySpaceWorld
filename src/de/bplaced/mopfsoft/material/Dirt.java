package de.bplaced.mopfsoft.material;

import org.newdawn.slick.Color;

import de.bplaced.mopfsoft.items.Item;


public class Dirt extends Massive{

	public Dirt() {
	}

	@Override
	public int getStrength(Item item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDrop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getColor() {
		return new Color(88,0,0);
	}

	@Override
	public int getMid() {
		return 2;
	}

}
