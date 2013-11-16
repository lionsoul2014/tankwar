package org.lionsoul.tankwar.model;

import java.awt.Graphics;
import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.map.TMap;

/**
 * explosion control class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class Explosion {
	
	public static final int SMALL_EXPOSION = 0;
	public static final int SUPER_EXPLOSION = 1;
	
	public static Battlefield BF = null;
	public static TMap MAP = null;
	
	private Image[] images;
	private int idx = 0;
	private int x;
	private int y;
	private int t;
	private short rows;
	private short cols;
	private short offset;
	private short counter;
	private boolean alive = true;
	
	/**
	 * construct method . <br />
	 * 
	 * @param	image
	 * @param	x	explosion center x
	 * @param	y	explosion center y
	 * @param	rows
	 * @param	cols
	 * @param	offset
	 */
	public Explosion( Battlefield bf, Image[] image, int t,
			int x, int y, short rows, short cols, short offset ) {
		if ( BF == null ) {
			BF = bf;
			MAP = bf.getMap();
		}
		this.images = image;
		this.t = t;
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.cols = cols;
		this.offset = offset;
		counter = offset;
	}
	
	/**
	 * draw the explosion . <br />
	 * 
	 * @param	g
	 */
	public void draw( Graphics g ) {
		if ( idx < images.length ) {
			if ( counter-- <= 0 ) {
				g.drawImage(images[idx++], (x - cols / 2) * MAP.getXoffset(),
					(y - rows / 2) * MAP.getYoffset(), null);
				counter = offset;
			}
		}
		else setAlive(false);		//done
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public short getRows() {
		return rows;
	}

	public void setRows(short rows) {
		this.rows = rows;
	}

	public short getCols() {
		return cols;
	}

	public void setCols(short cols) {
		this.cols = cols;
	}

	public short getOffset() {
		return offset;
	}

	public void setOffset(short offset) {
		this.offset = offset;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive( boolean alive ) {
		this.alive = alive;
	}
	
	public int getType() {
		return t;
	}
}
