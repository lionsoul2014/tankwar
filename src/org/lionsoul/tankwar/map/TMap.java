package org.lionsoul.tankwar.map;

//import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.lionsoul.tankwar.model.Wall;

/**
 * tank map base class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class TMap {
	
	public static final int BIT_BLANK = 0;
	public static final int BIT_BULLET = - (1 << 20);
	
	protected int[][] maps = null;
	protected int rows;
	protected int cols;
	protected int x_offset;
	protected int y_offset;
	protected int width;
	protected int height;
	protected HashMap<Integer, Wall> wallmap;
	
	public TMap( int rows, int cols,
				int x_offset, int y_offset, HashMap<Integer, Wall> wallmap ) {
		this.rows = rows;
		this.cols = cols;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.wallmap = wallmap;
		
		maps = new int[rows][cols];
		width = cols * x_offset;
		height = rows * y_offset;
	}
	
	/**
	 * draw the map . <br />
	 * 
	 * @param	g
	 */
	public void draw( Graphics g ) {
		//for debug
		/*int x, y;
		for ( y = 0; y < rows; y++ ) {
			for ( x = 0; x < cols; x++ ) {
				if ( maps[y][x] > 0 ) {
					g.setColor(Color.RED);
					g.drawRect(x * x_offset, y * y_offset, x_offset, y_offset);
				}
				else if ( maps[y][x] < 0 ) {
					g.setColor(Color.BLUE);
					g.drawRect(x * x_offset, y * y_offset, x_offset, y_offset);
				}
			}
		}*/
		
		if ( wallmap != null ) {
			Iterator<Entry<Integer, Wall>> it = wallmap.entrySet().iterator();
			Wall w;
			while ( it.hasNext() ) {
				Entry<Integer, Wall> e= it.next();
				w = e.getValue();
				if ( ! w.isAlive() ) it.remove();
				else {
					g.drawImage(w.getImage(), w.getX() * x_offset, w.getY() * y_offset, 24, 24, null);
				}
			}
		}

	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getBit( int x, int y ) {
		return maps[y][x];
	}
	
	/**
	 * set bits of the map to the specified value . <br />
	 * 
	 * @param	x
	 * @param	y
	 * @param	rows
	 * @param	cols
	 * @param	val
	 */
	public void setMapBit( int x, int y, int rows, int cols, int val ) {
		int c;
		for ( int r = 0; r < rows; r++ ) {
			for ( c = 0; c < cols; c++ ) setBit(x + c, y + r, val);
		}
	}
	
	/** check the bit in the map
	 * 		according to the specified rectange . <br />
	 * 
	 * @param	x
	 * @param	y
	 * @param	w
	 * @param	y
	 * @return	boolean
	 */
	public boolean checkMapBit( int x, int y, int rows, int cols, int val ) {
		int c;
		for ( int r = 0; r < rows; r++ ) {
			for ( c = 0; c < cols; c++ ) if ( getBit(x + c, y + r) != val ) return false;
		}
		return true;
	}
	
	public void setBit( int x, int y, int val ) {
		maps[y][x] = val;
	}
	
	public int getXoffset() {
		return x_offset;
	}
	
	public int getYoffset() {
		return y_offset;
	}
	
	public void clear() {
		int x, y;
		for ( y = 0; y < rows; y++ ) {
			for ( x = 0; x < cols; x++ ) maps[y][x] = BIT_BLANK;
		}
	}
	
	/**
	 * get the specified wall by its serial . <br />
	 * 
	 * @param	serial
	 * @return	Wall
	 */
	public Wall getWall( int serial ) {
		if ( wallmap == null ) return null;
		return wallmap.get(serial);
	}
	
	/**
	 * clear the given wall in the map . <br />
	 * 
	 * @param	w
	 */
	public void clearWall( Wall w ) {
		if ( w != null ) {
			setMapBit(w.getX(), w.getY(), w.getRows(), w.getCols(), BIT_BLANK);
		}
	}
	
	/**
	 * set wall map . <br /> 
	 */
	public void setWalls( HashMap<Integer, Wall> wallmap ) {
		this.wallmap = wallmap;
		
		//set the map bit
		if ( wallmap != null ) {
			Iterator<Entry<Integer, Wall>> it = wallmap.entrySet().iterator();
			Wall w;
			while ( it.hasNext() ) {
				Entry<Integer, Wall> e= it.next();
				w = e.getValue();
				if ( w.getType() != Wall.GRASS_WALL )
					setMapBit(w.getX(), w.getY(), w.getRows(), w.getCols(), w.getSerial());
			}
		}
	}
}
