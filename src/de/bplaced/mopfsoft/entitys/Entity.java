package de.bplaced.mopfsoft.entitys;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


import de.bplaced.mopfsoft.map.Direction;

public abstract class Entity {
	
	private static final Map<Integer,String> entityMap = setupEntityMap();
	private static final Map<String, Direction> DIRECTION_MAP = setupDirectionMap();
	private final int id;
	private int xOld;
	private int yOld;
	protected int y;
	protected int x;
	protected int verSpeed = 0;
	protected final de.bplaced.mopfsoft.map.Map map;
	private Direction direction = Direction.LEFT;
	private Direction directionOld = Direction.LEFT;
	
	public Entity(int id, int x, int y, de.bplaced.mopfsoft.map.Map map) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.map = map;
		
		this.xOld = x;
		this.yOld = y;
	}
	
	public Entity(int id, int x, int y, de.bplaced.mopfsoft.map.Map map, Direction direction) {
		this(id,x,y,map);
		this.direction = direction;
		this.directionOld = direction;
	}
	
	private static Map<String, Direction> setupDirectionMap() {
		Map<String, Direction> result = new HashMap<String, Direction>();
        
        
        result.put("left", Direction.LEFT);
        result.put("right", Direction.RIGHT);
        result.put("up", Direction.UP);
        result.put("down", Direction.DOWN);
		return result;
	}

	private static Map<Integer, String> setupEntityMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        
        result.put(1, "Player");
        
        
        return Collections.unmodifiableMap(result);
	}

	@Override
	public String toString() {
		return getId()+","+getEid()+","+x+","+y+","+getDirection();
	}


	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getEid();
	protected abstract void prepareImages();
	
	public abstract Image getImage();
	
	public abstract void resolveEntityCollisions();
	
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
	public void set(int x, int y, String direction) {
		this.x = x;
		this.y = y;
		this.direction = DIRECTION_MAP.get(direction);
	}
	private void updateDirection() {
		//TODO add up and down!
		if (!hasMoved()) return;
		if (x == xOld) {
			if (y < yOld)
				direction = Direction.UP;
			else
				direction = Direction.DOWN;
		} else {
			if (x < xOld)
				direction = Direction.LEFT;
			else
				direction = Direction.RIGHT;
		}
	}

	public void move(int x, int y) {
		this.x += x*getSpeed();
		this.y += y*getSpeed();
		updateDirection();
	}
	
	public abstract int getSpeed();

	public void move(String direction) {
		move(DIRECTION_MAP.get(direction));
	}
	
	private void move(Direction direction) {
		move(direction.getX(), direction.getY());
	}

	public int getId() {
		return this.id;
	}

	/**
	 * Returns true if the player has a new Position (compared to initial
	 * Position) or if the Player has changed his direction
	 * 
	 * @return
	 */
	public boolean hasMoved() {
		return (xOld != x || yOld != y || directionOld.compareTo(direction) != 0);
	}
	

	public void setInitialPosition() {
		this.xOld = x;
		this.yOld = y;
		this.directionOld = direction;
	}
	
	/** Checks for potential world collisions and tries to resolve them.
	 * 
	 */
	public void resolveWorldCollisions() {
		while (isCollidingWithWorld()) {
			if (!(xOld==x)) x += (xOld-x)/Math.abs(xOld-x);
			if (!(yOld==y)) y += (yOld-y)/Math.abs(yOld-y);
		
		}
	}

	/** Resets the entities position to the last known
	 * 
	 */
	@SuppressWarnings("unused")
	private void moveToInitialPosition() {
		this.x = xOld;
		this.y = yOld;
	}

	/** Checks for potential collisions with the gameworld
	 * @return
	 */
	private boolean isCollidingWithWorld() {
		
		
		if (x<0 || x >map.getWidth()-getWidth() || y<0 || y>map.getHeight()-getHeight()) {
			return true;
			
		}
		

		Shape entityShape = new Rectangle(x,y, getWidth(), getHeight());
		Shape collidingShape = map.collidesWithEnvironment(entityShape);
		
		if (collidingShape == null)
			return false;
			
		if (collidingShape.includes(x, y+getHeight()-1) || collidingShape.includes(x+getWidth()-1, y+getHeight()-1))
			this.verSpeed = Math.min(0,verSpeed);
		return true;
	}
	
	
	/** Returns a new entity of the given id
	 * @param args
	 * @param gamefield
	 * @return
//	 */
//	public static Entity getNewEntity(String[] args, de.bplaced.mopfsoft.map.Map map) {
//		return getNewEntity(Integer.parseInt(args[0]),Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]),map);
//	}
	
	@SuppressWarnings("unchecked")
	public static Entity getNewEntity(int id, int eid, int x, int y, de.bplaced.mopfsoft.map.Map map) {
		Entity entity = null;

		Object [] args = {id,x,y,map};
		@SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[] { int.class, int.class, int.class, de.bplaced.mopfsoft.map.Map.class };
		
		@SuppressWarnings("rawtypes")
		Constructor argsConstructor;
		
	    try {
	    	@SuppressWarnings("rawtypes")
			Class blockDef = Class.forName("de.bplaced.mopfsoft.entitys."+entityMap.get(eid));
	    	argsConstructor = blockDef.getConstructor(argsClass);
	    	entity = (Entity)argsConstructor.newInstance(args);
	      
	      return entity;
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	      System.out.println("Could not create new Entity from eid!!"+id+" "+eid+" "+x+" "+y);
	    }
	    return null;
	  }

	public void applyGravity() {
		if (!isStanding()) {
		this.verSpeed +=map.getGravity();
		this.move(0, verSpeed);
		}
	}

	protected boolean isStanding() {
		Shape entityShape = new Rectangle(x,y, getWidth(), getHeight());
		Shape collidingShape = map.collidesWithEnvironment(entityShape);
		
		if (collidingShape == null) 
			return false;
		if (y+getHeight()>map.getHeight() || collidingShape.includes(x, y+getHeight()-1) || collidingShape.includes(x+getWidth()-1, y+getHeight()-1)) {
			this.verSpeed = Math.min(0,verSpeed);
			return true;
		}
		return false;
		
	}
	
	public Direction getDirection() {
		return this.direction;
	}

	public de.bplaced.mopfsoft.map.Map getMap() {
		return this.map;
	}
}
