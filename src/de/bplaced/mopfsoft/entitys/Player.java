package de.bplaced.mopfsoft.entitys;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.map.Direction;
import de.bplaced.mopfsoft.map.Map;

public class Player extends ItemUser implements Jumper{

	private HashMap<Direction,Image> images = new HashMap<Direction,Image>();

	public Player(int id, int x, int y, Map map) {
		super(id, x, y, map);
	}

	public Player(int id, String[] args, Map map) {
		this(id, Integer.parseInt(args[0]),Integer.parseInt(args[1]),map);
	}

	@Override
	public int getEid() {
		return 1;
	}

	@Override
	public Image getImage() {
		
		if (this.images.size() == 0)
		prepareImages();
		
		
		return this.images.get(getDirection());
	}

	@Override
	public int getHeight() {
		return 32;
	}

	@Override
	public int getWidth() {
		return 16;
	}

	@Override
	public void resolveEntityCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prepareImages() {
		try {
			this.images.put(Direction.LEFT, new Image("de/bplaced/mopfsoft/entitys/Player_1_left.gif"));
			this.images.put(Direction.RIGHT, new Image("de/bplaced/mopfsoft/entitys/Player_1_right.gif"));
			this.images.put(Direction.DOWN, new Image("de/bplaced/mopfsoft/entitys/Player_1_down.gif"));
			this.images.put(Direction.UP, new Image("de/bplaced/mopfsoft/entitys/Player_1_up.gif"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getSpeed() {
		return 1;
	}

	@Override
	public void jump() {
		if (super.isStanding()) {
			super.verSpeed = -6;
			this.move(0, -1);
		}
	}


}
