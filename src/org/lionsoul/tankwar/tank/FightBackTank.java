package org.lionsoul.tankwar.tank;

import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.model.Tank;

/**
 * fight back tank class . <br />
 * feature:
 * 	it will fight back when you shot them . <br />
 * 	and it will track to you throught the bullets. <br />
 * 
 *  before this all its actions is random . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class FightBackTank extends Tank {

	public FightBackTank(Battlefield bf, Image[] images, int t, int serial,
			int x, int y, int cols, int rows) {
		super(bf, images, t, serial, x, y, cols, rows);
	}

}
