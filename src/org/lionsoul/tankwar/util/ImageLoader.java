package org.lionsoul.tankwar.util;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * image loader class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class ImageLoader {
	
	static Toolkit TK = Toolkit.getDefaultToolkit();
	
	/**
	 * load the image from the resource directory . <br />
	 * 
	 * @param	_file
	 * @return	ImageIcon
	 */
	public static ImageIcon loadImageIcon(String _file) {
		return new ImageIcon(ImageLoader.class.getResource("/res/image/"+_file));
	}
}
