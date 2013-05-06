package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.bplaced.mopfsoft.blocks.Block;

public class Player extends ItemUser {

	private Image image = null;

	public Player(int id, int x, int y, Block [][] gamefield) {
		super(id, x, y, gamefield);
	}

	public Player(int id, String[] args, Block [][] gamefield) {
		this(id, Integer.parseInt(args[0]),Integer.parseInt(args[1]),gamefield);
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
		// TODO Auto-generated method stub
		return 32;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public void resolveEntityCollisions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareImage() {
		// TODO Auto-generated method stub
		try {
			this.image = new Image("de/bplaced/mopfsoft/entitys/Player.gif");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


}
