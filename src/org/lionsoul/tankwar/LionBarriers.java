package org.lionsoul.tankwar;

import org.lionsoul.tankwar.barriers.Barriers;
import org.lionsoul.tankwar.util.IConstants;

/**
 * lion soul's barriers define . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class LionBarriers {
	
	public static final Barriers[] DEFAULT = new Barriers[] {
		new Barriers(
			15, 
			//direction, offset, blood, bomb, missile
			new int[] {IConstants.DIRECTION_U, 1, 5, 2, 1},
			//direction, offset, blood, bomb, missile, move interval, shot interval
			new int[] {IConstants.DIRECTION_D, 1, 1, 0, 0, 50, 80},
			//bullet, offset, blood
			new short[][] {					//hero
				new short[] {4, 1},			//normal bullets
				new short[] {4, 2},			//bomb bullets
				new short[] {5, 5}			//missile bullets
			},
			new short[][] {					//enemy
				new short[] {4, 1},
				new short[] {4, 2},
				new short[] {5, 5}
			}
		),
		new Barriers(
			20,
			new int[] {IConstants.DIRECTION_U, 1, 5, 2, 2},
			new int[] {IConstants.DIRECTION_D, 1, 1, 1, 0, 40, 70},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 3},
				new short[] {5, 5}
			},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 3},	
				new short[] {5, 5}
			}
		),
		//barriers 3
		new Barriers(
			15,
			new int[] {IConstants.DIRECTION_U, 2, 5, 3, 2},
			new int[] {IConstants.DIRECTION_D, 2, 2, 2, 0, 30, 60},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},
				new short[] {5, 10}
			},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},	
				new short[] {5, 10}
			}
		),
		//barrier 4
		new Barriers(
			30,
			new int[] {IConstants.DIRECTION_U, 2, 5, 5, 2},
			new int[] {IConstants.DIRECTION_D, 2, 3, 5, 1, 20, 50},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},
				new short[] {5, 10}
			},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},	
				new short[] {5, 10}
			}
		),
		new Barriers(
			35,
			new int[] {IConstants.DIRECTION_U, 2, 5, 8, 4},
			new int[] {IConstants.DIRECTION_D, 2, 3, 5, 1, 10, 40},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},
				new short[] {5, 10}
			},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},	
				new short[] {5, 10}
			}
		),
		new Barriers(
			40,
			new int[] {IConstants.DIRECTION_U, 2, 10, 10, 8},
			new int[] {IConstants.DIRECTION_D, 2, 5, 10, 3, 10, 30},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},
				new short[] {6, 10}
			},
			new short[][] {
				new short[] {4, 1},
				new short[] {4, 5},	
				new short[] {6, 10}
			}
		),
	};
}
