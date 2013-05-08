package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.map.Map;

public class Player extends ItemUser implements Jumper{

	private Image image = null;

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
		if (this.image == null)
		prepareImage();
		return this.image;
	}

	@Override
	public void use(int itemId) {
		// TODO Auto-generated method stub
		
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
	public void prepareImage() {
		try {
			this.image = new Image("de/bplaced/mopfsoft/entitys/Player.gif");
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
