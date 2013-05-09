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
import java.util.List;

import de.bplaced.mopfsoft.blocks.Block;
import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.entitys.Player;
import de.bplaced.mopfsoft.entitys.World;
import de.bplaced.mopfsoft.entitys.ItemUser;

public class Map {
	
	private int entityCounter = 0;
	protected final Block[][] gamefield;
	private final String mapName;
	private final String mapDescription;
	protected final List<Entity> entitys = new ArrayList<Entity>();
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
		Block[][] gamefieldTemp = null;
		
		
		// Start loading the map
		try {
			
			//Read file information and process them
			String topLine;
			while((topLine = reader.readLine()) == null) {
			}
			
			int xMax = Integer.parseInt(topLine.split(";")[0]);
			int yMax = Integer.parseInt(topLine.split(";")[1]);
			gamefieldTemp = new Block[xMax][yMax];
			
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
			int x;
			int y = 0;
			
			while ((line = reader.readLine())!= null) {
				x = 0;
				for (String id: line.split(";")) {
					gamefieldTemp[x][y] = Block.getNewBlock(x,y,Integer.parseInt(id));
					x++;
				}
				y++;
			}
			
			//Close readers
			reader.close();
			
		} catch (Exception e) {
			System.out.println("[ERROR] Could not load gamefield data!");
			e.printStackTrace();
		}
		
		this.mapDescription = mapDescriptionTemp;
		this.mapName = mapNameTemp;
		this.gamefield = gamefieldTemp;
		this.world = new World(-1, -1, -1, this);
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
	
	
	/** Changes the block at the specified coordinates to a new block of the id
	 * @param x
	 * @param y
	 * @param newId
	 */
	public void updateBlock(int x, int y, int newId) {
		updateBlock(x,y,Block.getNewBlock(x, y, newId));
	}
		
	/** Changes the block at the specified coordinates to the specified block
	 * @param x
	 * @param y
	 * @param newBlock
	 */
	public void updateBlock(int x, int y, Block newBlock) {
		if (isInMap(x,y)) {
			this.gamefield[x][y] = newBlock;
		}

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
			writer.write(gamefield.length+";"+gamefield[0].length+System.getProperty("line.separator"));
			writer.write(mapName+System.getProperty("line.separator"));
			writer.write(mapDescription+System.getProperty("line.separator"));
			writer.write(gravity);
			
			
			//Write Entity data
			for (Entity entity: getEntitys()) {
				writer.write(entity+System.getProperty("line.separator"));
			}
			
			
			//Write gamefield
			for (int y = 0; y<gamefield[0].length; y++) {
				for (int x = 0; x< gamefield.length; x++) {
					if (x!=0) writer.write(";");
					writer.write(gamefield[x][y].getBid());
				}
				writer.write(System.getProperty("line.separator"));
			}
			
			writer.close();
			
		} catch (IOException e) {
			System.out.println("[ERROR] Could not save this map in "+file.getPath()+"!");
			e.printStackTrace();
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
				
				System.out.println("Creating new DefaultMap...");
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
	
	public int getHeight() {
		return this.gamefield[0].length;
	}
	
	public int getWidth() {
		return this.gamefield.length;
	}
	
	public Block getBlock(int x, int y) {
		if (isInMap(x,y))
		return this.gamefield[x][y];
		else
		return Block.EMPTY;
	}
	
	public boolean isInMap(int x, int y){
		return (x>=0 && y>=0 && x<gamefield.length && y<gamefield[0].length);
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
}
