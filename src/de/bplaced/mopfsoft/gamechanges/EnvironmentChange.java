package de.bplaced.mopfsoft.gamechanges;

import org.newdawn.slick.geom.Shape;

import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.map.Map;

public class EnvironmentChange extends GameChange{

	
	private final int bid;
	private final Shape shape;

	public EnvironmentChange(Shape shape, int bid, Entity issuer, Map map) {
		super(issuer);
		this.bid = bid;
		this.shape = shape;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public int getBid() {
		return bid;
	}
}
