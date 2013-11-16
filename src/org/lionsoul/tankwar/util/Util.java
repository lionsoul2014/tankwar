package org.lionsoul.tankwar.util;

import java.awt.Point;

/**
 * util class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class Util {
	
	/**
	 * count the x and y Coordinate
	 * 		with its centre coordinate and the its size. <br />
	 * 
	 * @param	x
	 * @param 	y
	 * @param	y
	 * @param	h
	 */
	public static Point center( int x, int y, int w, int h ) {
		return new Point(x - w / 2, y - h / 2);
	}
}
