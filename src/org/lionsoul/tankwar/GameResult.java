package org.lionsoul.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * game result tip class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class GameResult {
	
	private Font fFont = new Font("Arial", Font.BOLD, 30);
	private Font tFont = new Font("Arial", Font.PLAIN, 12);
	private Color sColor = new Color(100, 100, 35);
	
	private Image image;
	private int i_width;
	//private int i_height;
	private int idx = 0;
	private int w;
	private int h;
	private int ret;
	
	private int offset;
	private int counter = 0;
	
	public GameResult( int w, int h, int ret, int offset, ImageIcon icon ) {
		this.w = w;
		this.h = h;
		this.ret = ret;
		this.offset = offset;
		this.counter = offset;
		
		this.image = icon.getImage();
		i_width = icon.getIconWidth();
		//i_height = icon.getIconHeight();
	}
	
	/**
	 * draw the user interface . <br />
	 * 
	 * @param	g
	 */
	public void draw( Graphics g ) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w, h);
		
		String str, tip;
		g.setFont(fFont);
		g.setColor(sColor);
		if ( ret == Battlefield.GAME_FAILED ) {
			str = "You lose the game.";
			tip = "<Press ESC back to the main menu or R to retry>";
		} else {
			str = "Yeah, You win the game.";
			tip = "<Press ESC back to the main menu or N to continue>";
		}
		
		int x = ( w - g.getFontMetrics().stringWidth(str) ) / 2;
		int y = h / 2 - fFont.getSize() - 20;
		g.drawString(str, x, y);
		int rows = g.getFontMetrics().stringWidth(str) / i_width + 1;
		if ( counter-- == 0 ) {
			if ( idx < rows ) idx++;
			counter = offset;
		}
		for ( int j = 0; j < idx; j++ ) {
			g.drawImage(image, x + j * i_width, y + 10, null);
		}
		
		//tip words
		g.setFont(tFont);
		g.drawString(tip, ( w - g.getFontMetrics().stringWidth(tip) ) / 2, 
				h / 2 + tFont.getSize() + 20);
	}
}
