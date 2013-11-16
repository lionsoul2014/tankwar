package org.lionsoul.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Battlefield canvas control class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class BattleCanvas extends JPanel {

	private static final long serialVersionUID = -5073061633237940838L;
	
	private Image bufferImage = null;
	
	public BattleCanvas() {
		setSize(Battlefield.w_size);
		setPreferredSize(Battlefield.w_size);
	}
	
	@Override
	public void paintComponent( Graphics g ) {
		if ( bufferImage == null ) {
			//fill the background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			//draw the tip message
			String str = "Loading...";
			Font f = new Font("Arial", Font.BOLD, 50);
			g.setColor(Color.WHITE);
			g.setFont(f);
			g.drawString(str,
					(getWidth() - this.getFontMetrics(f).stringWidth(str)) / 2,
					getHeight() / 2);
			return;
		}
		//draw the buffer.
		g.drawImage(bufferImage, 0, 0, this);
	}
	
	public void setBufferImage( Image image ) {
		bufferImage = image;
	}
}
