package org.lionsoul.tankwar.tank;

import java.awt.Image;
import java.awt.event.KeyEvent;

import org.lionsoul.tankwar.Battlefield;
import org.lionsoul.tankwar.BulletFactory;
import org.lionsoul.tankwar.model.Bullet;
import org.lionsoul.tankwar.model.Tank;
import org.lionsoul.tankwar.util.IConstants;

/**
 * hero tank . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class HeroTank extends Tank {

	public HeroTank(Battlefield bf, Image[] images, int t, int serial,
				int x, int y, int rows, int cols) {
		super(bf, images, t, serial, x, y, cols, cols, IConstants.DIRECTION_U, 5);
	}
	
	public HeroTank(Battlefield bf, Image[] images, int t, int serial,
				int x, int y, int rows, int cols, int head, int blood ) {
		super(bf, images, t, serial, x, y, rows, cols, head, blood);
	}
	
	public void keyPress( KeyEvent e ) {
		if ( ! alive ) return;
		switch ( e.getKeyCode() ) {
		case KeyEvent.VK_A:
			direction |= IConstants.DIRECTION_L;
			break;
		case KeyEvent.VK_S:
			direction |= IConstants.DIRECTION_D;
			break;
		case KeyEvent.VK_D:
			direction |= IConstants.DIRECTION_R;
			break;
		case KeyEvent.VK_W:
			direction |= IConstants.DIRECTION_U;
			break;
		}
	}
	
	public void keyRelease( KeyEvent e ) {
		if ( ! alive ) return;		//do nothing for a dead tank
		switch ( e.getKeyCode() ) {
		case KeyEvent.VK_A:
			direction &= ~IConstants.DIRECTION_L;
			break;
		case KeyEvent.VK_S:
			direction &= ~IConstants.DIRECTION_D;
			break;
		case KeyEvent.VK_D:
			direction &= ~IConstants.DIRECTION_R;
			break;
		case KeyEvent.VK_W:
			direction &= ~IConstants.DIRECTION_U;
			break;
		case KeyEvent.VK_J:
			//complex mode && no moving bullets or simple mode
			if ( couldShot() ) {		//bullets limit
			Bullet nbt = BulletFactory.createHeroBullet(BF, 
						this, Bullet.NORMAL_BULLET, 0, 0, head);
			nbt.setX(x + (cols - nbt.getCols()) / 2);
			//nbt.setY(y + (rows - nbt.getRows()) / 2);
			nbt.setY(y + (rows - nbt.getRows()) / 2);
			increaseMovingBullets();
			BF.addSynBullet(nbt);
			}
			break;
		case KeyEvent.VK_K:
			if ( bomb > 0 && couldShot() ) {
				bomb--;
				Bullet bbt = BulletFactory.createHeroBullet(BF,
						this, Bullet.BOMB_BULLET, 0, 0, head);
				bbt.setX(x + (cols - bbt.getCols()) / 2);
				bbt.setY(y + (rows - bbt.getRows()) / 2);
				increaseMovingBullets();
				BF.addSynBullet(bbt);
			}
			break;
		case KeyEvent.VK_L:
			if ( missile > 0 && couldShot() ) {
				missile--;
				Bullet mbt = BulletFactory.createHeroBullet(BF, 
						this, Bullet.MISSILE_BULLET, 0, 0, head);
				mbt.setX(x + (cols - mbt.getCols()) / 2);
				mbt.setY(y + (rows - mbt.getRows()) / 2);
				increaseMovingBullets();
				BF.addSynBullet(mbt);
			}
			break;
		}
	}
}
