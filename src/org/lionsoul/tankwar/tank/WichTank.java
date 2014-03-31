package org.lionsoul.tankwar.tank;

import java.awt.Graphics;
import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.model.Tank;
import org.lionsoul.tankwar.util.IConstants;

/**
 * Wich(no special meaning) tank class .
 * feature:
 * 	its action is totally like random tank before you scare it
 * 	but when you close it enought it will follow you all the time
 * 	and shot you crazly (^.^)
 * 
 * @author chenxin <chenxin619315@gmail.com>
 */
public class WichTank extends RandomTank 
{
	//searching area (take MAP.col and MAP.row as unit)
	private int radius = 0;
	
	private int SHOT_TIMEOUT_FACOTR_CS = 5;
	private int cbfactor = SHOT_TIMEOUT_FACOTR_CS;

	public WichTank(Battlefield bf, Image[] images, int t, int serial,
			int x, int y, int cols, int rows) 
	{
		super(bf, images, t, serial, x, y, cols, rows);
		infokey = "W";
		radius = 2 * cols;
	}
	
	public WichTank( Battlefield bf, Image[] images, int t,
			int serial, int x, int y, int rows, int cols, int head, int blood ) 
	{
		super(bf, images, t, serial, x, y, rows, cols, head, blood);
		infokey = "W";
	}
	
	/**
	 * rewrite the draw method 
	 * 
	 * @see Tank#draw(Graphics)
	 * @see RandomTank#draw(Graphics)
	 */
	@Override
	public void draw( Graphics g )
	{
		super.draw(g);
		
		//check if wether the hero tank has scared the wich
		//count the center point of the current tank
		int xCenter = x + cols / 2;
		int yCenter = y + rows / 2;
		
		//count the start point
		int sx = xCenter - radius;
		int sy = yCenter - radius;
		if ( sx < 0 ) sx = 0;
		if ( sy < 0 ) sy = 0;
		
		//count the end point
		int ex = xCenter + radius;
		int ey = yCenter + radius;
		if ( ex >= MAP.getCols() ) ex = MAP.getCols();
		if ( ey >= MAP.getRows() ) ey = MAP.getRows();
		
		int r = 0, c = 0;
		Tank hero = BF.getHeroTank();
		int hserial = hero.getSerial();
		boolean __check = false;
		
		for ( r = sx; r < ex; r++ ) 
		{
			for ( c = sy; c < ey; c++ )
			{
				if ( MAP.getBit(r, c) == hserial )
				{
					__check = true;
					break;
				}
			}
			
			if ( __check ) break;
		}
		
		/*
		 * the hero tank has scraed the wich.(in its scraed circle)
		 * And of course, wich will shot it crazy, and you will find youself
		 * impossible to avoid its attack(the iron wall will make you survive).
		 * 
		 * @date: 2014-02-06
		 */
		if ( __check == true ) 
		{
			int xright  = hero.getX() + hero.getCols();
			int ybottom = hero.getY() + hero.getRows();
			
			if ( hero.getX() > xCenter )			//right of it
			{
				if ( hero.getY() > yCenter )
					direction = IConstants.DIRECTION_RD;
				else if ( ybottom < yCenter )
					direction = IConstants.DIRECTION_RU;
				else
					direction = IConstants.DIRECTION_R;
			}
			else if ( xright < xCenter )			//left of it
			{
				if ( hero.getY() > yCenter )
					direction = IConstants.DIRECTION_LD;
				else if ( ybottom < yCenter )
					direction = IConstants.DIRECTION_LU;
				else
					direction = IConstants.DIRECTION_L;
			}
			else									//center of it
			{
				if ( hero.getY() > yCenter )
					direction = IConstants.DIRECTION_D;
				else
					direction = IConstants.DIRECTION_U;
			}
			
			//reset the head
			head = direction;
			
			//fire the bullets with a specified timeout
			cbfactor--;
			if ( cbfactor == 0 ) 
			{
				addBullets();			//add bullets in the work main thread.
				cbfactor = SHOT_TIMEOUT_FACOTR_CS;
			}
		}
	}

}
