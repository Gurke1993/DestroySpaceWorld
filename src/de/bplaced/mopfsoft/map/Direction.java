package de.bplaced.mopfsoft.map;

public class Direction implements Comparable<Direction>{

	private final int x;
	private final int y;

	public static final Direction LEFT = new Direction(-1,0);
	public static final Direction RIGHT = new Direction(1,0);
	public static final Direction UP = new Direction(0,-1);
	public static final Direction DOWN = new Direction(0,1);
	
	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Direction o) {
		if (o.getX() == this.getX() && o.getY() == this.getY())
		return 0;
		return 1;
	}
	
	@Override
	public String toString() {
		if (this.compareTo(LEFT) == 0) {
			return "left";
		} else
		if (this.compareTo(RIGHT) == 0) {
			return "right";
		} else
		if (this.compareTo(UP) == 0) {
			return "up";
		} else
		if (this.compareTo(DOWN) == 0) {
			return "down";
		}
		return "not_known_direction: "+x+" "+y;
	}
}
