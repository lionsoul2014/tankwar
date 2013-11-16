package org.lionsoul.tankwar.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * word map generator class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class WordsToRGB {
	
	public static int[][] invoke( int width, int height, String str ) {
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics grap = image.getGraphics();
		
		grap.setColor(Color.BLACK);
		grap.fillRect(0, 0, width, height);
		
		//draw the white string
		grap.setColor(Color.WHITE);
		grap.setFont(new Font("宋体", Font.BOLD, 12));
		grap.drawString(str,
				(width - grap.getFontMetrics().stringWidth(str)) / 2, height / 2);
		
		BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = temp.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.ORANGE);
		int w = image.getWidth();
		int h = image.getHeight();
		
		int x1 = w, y1 = h, x2 = 0, y2 = 0;
		for ( int y = 0; y < h; y++ ) {
			for ( int x = 0; x < w; x++ ) {
				if ( image.getRGB(x, y) == -1 ) {
					if ( x < x1 ) x1 = x;
					if ( x > x2 ) x2 = x;
					if ( y < y1 ) y1 = y;
					if ( y > y1 ) y2 = y;
				}
			}
		}
		
		int[][] maps = new int[y2 - y1 + 1][x2 - x1 + 1];
		for ( int y = y1; y <= y2; y++ ) {
			for ( int x = x1; x <= x2; x++ ) {
				if ( image.getRGB(x, y) == -1 ) {
					maps[y - y1][x - x1] = -1;
				}
			}
		}
		
/*		int x_offset = width / maps.length;
		int y_offset = height / maps[0].length;
		int _x = 10, _y = 10;
		for ( int y = 0; y < maps.length; y++ ) {
			for ( int x = 0; x < maps[0].length; x++ ) {
				if ( maps[y][x] == -1 )
					g.fillOval(_x + x * x_offset, _y + y * y_offset, 10, 10);
			}
		}*/
		
		return maps;
	}
}
