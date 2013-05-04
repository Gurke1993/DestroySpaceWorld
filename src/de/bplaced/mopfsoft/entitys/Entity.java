package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;

public abstract class Entity {
	
	private int xOld;
	private int yOld;
	private int y;
	private int x;
	private final int width;
	private final int height;
	@SuppressWarnings("unused")
	private final int[][] gamefield;
	
	public Entity(int x, int y, int width, int height, int[][] gamefield) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gamefield = gamefield;
		
		this.xOld = x;
		this.yOld = y;
	}


	
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
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
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
