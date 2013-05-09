package de.bplaced.mopfsoft.items;

import java.util.ArrayList;
import java.util.List;

import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.gamechanges.GameChange;
import de.bplaced.mopfsoft.gamechanges.GamefieldChange;
import de.bplaced.mopfsoft.map.Direction;
import de.bplaced.mopfsoft.map.Map;

public class Drill extends Tool{

	public Drill(ItemUser owner, Map map) {
		super(owner,map);
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public int getEnergy() {
		return 0;
	}

	@Override
	public int getStrength() {
		return 1;
	}

	@Override
	public int drainEnergy(int amount) {
		return 0;
	}

	@Override
	public int getMaxEnergy() {
		return 0;
	}

	@Override
	public int addEnergy() {
		return 0;
	}

	@Override
	public List<GameChange> use(ItemUser user) {
		List <GameChange> result= new ArrayList<GameChange>();
		
		if (user.getDirection().compareTo(Direction.LEFT) == 0) {
			for (int i=0; i<user.getHeight(); i++) {
				result.add(new GamefieldChange(user.getX()-1, user.getY()+i, 0, user, map));
			}
		} else
		
		if (user.getDirection().compareTo(Direction.RIGHT) == 0) {
			for (int i=0; i<user.getHeight(); i++) {
				result.add(new GamefieldChange(user.getX()+user.getWidth(), user.getY()+i, 0, user, map));
			}
		} else
		
		if (user.getDirection().compareTo(Direction.UP) == 0) {
			for (int i=0; i<user.getWidth(); i++) {
				result.add(new GamefieldChange(user.getX()+i, user.getY()-1, 0, user, map));
			}
		} else
		
		if (user.getDirection().compareTo(Direction.DOWN) == 0) {
			for (int i=0; i<user.getWidth(); i++) {
				result.add(new GamefieldChange(user.getX()+i, user.getY()+user.getHeight(), 0, user, map));
			}
		}
		return result;
	}

}
