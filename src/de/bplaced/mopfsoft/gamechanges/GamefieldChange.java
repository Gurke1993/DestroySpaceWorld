package de.bplaced.mopfsoft.gamechanges;

import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.map.Map;

public class GamefieldChange extends GameChange{

	
	private final int x;
	private final int y;
	private final int bid;

	public GamefieldChange(int x, int y, int bid, Entity issuer, Map map) {
		super(issuer);
		this.x = x;
		this.y = y;
		this.bid = bid;

		map.updateBlock(x,y,bid);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getBid() {
		return bid;
	}
	
	@Override
	public String toString() {
		return super.toString()+"type=gamefieldchange"+":x="+x+":y="+y+":bid="+bid;
	}
}
