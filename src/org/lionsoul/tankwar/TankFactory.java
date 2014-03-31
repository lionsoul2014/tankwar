package org.lionsoul.tankwar;

import org.lionsoul.tankwar.model.Tank;
import org.lionsoul.tankwar.tank.HeroTank;
import org.lionsoul.tankwar.tank.RandomTank;
//import org.lionsoul.tankwar.tank.TrackTank;
import org.lionsoul.tankwar.tank.WichTank;

/**
 * tank create factory, not thread safe.
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class TankFactory {
	
	//direction, offset, blood, bomb, missile
	private static int serial = 1;
	public static final int DEFAULT_TANK_OUTPUT = 5;
	private static int tanknum = 0;
	
	public static final int RANDOM_ENEMY_TANK = 0;				//random tank
	public static final int WICH_ENEMY_TANK = 1;				//wich tank
	public static final int TRACK_ENEMY_TANK = 2;				//track tank
	public static final int CIRCLE_ENEMY_TANK = 3;
	
	//the total number of tank type
	public static final int ENEMY_TANK_TYPE_NUMBER = 4;
	
	public static void reset() 
	{
		serial = 1;
		tanknum = 0;
	}
	
	public static int getTankOutput() {
		return tanknum;
	}
	
	/**
	 * create a here tank . <br />
	 * 
	 * @param	bf
	 * @param	w
	 * @param	h
	 * @return	HeroTank
	 */
	public static HeroTank createCenterHeroTank( Battlefield bf,
							int w, int h, int[] cfg ) {
		int rows = (h / bf.getMap().getYoffset());
		int cols = (w / bf.getMap().getXoffset());
		
		//hero use rows * MAP.getYOffset() instead of h
		//and use cols * MAP.getXOffset() instead of w
		HeroTank hTank = new HeroTank( bf, Battlefield.heroImages, Tank.HERO_TANK, serial++,
				bf.getMap().getCols() / 2,
				bf.getMap().getRows() - rows, rows, cols);
		
		hTank.setHead(cfg[0]);
		hTank.setOffset(cfg[1]);
		hTank.setBlood(cfg[2]);
		hTank.setBomb(cfg[3]);
		hTank.setMissile(cfg[4]);
		return hTank;
	}
	
	/**
	 * create a enemy tank . <br />
	 * 
	 * @param	bf
	 * @param	w
	 * @param	h
	 * @return	Tank
	 */
	public static Tank createEnemyTank( Battlefield bf, 
						int w, int h, int[] cfg, int type ) 
	{
		short rows = (short) (h / bf.getMap().getYoffset());
		short cols = (short) (w / bf.getMap().getXoffset());
		int i = ( int ) ( Math.random() * 1000 ) % Battlefield.enemyImages.length;
		int x = ( ( int ) (Math.random() * 10000) ) % ( bf.getMap().getCols() - cols);
		
		Tank etank = null;
		switch ( type )
		{
/*		case TRACK_ENEMY_TANK:
			etank = new TrackTank( bf, 
					Battlefield.enemyImages[i], Tank.ENEMY_TANK, serial++, x, 0, rows, cols );
			break;*/
		case CIRCLE_ENEMY_TANK:
		case TRACK_ENEMY_TANK:
		case RANDOM_ENEMY_TANK:
			etank = new RandomTank( bf, 
					Battlefield.enemyImages[i], Tank.ENEMY_TANK, serial++, x, 0, rows, cols );
			((RandomTank)etank).setSeeds(new int[] {cfg[5], cfg[6]});
			break;
		case WICH_ENEMY_TANK:
			etank = new WichTank( bf, 
					Battlefield.enemyImages[i], Tank.ENEMY_TANK, serial++, x, 0, rows, cols );
			((WichTank)etank).setSeeds(new int[] {cfg[5], cfg[6]});
			break;
/*		case CIRCLE_ENEMY_TANK:
			etank = new CircleTank( bf, 
					Battlefield.enemyImages[i], Tank.ENEMY_TANK, serial++, x, 0, rows, cols );
			break;*/
		}
		
		etank.setHead(cfg[0]);
		etank.setOffset(cfg[1]);
		etank.setBlood(cfg[2]);
		etank.setBomb(cfg[3]);
		etank.setMissile(cfg[4]);

		tanknum++;
		
		return etank;
	}
}
