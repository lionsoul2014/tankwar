package org.lionsoul.tankwar.tank;

import java.awt.Graphics;
import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.BulletFactory;
import org.lionsoul.tankwar.model.Bullet;
import org.lionsoul.tankwar.model.Tank;

/**
 * random tank class . <br />
 * feature:
 * 	all the actions entirely depends on random . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class RandomTank extends Tank {
	
	private int[] seeds = new int[] {20, 50};
	
	private int moveInterval = 10;
	private int shotInterval = 10;
	
	public RandomTank(Battlefield bf, Image[] images, int t, int serial,
				int x, int y, int rows, int cols ) {
		super(bf, images, t, serial, x, y, rows, cols);
	}
	
	public RandomTank( Battlefield bf, Image[] images, int t,
			int serial, int x, int y, int rows, int cols, int head, int blood ) {
		super(bf, images, t, serial, x, y, rows, cols, head, blood);
	}
	
	@Override
	public void draw( Graphics g ) {
		super.draw(g);
		//autonomous moveing
		if ( moveInterval-- == 0 ) {
			int t = ( ( int ) ( Math.random() * 10000 ) ) % 7;
			direction = 1 << t;
			moveInterval = ( (int) ( Math.random() * 1000) ) % seeds[0];
		}
		
		//autonomous shot
		if ( shotInterval-- == 0 ) {
			if ( couldShot() ) {
			Bullet bbt = BulletFactory.createEnemyBullet(BF, 
					this, Bullet.NORMAL_BULLET, 0, 0, head);
			bbt.setX(x + (cols - bbt.getCols()) / 2);
			bbt.setY(y + (rows - bbt.getRows()) / 2);
			increaseMovingBullets();
			BF.addBullet(bbt);
			shotInterval = ( ( int ) ( (Math.random() + 1) * 10000 ) ) % seeds[1];
			}
		}
	}
	
	/**
	 * set the seeds . <br />
	 * seeds[0] = move interval seed,
	 * seeds[1] = shot interval seed
	 * 
	 * @param	seeds
	 */
	public void setSeeds( int[] seeds ) {
		this.seeds = seeds;
	}
}
