package de.bplaced.mopfsoft.entitys;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.map.Direction;

public abstract class Entity {
	
	private static final Map<Integer,String> entityMap = setupEntityMap();
	private static final Map<String, Direction> DIRECTION_MAP = setupDirectionMap();
	private final int id;
	private int xOld;
	private int yOld;
	private int y;
	private int x;
	protected int verSpeed = 0;
	private final de.bplaced.mopfsoft.map.Map map;
	
	public Entity(int id, int x, int y, de.bplaced.mopfsoft.map.Map map) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.map = map;
		
		this.xOld = x;
		this.yOld = y;
	}
	
	private static Map<String, Direction> setupDirectionMap() {
		Map<String, Direction> result = new HashMap<String, Direction>();
        
        
        result.put("left", new Direction(-1,0));
        result.put("right", new Direction(1,0));
        result.put("up", new Direction(0,-1));
        result.put("down", new Direction(0,1));
		return result;
	}

	private static Map<Integer, String> setupEntityMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        
        result.put(1, "Player");
        
        
        return Collections.unmodifiableMap(result);
	}

	@Override
	public String toString() {
		return getId()+","+getEid()+","+x+","+y;
	}


	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getEid();
	public abstract void prepareImage();
	
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
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void move(int x, int y) {
		this.x += x*getSpeed();
		this.y += y*getSpeed();
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
	
	public boolean hasMoved() {
		return (xOld != x || yOld != y);
	}
	

	public void setInitialPosition() {
		this.xOld = x;
		this.yOld = y;
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
			
		if (map.getBlock(x, y).getBid() != 0) {
			return true;
		}
		if (map.getBlock(x, y+getHeight()-1).getBid() != 0) {
			this.verSpeed = Math.min(0,verSpeed);
			return true;
		}
		if (map.getBlock(x+getWidth()-1, y).getBid() != 0) {
			return true;
		}
		if (map.getBlock(x+getWidth()-1, y+getHeight()-1).getBid() != 0) {
			this.verSpeed = Math.min(0,verSpeed);
			return true;
		}
		
		return false;
	}
	
	
	/** Returns a new entity of the given id
	 * @param args
	 * @param gamefield
	 * @return
	 */
	public static Entity getNewEntity(String[] args, de.bplaced.mopfsoft.map.Map map) {
		return getNewEntity(Integer.parseInt(args[0]),Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]),map);
	}
	
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
		if (map.getBlock(x+getWidth()-1, y+getHeight()).getBid() != 0) {
			this.verSpeed = Math.min(0,verSpeed);
			return true;
		}
		if (map.getBlock(x, y+getHeight()).getBid() != 0) {
			this.verSpeed = Math.min(0,verSpeed);
			return true;
		}
		return false;
	}
}
