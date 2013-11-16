package org.lionsoul.tankwar;

import org.lionsoul.tankwar.model.Explosion;

/**
 * explosion factory class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class ExplosionFactory {
	
	//rows, cols, offset
	private static short[][] exp = new short[][] {
		new short[] {30, 30, 0},
		new short[] {42, 42, 0},
	};
	
	/**
	 * create a new explosion . <br />
	 * 
	 * @param	bf
	 * @param	x
	 * @param	y
	 * @param	t
	 * @return	Explosion
	 */
	public static Explosion createExplosion( Battlefield bf, int x, int y, int t ) {
		return new Explosion( bf, Battlefield.explosionImages[t], t,
				x, y, exp[t][0], exp[t][1], exp[t][2] );
	}
}
