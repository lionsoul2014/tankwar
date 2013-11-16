package org.lionsoul.tankwar.barriers;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import org.lionsoul.tankwar.Battlefield;

/**
 * barriers selector class . <br />
 * 		you may extends this class and rewrite the draw method
 * 	to change the user interface . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class BarriersSelector {
	
	private Font titleFont = new Font("Arial", Font.BOLD, 30);
	private Font numFont = new Font("Arial", Font.BOLD, 30);
	private Font cFont = new Font("宋体", Font.PLAIN, 12);
	private Color sColor = new Color(100, 100, 35);
	private Color color = new Color(230, 170, 5);

	private Battlefield BF = null;
	private Image tImages = null;
	private Image pImages = null;
	private ImageIcon grass = null;
	private AudioClip buttonClip = null;
	private int i_width;
	private int i_height;
	
	private int barriers;
	private int max;			/*maximum barriers*/
	private int offset;			/*number offset*/
	private int w;				/*width of the screen*/
	private int h;				/*height of the screen*/
	
	private int str_width;
	
	/**
	 * construct method . <br />
	 * 
	 * @param	default
	 * @param	max
	 * @param	offset
	 * @param	w
	 * @param	h
	 * @param	icons
	 */
	public BarriersSelector( Battlefield bf, int defualt,
			int max, int offset, int w, int h, ImageIcon[] icons, AudioClip buttonClip ) {
		this.BF = bf;
		this.barriers = defualt;
		this.max = max;
		this.offset = offset;
		this.w = w;
		this.h = h;
		this.tImages = icons[0].getImage();
		this.pImages = icons[1].getImage();
		this.grass = icons[2];
		this.buttonClip = buttonClip;
		i_width = icons[0].getIconWidth();
		i_height = icons[0].getIconHeight();
		
		str_width = max * this.offset;
	}
	
	/**
	 * draw the user interface . <br />
	 * 
	 * @param	g
	 */
	public void draw( Graphics g ) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w, h);
		
		//title string
		String str = "Barriers Selector";
		g.setColor(sColor);
		g.setFont(titleFont);
		int x = ( w - g.getFontMetrics(titleFont).stringWidth(str) ) / 2;
		int y = h / 2 - 50;		//center - up space
		g.drawString(str, x, y);
		//title image
		g.drawImage(tImages, x - i_width - 10,
			y - i_height / 2 - 10, null);
		
		//grass image
		for ( int j = 0; j < 9; j++ ) {
			g.drawImage(grass.getImage(),
					x - i_width - 10 + grass.getIconWidth() * j, y + 10, null);
		}
		
		
		g.setFont(numFont);
		y = h / 2 - numFont.getSize() + 70;		//center - down space
		for ( int j = 1; j <= max; j++ ) {
			if ( j < 10 ) str = "0"+j;
			else str = ""+j;
			x = (w - str_width) / 2 + offset * ( j - 1 );
			if ( j == barriers ) {
				g.drawImage(pImages, x - (i_width - numFont.getSize()) / 2, y, null);
				g.setColor(color);
				g.drawString(str, x, y);
			}
			else {
				g.setColor(sColor);
				g.drawString(str, x, y);
			}
		}
		
		//copyright
		g.setColor(new Color(50, 50, 50));
		int cw = 400, ch = 20;
		x = (w - cw) / 2;
		y = h / 10;
		g.fill3DRect(x, y, cw, ch, true);
		str = "畅想网络, 狮子的魂<chenxin619315@gmail.com>";
		g.setFont(cFont);
		g.setColor(color);
		g.drawString(str,
			x + (cw - g.getFontMetrics(cFont).stringWidth(str)) / 2, 
			y + cFont.getSize() + 2);
		
		//handling tip
		str = "<操作提示: A, D左右移动, J 确认游戏, H 游戏帮助>";
		g.drawString(str, (w - g.getFontMetrics().stringWidth(str)) / 2,
				y + ch + 20);
		
		
	}
	
	/**
	 * key press handling method . <br />
	 * 
	 * @param	e
	 */
	public void keyPress( KeyEvent e ) {
		switch ( e.getKeyCode() ) {
		case KeyEvent.VK_A:
			if ( barriers > 1 ) {
				barriers--;
				buttonClip.stop();
				buttonClip.play();
			}
			break;
		case KeyEvent.VK_D:
			if ( barriers < max ) {
				barriers++;
				buttonClip.stop();
				buttonClip.play();
			}
			break;
		case KeyEvent.VK_J:
			buttonClip.stop();
			buttonClip.play();
			BF.play(barriers - 1);
			break;
		}
	}
	
	public void stopAudio() {
		buttonClip.stop();
	}
	
	public boolean hasNext() {
		return (barriers < max);
	}
	
	public int getBarriers() {
		return barriers;
	}
	
	public void setBarriers( int barriers ) {
		if ( barriers > 0 && barriers <= max )
			this.barriers = barriers;
	}
	
	public int next() {
		if ( ++barriers <= max ) return barriers;
		return 0;
	}
}
