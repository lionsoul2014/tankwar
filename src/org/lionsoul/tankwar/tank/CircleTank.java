package org.lionsoul.tankwar.tank;

import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.model.Tank;

/**
 * circle tank class . <br />
 * feature:
 * 	it moves around the map border and shot the center. <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class CircleTank extends Tank {

	public CircleTank(Battlefield bf, Image[] images, int t, int serial,
			int x, int y, int cols, int rows) {
		super(bf, images, t, serial, x, y, cols, rows);
	}

}
