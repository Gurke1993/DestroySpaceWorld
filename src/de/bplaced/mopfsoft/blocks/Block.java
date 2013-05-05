package de.bplaced.mopfsoft.blocks;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;

import de.bplaced.mopfsoft.items.Item;

public abstract class Block {
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private int y;
	private int x;
	
	private static final Map<Integer,String> blockMap = setupBlockMap();
	
	private static Map<Integer, String> setupBlockMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        result.put(0, "Air");
        result.put(1, "Stone");
        result.put(2, "Dirt");
        
        
        return Collections.unmodifiableMap(result);
    }
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public abstract int getStrength(Item item);
	
	public abstract int getDrop();
	
	public abstract Color getColor();
	
	public abstract int getBid();
	
	@SuppressWarnings("unchecked")
	public static Block getNewBlock(int x, int y, int bid) {
		if (!blockMap.containsKey(bid)) {
			System.out.println("[ERROR] Block is not registered in blockmap: "+bid);
			return new Air(x,y);
		}
		
		Block block = null;

		Object [] args = {x,y};
		@SuppressWarnings("rawtypes")
		Class[] argsClass = new Class[] { int.class, int.class };
		
		@SuppressWarnings("rawtypes")
		Constructor argsConstructor;
		
	    try {
	    	@SuppressWarnings("rawtypes")
			Class blockDef = Class.forName("de.bplaced.mopfsoft.blocks."+blockMap.get(bid));
	    	argsConstructor = blockDef.getConstructor(argsClass);

	    	block = (Block)argsConstructor.newInstance(args);
	      
	      return block;
	      
	    } catch (Exception e) {
	      System.out.println("Could not create new Block from id!!");
	    }
	    return null;
	  }
	
}
