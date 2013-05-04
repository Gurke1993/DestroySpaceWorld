package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.blocks.Block;

public abstract class Entity {
	
	private int xOld;
	private int yOld;
	private int y;
	private int x;
	@SuppressWarnings("unused")
	private final Block[][] gamefield;
	
	public Entity(int x, int y, Block[][] gamefield) {
		this.x = x;
		this.y = y;
		this.gamefield = gamefield;
		
		this.xOld = x;
		this.yOld = y;
	}
	
	@Override
	public String toString() {
		return getId()+":"+x+":"+y;
	}


	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getId();
	public abstract Image getImage();
	
	public int getOldX() {
		return this.xOld;
	}
	public int getOldY() {
		return this.yOld;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public boolean hasMoved() {
		return (xOld == x && yOld == y);
	}
	

	public void setInitialPosition() {
		this.xOld = x;
		this.yOld = y;
	}
}
