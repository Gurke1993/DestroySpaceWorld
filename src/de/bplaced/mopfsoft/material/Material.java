package de.bplaced.mopfsoft.material;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.items.Item;

public abstract class Material {
	
	public Material() {
	}

	
	private static final Map<Integer,String> materialMap = setupBlockMap();
	
	public static final Material EMPTY = new Empty();
	
	private static Map<Integer, String> setupBlockMap() {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        result.put(-1, "Empty");
        result.put(0, "Air");
        result.put(1, "Stone");
        result.put(2, "Dirt");
        
        
        return Collections.unmodifiableMap(result);
    }
	
	public abstract int getStrength(Item item);
	
	public abstract int getDrop();
	
	public abstract Color getColor();
	
	public abstract int getMid();
	
	@SuppressWarnings("unchecked")
	public static Material getNewMaterial(int mid) {
		if (!materialMap.containsKey(mid)) {
			Log.error("Material is not registered in materialMap: "+mid);
			return new Air();
		}
		
		Material material = null;
		
		@SuppressWarnings("rawtypes")
		Constructor argsConstructor;
		
	    try {
	    	@SuppressWarnings("rawtypes")
			Class matDef = Class.forName("de.bplaced.mopfsoft.material."+materialMap.get(mid));
	    	argsConstructor = matDef.getConstructor();

	    	material = (Material)argsConstructor.newInstance();
	      
	      return material;
	      
	    } catch (Exception e) {
	     Log.error("Could not create new Material from id!!");
	    }
	    return null;
	  }
	
}
