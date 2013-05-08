package de.bplaced.mopfsoft.items;

public class Drill extends Tool{

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public int getEnergy() {
		return 0;
	}

	@Override
	public int getStrength() {
		return 1;
	}

	@Override
	public int drainEnergy(int amount) {
		return 0;
	}

	@Override
	public int getMaxEnergy() {
		return 0;
	}

	@Override
	public int addEnergy() {
		return 0;
	}

}
