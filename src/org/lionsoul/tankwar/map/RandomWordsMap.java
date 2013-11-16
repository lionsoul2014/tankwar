package org.lionsoul.tankwar.map;

import java.awt.Image;
import java.util.HashMap;

import org.lionsoul.tankwar.model.Wall;

/**
 * random words map . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class RandomWordsMap {
	
	public static final String[] WORDS = {
		"|o|", "囧", "龍", "Y", "口", "江", "M", "国",
		"恒", "K", "和", "海", "T", "好", "磊", "H", "吠", "哭", "畅",
		"想", "+", "网", "络", "R", "武", "发", "中", "X", "陳", "鑫", "V", "A"};
	/**
	 * generate a random words map . <br />
	 * 
	 * @param	images
	 * @param	rows		(wall image rows bit)
	 * @param	cols		(wall image cols bit)
	 * @param	x_offset	(start x offset in the map)
	 * @param	y_offset	(start y offset in the map)
	 */
	public static HashMap<Integer, Wall> generate( Image[] images, int rows, int cols, 
				int width, int height, int x_offset, int y_offset ) {
		//get the words
		String str = WORDS[( (int) (Math.random() * 10000) ) % WORDS.length];
		int[][] maps = WordsToRGB.invoke(width, height, str);
		
		HashMap<Integer, Wall> wmap = new HashMap<Integer, Wall>(16, 0.85F);
		int key = -1;
		
		//create the walls
		Wall wall;
		int x_off = width / maps.length;
		int y_off = height / maps[0].length;
		int t, _x, _y, r = y_off / rows, c = x_off / cols;
		for ( int y = 0; y < maps.length; y++ ) {
			for ( int x = 0; x < maps[0].length; x++ ) {
				if ( maps[y][x] == -1 ) {
					t = ( (int)(Math.random() * 10000) ) % images.length;
					_x = x_offset + x * x_off;
					_y = y_offset + y * y_off;
					//add the wall sets
					for ( int j = 0; j < r; j++) {
						for ( int i = 0; i < c; i++ ) {
							wall = new Wall(images[t], t, key,
									_x + i * cols, _y + j * rows, rows, cols);
							wmap.put(key--, wall);
						}
					}
				}
			}
		}
		
		return wmap;
	}
}
