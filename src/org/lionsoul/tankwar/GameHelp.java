package org.lionsoul.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/**
 * game help manager class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class GameHelp {
	
	
	public static final String[] HELP = new String[]{
		"游戏操作帮助: ",
		"J - 发射普通子弹",
		"K - 发射普通炸弹",
		"L - 发射无敌飞弹",
		"P - 暂停/开始",
		"ESC - 回到关卡选择",
		"M - 简易/复杂模式切换",
		"H - 弹出/关闭本窗口"
	};
	
	private Image image = null;
	private int w;
	private int h;
	private boolean show;
	
	public GameHelp( int w, int h ) {
		this.w = w;
		this.h = h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public void setVisible( boolean show ) {
		this.show = show;
	}
	
	public boolean isVisible() {
		return show;
	}
	
	/**
	 * create the help image . <br />
	 * 
	 * @return	Image
	 */
	public Image getHelpImage() {
		if ( image == null ) {
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = img.createGraphics();
			img = g.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
			g.dispose();
			g = img.createGraphics();
			
			g.setColor(new Color(0, 0, 0, 80));		//set the background
			//g.fill3DRect(0, 0, w, h, false);
			g.fillRoundRect(0, 0, w, h, 5, 5);
			
			Font f = new Font("宋体", Font.PLAIN, 14);
			g.setFont(f);
			g.setColor(Color.RED);
			int x_off = 20, y_off = 25;
			for ( int j = 0; j < HELP.length; j++ ) {
				g.drawString(HELP[j], x_off, y_off + j * (f.getSize() + 5));
			}
			
			/*int[] rgbArray = new int[h * w];
			img.setRGB(0, 0, w, h, rgbArray, 0, w);*/
			g.dispose();
			image = img;
		}
		
		return image;
	}
}
