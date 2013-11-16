package org.lionsoul.tankwar.tank;

import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.model.Tank;

/**
 * Track tank class .<br />
 * feature:
 * 	it will always track your step,
 *  and move towards you and try to kill you . <br />
 *  
 * @author chenxin<chenxin619315@gmail.com>
 */
public class TrackTank extends Tank {

	public TrackTank(Battlefield bf, Image[] images, int t, int serial,
			int x, int y, int cols, int rows) {
		super(bf, images, t, serial, x, y, cols, rows);
	}

}
