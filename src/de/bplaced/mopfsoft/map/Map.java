package de.bplaced.mopfsoft.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.Log;

import util.Util;

import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.entitys.Player;
import de.bplaced.mopfsoft.entitys.World;
import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.material.Air;
import de.bplaced.mopfsoft.material.Material;

public class Map {
	
	private int entityCounter = 0;
	private String mapName;
	private String mapDescription;
	protected Shape eMax;
	protected final List<Entity> entitys = new ArrayList<Entity>();
	protected java.util.Map<Shape,Material> environment = new HashMap<Shape,Material>();
	private File previewImagePath;
	private int gravity;
	private World world;
	

	private Map (BufferedReader reader) {
		
		// If reader equals null load default map
		if (reader == null) {
			Map.copyDefaultMap();
			try {
				reader = new BufferedReader(new FileReader(new File("maps/DefaultMap.map")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//Initialize Temp variables
		String mapNameTemp = null, mapDescriptionTemp = null;
		
		
		// Start loading the map
		try {
			
			//Read file information and process them
			String topLine;
			while((topLine = reader.readLine()) == null) {
			}
			eMax = new Rectangle(0,0,Integer.parseInt(topLine.split(";")[0]),Integer.parseInt(topLine.split(";")[1]));

			mapNameTemp = reader.readLine();
			mapDescriptionTemp = reader.readLine();
			
			gravity = Integer.parseInt(reader.readLine());
			
			//Skip empty line
			reader.readLine();
			
			//Read Entity data
			String line;
			String [] lineSplit;
			Entity entity;
			while (!(line = reader.readLine()).equalsIgnoreCase("#")){
				lineSplit = line.split(";")[0].split(",");
				entity = Entity.getNewEntity(getId(), Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), this);
				
				//ItemUser
				if (entity instanceof ItemUser && line.split(";").length > 1) {
					for (String iid: line.split(";")[1].split(",")) {
						((ItemUser) entity).addItem(Integer.parseInt(iid));
					}
				}
					
				//Add to entity list of the map
				this.entitys.add(entity);
			}
			
			//Read gamefield data
			this.environment = new HashMap<Shape,Material>();
			String shape, location;
			Polygon p;
			while ((line = reader.readLine())!= null) {
				shape = line.split(";")[1];
				location = line.split(";")[2];
				float[] points = new float[shape.split(",").length];
				for (int i = 0; i< shape.split(",").length; i++) {
					points[i] = Float.parseFloat(shape.split(",")[i]);
				}
				p =  new Polygon(points);
				p.setLocation(Float.parseFloat(location.split(",")[0]), Float.parseFloat(location.split(",")[1]));
				environment.put(p, Material.getNewMaterial(Integer.parseInt(line.split(";")[0])));
			}
			
			
			
			while ((line = reader.readLine())!= null) {
				updateBlocks(line.split(";")[1], Integer.parseInt(line.split(";")[0]));
			}
			
			//Close readers
			reader.close();
			
		} catch (Exception e) {
			Log.error("Could not load gamefield data!");
			e.printStackTrace();
		}
		
		this.mapDescription = mapDescriptionTemp;
		this.mapName = mapNameTemp;
		this.world = new World(-1, -1, -1, this);
	}
	
	public java.util.Map <Shape,Material> getEnvironment(){
		return environment;
	}
	public Map (String mapData) {
		this(new BufferedReader(new StringReader(mapData)));
	}
	
	public Map() throws FileNotFoundException {
		this(new File("maps/DefaultMap.map"));
	}
	
	
	public Map (File file) throws FileNotFoundException {
		this(new BufferedReader(new FileReader(file)));
		
		this.previewImagePath = new File(file.getPath().split("\\.")[0] + ".gif");
		
	}
	
	/** Returns the name of the map as specified in the .map file
	 * @return map name
	 */
	public String getMapName() {
		return this.mapName;
	}
	
	/** Returns the description of the map as specified in the .map file
	 * @return map description
	 */
	public String getMapDescription() {
		return this.mapDescription;
	}
	
	public void setMapName(String name) {
		this.mapName = name;
	}
	
	public void setMapDescription(String description) {
		this.mapDescription = description;
	}
	
	public Shape collidesWithEnvironment(Shape shape) {
		if (shape.getMinX()<0 || shape.getMaxX() >getWidth() || shape.getMinY()<0 || shape.getMaxY()>getHeight()) {
			return new Point(-1,-1);	
		}
		
		for (Shape enShape: environment.keySet()) {
			if (enShape.intersects(shape)) {
				return enShape;
			}
		}
		return null;
	}
	
	public void updateBlocks(Shape shape, Material material) {
		java.util.Map<Shape,Material> newEnvironment = new HashMap<Shape,Material>();
		for (Entry<Shape,Material> entry: environment.entrySet()) {
			for (Shape newObject: entry.getKey().subtract(shape)) {
				newEnvironment.put(newObject, entry.getValue());
			}
		}
		if (!(material instanceof Air))
			newEnvironment.put(shape, material);
		this.environment = newEnvironment;
	}
	
	public void updateBlocks(String shape, int newId) {
		float[] points = new float[shape.split(",").length];
		for (int i = 0; i< shape.split(",").length; i++) {
			points[i] = Float.parseFloat(shape.split(",")[i]);
		}
		updateBlocks(new Polygon(points),Material.getNewMaterial(newId));
	}

	/** Saves this map in its current state in a file
	 * @param path
	 */
	public void saveToFile(String path) {
		saveToFile(new File(path));
	}
	
	
	/** Saves this map in its current state in a file
	 * @param file
	 */
	public void saveToFile(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			
			//Write file header
			writer.write(eMax.getWidth()+";"+eMax.getHeight()+System.getProperty("line.separator"));
			writer.write(mapName+System.getProperty("line.separator"));
			writer.write(mapDescription+System.getProperty("line.separator"));
			writer.write(gravity);
			
			
			//Write Entity data
			for (Entity entity: getEntitys()) {
				writer.write(entity+System.getProperty("line.separator"));
			}
			
			
			//Write Environment
			for (Entry<Shape,Material> entry: environment.entrySet()) {
				writer.write(entry.getValue().getMid()+";"+Util.getArrayAsString(entry.getKey().getPoints(), ","));
			}
			
			writer.close();
			
		} catch (IOException e) {
			Log.error("Could not save this map in "+file.getPath()+"!",e);
		}
		
	}
	
	
	/** Copies the default map from the jar into the map folder
	 * 
	 */
	public static void copyDefaultMap() {
			try {
				File file = new File("maps/DefaultMap.map");
				File previewImageFile = new File(file.getPath().split("\\.")[0] + ".gif");
				
				new File("maps").mkdirs();
				
				Log.info("Creating new DefaultMap...");
				file.createNewFile();
				
				InputStream is = Map.class.getClass().getResourceAsStream(
						"/resources/other/DefaultMap.map");
				FileOutputStream os = new FileOutputStream(new File(
						"maps/DefaultMap.map"));
				
				for (int read = 0; (read = is.read()) != -1;) {
					os.write(read);
				}
				os.flush();
				os.close();
				
				System.out.println("Creating new DefaultMap preview...");
				is = Map.class.getResourceAsStream(
						"/resources/other/DefaultMap.gif");
				os = new FileOutputStream(previewImageFile);
				for (int read = 0; (read = is.read()) != -1;)
					os.write(read);
				os.flush();
				os.close();
				is.close();

			} catch (IOException e) {
				System.out.println("[ERROR] Could not create default map!!");
				e.printStackTrace();
			}
		}

	public List<Player> getPlayers() {
		List <Player> players= new ArrayList<Player>();
		
		for (Entity entity: entitys) {
			if (entity instanceof Player) {
				players.add((Player)entity);
			}
		}
		return players;
	}


	public List<Entity> getEntitys() {
		return this.entitys;
	}

	public File getPreviewImageFile() {
		return this.previewImagePath;
	}
	
	
	public Material getMaterial(int x, int y) {
		if (isInMap(x,y)) {
			for (Shape shape: environment.keySet()) {
				if (shape.contains(x, y)) {
					return environment.get(shape);
				}
			}
		}
		return Material.EMPTY;
	}
	
	public boolean isInMap(int x, int y){
		return (x>=0 && y>=0 && x<eMax.getWidth() && y<eMax.getHeight());
	}

	public int getGravity() {
		return this.gravity;
	}
	
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public World getWorld() {
		return this.world;
	}

	public int getId() {
		int id = entityCounter;
		entityCounter++;
		return id ;
	}

	public int getWidth() {
		return (int) eMax.getWidth();
	}
	
	public int getHeight() {
		return (int) eMax.getHeight();
	}
}
