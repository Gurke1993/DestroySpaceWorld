package de.bplaced.mopfsoft.items;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import de.bplaced.mopfsoft.entitys.ItemUser;
import de.bplaced.mopfsoft.gamechanges.EnvironmentChange;
import de.bplaced.mopfsoft.gamechanges.GameChange;
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
			result.add(new EnvironmentChange(new Rectangle(user.getX()-1, user.getY(), 1, user.getHeight()), 0, user, map));
		} else
		
		if (user.getDirection().compareTo(Direction.RIGHT) == 0) {
			result.add(new EnvironmentChange(new Rectangle(user.getX()+user.getWidth(), user.getY(), 1, user.getHeight()), 0, user, map));
		} else
		
		if (user.getDirection().compareTo(Direction.UP) == 0) {
			result.add(new EnvironmentChange(new Rectangle(user.getX(), user.getY()-1, user.getWidth(), 1), 0, user, map));
		} else
		
		if (user.getDirection().compareTo(Direction.DOWN) == 0) {
			result.add(new EnvironmentChange(new Rectangle(user.getX(), user.getY()+user.getHeight(), user.getWidth(), 1), 0, user, map));
		}
		return result;
	}

}
