package org.lionsoul.tankwar.util;

import java.awt.Color;
import java.awt.Font;

/**
 * constants class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class IConstants {
	//direction
	public static final int DIRECTION_U = 1 << 0;
	public static final int DIRECTION_R = 1 << 1;
	public static final int DIRECTION_RU = 1 << 2;
	public static final int DIRECTION_RD = 1<< 3;
	
	public static final int DIRECTION_D = 1 << 4;
	public static final int DIRECTION_L = 1 << 5;
	public static final int DIRECTION_LU = 1 << 6;
	public static final int DIRECTION_LD = 1 << 7;
	
	public static final short X_OFFSET = 3;
	public static final short Y_OFFSET = 3;
	
	public static final int T_WIDTH = 48;
	public static final int T_HEIGHT = 48;
	
	public static final Font iFont = new Font("宋体", Font.PLAIN, 12);
	public static final Color iColor = Color.RED;
}
