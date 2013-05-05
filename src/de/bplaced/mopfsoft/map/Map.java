package de.bplaced.mopfsoft.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.blocks.Block;
import de.bplaced.mopfsoft.entitys.Entity;
import de.bplaced.mopfsoft.entitys.Player;

public class Map {
	
	private final Image previewImage;
	protected final Block[][] gamefield;
	private final String mapName;
	private final String mapDescription;
	protected final List<Entity> entitys = new ArrayList<Entity>();
	private final File previewImagePath;
	

	public Map (String path) {
		this(new File (path));
	}
	
	public Map() {
		this(new File("maps/DefaultMap.map"));
	}
	
	
	public Map (File file) {


		// If file does not exist, load default
		if (!file.exists()) {
			Map.copyDefaultMap();
			file = new File("maps/DefaultMap.map");
		}
		
		
		// Set path to previewImage
		this.previewImagePath = new File(file.getPath().split("\\.")[0] + ".gif");
		Image previewImageTemp = null;
		try {
			previewImageTemp = new Image(file.getPath().split("\\.")[0] + ".gif");
		} catch (SlickException e1) {
		}
		previewImage = previewImageTemp;
		
		
		//Initialize Temp variables
		String mapNameTemp = null, mapDescriptionTemp = null;
		Block[][] gamefieldTemp = null;
		
		
		// Start loading the map
		try {
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);


			
			//Read file information and process them
			String topLine = reader.readLine();
			
			int xMax = Integer.parseInt(topLine.split(":")[0]);
			int yMax = Integer.parseInt(topLine.split(":")[1]);
			gamefieldTemp = new Block[xMax][yMax];
			
			mapNameTemp = reader.readLine();
			mapDescriptionTemp = reader.readLine();
			
			//Skip empty line
			reader.readLine();
			
			//Read player data
			String line;
			while (!(line = reader.readLine()).equalsIgnoreCase("#")){
				this.entitys.add(Entity.getNewEntity(line.split(":"), gamefieldTemp));
			}
			
			//Read gamefield data
			int x;
			int y = 0;
			
			while ((line = reader.readLine())!= null) {
				x = 0;
				for (String id: line.split(":")) {
					gamefieldTemp[x][y] = Block.getNewBlock(x,y,Integer.parseInt(id));
					x++;
				}
				y++;
			}
			
			//Close readers
			reader.close();
			fr.close();
			
		} catch (Exception e) {
			System.out.println("[ERROR] Could not load gamefield data in "+file.getName()+"!");
		}
		
		this.mapDescription = mapDescriptionTemp;
		this.mapName = mapNameTemp;
		this.gamefield = gamefieldTemp;
		
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
	
	/** Returns the preview image of the map
	 * @return
	 */
	public Image getPreviewImage() {
		return this.previewImage;
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
		this.gamefield[x][y] = newBlock;
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
			writer.write(gamefield.length+":"+gamefield[0].length+System.getProperty("line.separator"));
			writer.write(mapName+System.getProperty("line.separator"));
			writer.write(mapDescription+System.getProperty("line.separator"));
			
			//Write Player data
			for (Player player: getPlayers()) {
				writer.write(player.toString()+System.getProperty("line.separator"));
			}
			
			
			//Write gamefield
			for (int y = 0; y<gamefield[0].length; y++) {
				for (int x = 0; x< gamefield.length; x++) {
					if (x!=0) writer.write(":");
					writer.write(gamefield[x][y].getId());
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

	public File getPreviewImageFile() {
		return this.previewImagePath;
	}

	public List<Entity> getEntitys() {
		return this.entitys;
	}
}
