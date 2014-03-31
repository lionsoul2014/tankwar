package org.lionsoul.tankwar.tank;

import java.awt.Graphics;
import java.awt.Image;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.model.Tank;

/**
 * Track tank class .<br />
 * feature:
 * 	it will always track your step,
 *  and move towards you and try to kill you . <br />
 *  
 * @author chenxin <chenxin619315@gmail.com>
 */
public class TrackTank extends Tank 
{
	//the aim tank
	private Tank aimTank;

	public TrackTank(Battlefield bf, Image[] images, int t, int serial,
			int x, int y, int cols, int rows) 
	{
		super(bf, images, t, serial, x, y, cols, rows);
		infokey = "T";
	}
	
	public TrackTank( Battlefield bf, Image[] images, int t,
			int serial, int x, int y, int rows, int cols, int head, int blood ) 
	{
		super(bf, images, t, serial, x, y, rows, cols, head, blood);
		infokey = "T";
	}
	
	/**
	 * set the aim tank
	 * 
	 * @param aimTank
	 */
	public void setAimTank( Tank aimTank )
	{
		this.aimTank = aimTank;
	}
	
	/**
	 * get the aim tank
	 * 
	 * @return Tank
	 */
	public Tank getAimTank()
	{
		return aimTank;
	}
	
	/**
	 * rewrite the draw method to track the hero tank
	 * 
	 * @param	g
	 * @see		Tank#draw(Graphics)
	 */
	@Override
	public void draw( Graphics g )
	{
		//invoke the parent draw, count the direction and move the tank.
		super.draw(g);
		
		int xCenter = x + cols / 2;
		//int yCenter = y + rows / 2;
		
		/*
		 * we will let the tank tack the specified tank(aimTank)
		 */
		int xright  = aimTank.getX() + aimTank.getCols();
		//int ybottom = aimTank.getY() + aimTank.getRows();
		
		if ( xright < xCenter )					//left of it 
		{
			
		}
		else if ( aimTank.getX() > xCenter ) 	//right of it 
		{
			
		}
		else									//so-called center 
		{
			
		}
		
	}

}
