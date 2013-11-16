package org.lionsoul.tankwar.model;

import java.awt.Graphics;
import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.ExplosionFactory;
import org.lionsoul.tankwar.map.TMap;
import org.lionsoul.tankwar.util.IConstants;

/**
 * bullet class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class Bullet {
	
	public static final short NORMAL_BULLET = 0;
	public static final short BOMB_BULLET = 1;
	public static final short MISSILE_BULLET = 2;
	
	public static Battlefield BF = null;
	public static TMap MAP = null;
	
	private Image image;
	private Tank mtank;				/*source tank's serial*/
	private int tt;					/*source tank's type [Tank.HERO_TANK, TANK.ENEMY_TANK]*/
	private int x;
	private int y;
	private short rows;
	private short cols;
	private int offset = 1;
	private short t;
	private int blood = 1;			/*bloods take for one shot.*/
	private int direction = 0;
	private boolean alive = true;
	
	/**
	 * construct method . <br />
	 * 
	 * @param	bf
	 * @param	image
	 * @param	w
	 * @param	h
	 */
	public Bullet( Battlefield bf, Image image, short t, Tank mtank, int tt,
				int x, int y, short rows, short cols, int direction ) {
		this(bf, image, t, mtank, tt, x, y, rows, cols, direction, 1);
	}
	
	/**
	 * construct method . <br />
	 * 	
	 * @param	bf
	 * @param	image
	 * @param	x
	 * @param	y
	 * @param	w
	 * @param	h
	 * @param	t
	 */
	public Bullet( Battlefield bf, Image image, short t, Tank mtank, int tt,
				int x, int y, short rows, short cols, int direction, int blood ) {
		BF = bf;
		MAP = bf.getMap();
		this.image = image;
		this.t = t;
		this.mtank = mtank;
		this.tt = tt;
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.cols = cols;
		this.direction = direction;
		this.blood = blood;
	}
	
	/**
	 * draw the bullet . <br />
	 * 
	 * @param	g
	 */
	public void draw( Graphics g ) {
		g.drawImage(image, (x - cols / 2) * MAP.getXoffset(),
				(y - rows / 2) * MAP.getYoffset(), null);
		move();
	}
	
	/**
	 * move the bullets . <br /> 
	 */
	private void move() {
		switch ( direction ) {
		case IConstants.DIRECTION_U:	y -= offset; break;
		case IConstants.DIRECTION_RU:	x += offset; y -= offset; break;
		case IConstants.DIRECTION_R:	x += offset; break;
		case IConstants.DIRECTION_RD:	x += offset; y += offset; break;
		case IConstants.DIRECTION_D:	y += offset; break;
		case IConstants.DIRECTION_LD:	x -= offset; y += offset; break;
		case IConstants.DIRECTION_L:	x -= offset; break;
		case IConstants.DIRECTION_LU:	x -= offset; y -= offset; break;	
		}
		
		//the bullet has out of the map.
		if ( (x < 0 || x >= (MAP.getCols() - 1)) 
					|| (y < 0 || y >= (MAP.getRows() - 1)) ) {
			setAlive(false);
			mtank.reduceMovingBullets();
			return;
		}
		//check if the bullet has hit something.
		int bit = MAP.getBit(x, y);
		if ( bit == mtank.getSerial() ) {
			/*
			 * mean that the bullet is still in its master
			 * 		tank's map area. 
			 */
			return;
		}
		
		//clear the bits it takes in the map to BIT_BLANK
		//MAP.setMapBit(ox, oy, rows, cols, TMap.BIT_BLANK);
		
		if ( bit < 0 ) {							//hit the wall or the bullet
			if ( bit != TMap.BIT_BULLET ) {
				Wall w = MAP.getWall(bit);
				if ( w != null && w.couldSmash(this) ) {
					MAP.clearWall(w);
					w.setAlive(false);				//clear the Wall if it could be mashed.
				}
			}
			setAlive(false);						//clear the bullet
			mtank.reduceMovingBullets();
		} else if ( bit > 0 ) {						//hit the tank.
			Tank tank = BF.getTankBySerial(bit);	//get the tank that was hit
			if ( tank == null )		return;
			if ( tank.getType() != tt ) {			//not same tank type (not teammate)						
				if ( tank.getBlood() > blood ) {	//take its blood
					tank.setBlood(tank.getBlood() - blood);
				} else {							//the tank is destroyed
					tank.setAlive(false);
					//add an explosion the battlefield
					BF.addExplosion(ExplosionFactory.createExplosion(BF, x, y,
							t == MISSILE_BULLET ? Explosion.SUPER_EXPLOSION : Explosion.SMALL_EXPOSION));
				}
				setAlive(false);
				mtank.reduceMovingBullets();
			}
		}
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
	
	public short getCols() {
		return cols;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public void setOffset( int offset ) {
		this.offset = offset;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive( boolean alive ) {
		this.alive = alive;
	}
	
	public Tank getMasterTank() {
		return mtank;
	}
	
	public int getType() {
		return t;
	}
	
	public int getTankType() {
		return tt;
	}
}
