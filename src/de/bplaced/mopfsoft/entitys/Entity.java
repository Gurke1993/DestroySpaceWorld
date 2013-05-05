package de.bplaced.mopfsoft.entitys;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.blocks.Block;

public abstract class Entity {
	
	private static final Map<Integer,String> entityMap = setupEntityMap();
	private int xOld;
	private int yOld;
	private int y;
	private int x;
	private final Block[][] gamefield;
	
	public Entity(int x, int y, Block[][] gamefield) {
		this.x = x;
		this.y = y;
		this.gamefield = gamefield;
		
		this.xOld = x;
		this.yOld = y;
	}
	
	private static Map<Integer, String> setupEntityMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        
        result.put(1, "Player");
        
        
        return Collections.unmodifiableMap(result);
	}

	@Override
	public String toString() {
		return getId()+":"+x+":"+y;
	}


	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getId();
	
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
	
	/** Checks for potential world collisions and tries to resolve them.
	 * 
	 */
	public void resolveWorldCollisions() {
		if (isCollidingWithWorld()) {
			//TODO Move torwards intial if possible!!
			moveToInitialPosition();
		}
	}

	/** Resets the entities position to the last known
	 * 
	 */
	private void moveToInitialPosition() {
		this.x = xOld;
		this.y = yOld;
	}

	/** Checks for potential collisions with the gameworld
	 * @return
	 */
	private boolean isCollidingWithWorld() {
		
		if (gamefield[x][y].getId() != 0) {
			return true;
		}
		if (gamefield[x][y+getHeight()].getId() != 0) {
			return true;
		}
		if (gamefield[x+getWidth()][y].getId() != 0) {
			return true;
		}
		if (gamefield[x+getWidth()][y+getHeight()].getId() != 0) {
			return true;
		}
		
		return false;
	}
	
	
	/** Returns a new entity of the given id
	 * @param args
	 * @param gamefield
	 * @return
	 */
	public static Entity getNewEntity(String[] args, Block[][] gamefield) {
		return getNewEntity(Integer.parseInt(args[0]), Integer.parseInt(args[1]), gamefield, Integer.parseInt(args[2]));
	}
	
	@SuppressWarnings("unchecked")
	public static Entity getNewEntity(int x, int y, Block[][] gamefield, int id) {
		Entity entity = null;

		Object [] args = {x,y,gamefield};
		@SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[] { int.class, int.class, Block[][].class };
		
		@SuppressWarnings("rawtypes")
		Constructor argsConstructor;
		
	    try {
	    	@SuppressWarnings("rawtypes")
			Class blockDef = Class.forName("de.bplaced.mopfsoft.entitys."+entityMap.get(id));
	    	argsConstructor = blockDef.getConstructor(argsClass);
	    	entity = (Entity)argsConstructor.newInstance(args);
	      
	      return entity;
	      
	    } catch (Exception e) {
	      System.out.println("Could not create new Block from id!!");
	    }
	    return null;
	  }
}
