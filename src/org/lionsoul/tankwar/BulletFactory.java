package org.lionsoul.tankwar;

import org.lionsoul.tankwar.model.Bullet;
import org.lionsoul.tankwar.model.Tank;

/**
 * Bullet factory class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class BulletFactory {
	
	//rows, cols
	private static short[][] rect = {
		new short[]{3, 3},
		new short[]{4, 4},
		new short[]{5, 5}
	};
	
	//offset, blood
	public static short[][] heroDamage = {
		new short[] {4, 1},			//normal bullets
		new short[] {5, 5},			//bomb bullets
		new short[] {6, 10}			//missile bullets
	};
	
	public static short[][] enemyDamage = {
		new short[] {4, 1},			//normal bullets
		new short[] {5, 5},			//bomb bullets
		new short[] {6, 10}			//missile bullets
	};
	
	/**
	 * create a hero bullet . <br />
	 * 
	 * @param	bf
	 * @param	mtank
	 * @param	t
	 * @param	x
	 * @param	y
	 * @param	direction
	 */
	public static Bullet createHeroBullet( Battlefield bf, 
					Tank mtank, short t, int x, int y, int direction ) {
		Bullet e = new Bullet(bf, Battlefield.bulletImages[t],
				t, mtank, Tank.HERO_TANK, x, y, rect[t][0], rect[t][1], direction);
		e.setOffset(heroDamage[t][0]);
		e.setBlood(heroDamage[t][1]);
		return e;
	}
	
	
	/**
	 * create a enemy bullet . <br />
	 * 
	 * @param	bf
	 * @param	mtank
	 * @param	t
	 * @param	x
	 * @param	y
	 * @param	direction
	 */
	public static Bullet createEnemyBullet( Battlefield bf, 
					Tank mtank, short t, int x, int y, int direction ) {
		Bullet e = new Bullet(bf, Battlefield.bulletImages[t],
				t, mtank, Tank.ENEMY_TANK, x, y, rect[t][0], rect[t][1], direction);
		e.setOffset(enemyDamage[t][0]);
		e.setBlood(enemyDamage[t][1]);
		return e;
	}
}
