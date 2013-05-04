package de.bplaced.mopfsoft.entitys;

import org.newdawn.slick.Image;

import de.bplaced.mopfsoft.blocks.Block;

public class Player extends ItemUser {

	public Player(int x, int y, Block [][] gamefield) {
		super(x, y, gamefield);
	}

	public Player(String[] args, Block [][] gamefield) {
		this(Integer.parseInt(args[0]),Integer.parseInt(args[1]),gamefield);
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
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


}
