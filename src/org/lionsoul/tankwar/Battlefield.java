package org.lionsoul.tankwar;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.lionsoul.tankwar.barriers.Barriers;
import org.lionsoul.tankwar.barriers.BarriersSelector;
import org.lionsoul.tankwar.map.RandomWordsMap;
import org.lionsoul.tankwar.map.TMap;
import org.lionsoul.tankwar.model.Bullet;
import org.lionsoul.tankwar.model.Explosion;
import org.lionsoul.tankwar.model.Tank;
import org.lionsoul.tankwar.tank.HeroTank;
import org.lionsoul.tankwar.tank.RandomTank;
import org.lionsoul.tankwar.util.AudioLoader;
import org.lionsoul.tankwar.util.IConstants;
import org.lionsoul.tankwar.util.ImageLoader;

/**
 * battlefield for tank war . <br />
 * 
 * @author	chenxin<chenxin619315@gmail.com>
 * @version	1.0
 */
public class Battlefield extends JFrame {

	private static final long serialVersionUID = -1105359425320038750L;
	public static final int GAME_OVERED = -1;
	public static final int GAME_PUSHED = 0;
	public static final int GAME_BARRIER_SELECT = 1;
	public static final int GAME_MAP_SELECT = 2;
	public static final int GAME_RUNING = 3;
	public static final int GAME_FAILED = 4;
	public static final int GAME_SUCCEED = 5;
	
	public static final int SIMPLE_MODE = 1;
	public static final int STANDART_MODE = ~SIMPLE_MODE;
	
	
	public static Object lock = new Object();
	public static Dimension w_size = new Dimension(640, 512);
	public static ExecutorService tpool = Executors.newCachedThreadPool();
	
	//image resource
	public static Image[] background = null;
	public static Image[] heroImages = null;
	public static Image[][] enemyImages = null;
	public static Image[] bulletImages = null;
	public static Image[] wallImages = null;
	public static Image[][] explosionImages = null;
	public static Image helpImage = null;
	
	public int bgWidth = 0;
	public int bgHeight = 0;
	
	//components
	private BattleCanvas canvas = null;
	private TMap tankmap = null;
	private HeroTank hero = null;
	private HashMap<Integer, Tank> tanks = null;
	private ArrayList<Bullet> bullets = null;
	private ArrayList<Explosion> explosions = null;
	
	//user interface
	private BarriersSelector bselector = null;
	private GameResult gresult = null;
	
	private int state = GAME_BARRIER_SELECT;
	private Barriers[] barriersSetting = LionBarriers.DEFAULT;
	private int barriers = 0;
	private boolean sound = true;		//start the game sound .
	private int bg = 0;					//canvas background index
	private GameHelp gameHelp = null;
	private int mode = STANDART_MODE;
	
	//audio component
	//private AudioClip shotClip[] = null;
	private AudioClip explosionClip[] = null;
	
	//double buffer draw
	private Image bufferImage = null;
	private Graphics bufferScreen = null;
	
	public Battlefield() {
		setTitle("lion tank war");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setUndecorated(true);
		//add the battel canvas
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setResizable(false);
		canvas = new BattleCanvas();
		c.add(canvas);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * initialize the resource : <br />
	 * 1. load the hero tank resource . <br />
	 * 2. load the enemy tank resource . <br />
	 * 3. load the explosion resource. <br />
	 * 4. load the bullet resource . <br />
	 */
	public void loadResource() {
		
		bufferImage = createImage(w_size.width, w_size.height);
		bufferScreen = bufferImage.getGraphics();
		addKeyListener(new TankKeyListener());
		
		//load the background.
		ImageIcon bg = ImageLoader.loadImageIcon("bg-green.gif");
		background = new Image[] {
			bg.getImage(),
			ImageLoader.loadImageIcon("bg-grass.jpg").getImage(),
			ImageLoader.loadImageIcon("bg-sand.jpg").getImage()
		};
		bgWidth = bg.getIconWidth();
		bgHeight = bg.getIconHeight();
		
		//hero tank resource
		heroImages = new Image[] {
			ImageLoader.loadImageIcon("tank/hero/1/hero-u.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-ru.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-r.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-rd.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-d.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-ld.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-l.png").getImage(),
			ImageLoader.loadImageIcon("tank/hero/1/hero-lu.png").getImage()	
		};
		//enemy tank resource
		enemyImages = new Image[][] {
			new Image[] {
				ImageLoader.loadImageIcon("tank/enemy/1/tank-u.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-ru.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-r.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-rd.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-d.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-ld.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-l.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/1/tank-lu.png").getImage()	
			},
			new Image[] {
				ImageLoader.loadImageIcon("tank/enemy/2/tank-u.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-ru.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-r.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-rd.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-d.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-ld.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-l.png").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/2/tank-lu.png").getImage()	
			},
			new Image[] {
				ImageLoader.loadImageIcon("tank/enemy/3/tank-u.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-ru.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-r.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-rd.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-d.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-ld.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-l.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/3/tank-lu.gif").getImage()	
			},
			new Image[] {
				ImageLoader.loadImageIcon("tank/enemy/4/tank-u.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-ru.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-r.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-rd.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-d.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-ld.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-l.gif").getImage(),
				ImageLoader.loadImageIcon("tank/enemy/4/tank-lu.gif").getImage()	
			}
		};
		//bullet resource
		bulletImages = new Image[] {
			ImageLoader.loadImageIcon("bullet/normal.png").getImage(),
			ImageLoader.loadImageIcon("bullet/bomb.png").getImage(),
			ImageLoader.loadImageIcon("bullet/missile.png").getImage()	
		};
		//wall resource
		wallImages = new Image[] {
			ImageLoader.loadImageIcon("wall/grass.gif").getImage(),
			ImageLoader.loadImageIcon("wall/wood.gif").getImage(),
			ImageLoader.loadImageIcon("wall/brick.gif").getImage(),
			ImageLoader.loadImageIcon("wall/iron.gif").getImage()
		};
		//explosion resource
		explosionImages = new Image[][] {
			new Image[] {
				ImageLoader.loadImageIcon("explosion/1.png").getImage(),
				ImageLoader.loadImageIcon("explosion/2.png").getImage(),
				ImageLoader.loadImageIcon("explosion/3.png").getImage(),
				ImageLoader.loadImageIcon("explosion/4.png").getImage(),
				ImageLoader.loadImageIcon("explosion/5.png").getImage(),
				ImageLoader.loadImageIcon("explosion/6.png").getImage()
			},
			new Image[] {
				ImageLoader.loadImageIcon("explosion/big/1.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/2.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/3.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/4.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/5.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/6.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/7.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/8.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/9.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/10.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/11.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/12.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/13.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/14.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/15.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/16.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/17.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/18.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/19.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/20.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/21.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/22.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/23.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/24.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/25.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/26.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/27.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/28.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/29.png").getImage(),
				ImageLoader.loadImageIcon("explosion/big/30.png").getImage()
			}
		};
		//create help image
		gameHelp = new GameHelp(200, 200);
		
		//shot audio
		/*shotClip = new AudioClip[] {
			AudioLoader.loadAudio("shot.wav")
		};*/
		
		//explosion audio
		explosionClip = new AudioClip[] {
			AudioLoader.loadAudio("s-explosion.wav"),
			AudioLoader.loadAudio("b-explosion.wav")
		};
		
		//user interface
		bselector = new BarriersSelector(this, 1, barriersSetting.length,
				60, w_size.width, w_size.height,
				new ImageIcon[] {
				ImageLoader.loadImageIcon("barriers/title.png"),
				ImageLoader.loadImageIcon("barriers/pointer.png"),
				ImageLoader.loadImageIcon("barriers/grass.gif")},
				AudioLoader.loadAudio("barriers/done.wav")
		);
		
		short width = IConstants.X_OFFSET, height = IConstants.Y_OFFSET;
		tankmap = new TMap(w_size.height / height, w_size.width / width,
					width, height, null);
		
		canvas.setBufferImage(bufferImage);
		start();				//start the counting thread . <br />
	}
	
	public TMap getMap() {
		return tankmap;
	}
	
	/**
	 * start to play the game . <br />
	 * 1. clear the old components . <br />
	 * 2. create the battlefield components 
	 * 			with the given barriers index . <br />
	 * 3. change the state of the battlefiled to GAME_RUNING . <br />
	 */
	public void play( int idx ) {
		gresult = null;				//reset the game result user interface
		hero = null;				//clear the hero
		if ( tanks != null ) tanks.clear();
		if ( bullets != null ) bullets.clear();
		if ( explosions != null ) explosions.clear();
		tankmap.clear();			//clear the map
		System.gc();				//start the garbage collection
		bselector.stopAudio();		//stop the barriers button audio
		create( barriersSetting[idx] );
		state = GAME_RUNING;
	}
	
	/**
	 * initialize the battlefield: <br />
	 * 1. create the hero tank . <br />
	 * 2. create the enemy tank . <br />
	 * 3. create the explosion . <br />
	 * 4. create some bullets pool . <br />
	 * 
	 * @param	barrier
	 */
	private void create( Barriers barrier ) {	
		
		//get the random background
		bg = ( (int) (Math.random() * 10000) ) % background.length;
		
		//hero tank
		int width = IConstants.T_WIDTH, height = IConstants.T_WIDTH;
		tanks = new HashMap<Integer, Tank>(16, 0.85F);
		TankFactory.reset();
		hero = TankFactory.createCenterHeroTank(this, width, height, barrier.getHeroArgs());
		tanks.put(hero.getSerial(), hero);
		
		//enemy tank
		width = IConstants.T_WIDTH; height = IConstants.T_WIDTH;
		int tanknum = TankFactory.DEFAULT_TANK_OUTPUT;
		if ( barrier.getEnemyNumber() < tanknum ) tanknum = barrier.getEnemyNumber();
		RandomTank etank;
		for ( int j = 0; j < tanknum; j++ ) {
			etank = TankFactory.createRandomTank(this, width, height, barrier.getEnemyArgs());
			tanks.put(etank.getSerial(), etank);
		}
		
		//set the walls
		tankmap.setWalls(RandomWordsMap.generate(
				wallImages, 8, 8,
				tankmap.getCols() - 2 * hero.getCols(),
				tankmap.getRows() - 2 * hero.getRows(), hero.getCols(), hero.getRows() ));
		
		//bullets
		BulletFactory.heroDamage = barrier.getHeroBulletArgs();
		BulletFactory.enemyDamage = barrier.getEnemyBulletArgs();
		bullets = new ArrayList<Bullet>();
		
		//explosion
		explosions = new ArrayList<Explosion>();
	}
	
	/**
	 * start the game
	 * 		by start a counting thread . <br />
	 */
	private void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while ( true ) {
					if ( state == GAME_OVERED ) break;
					if ( state == GAME_PUSHED ) {
						synchronized ( lock ) {
							try {lock.wait();} catch (InterruptedException e) {break;}
						}
					}
		
					//sleep for moment
					try {Thread.sleep(30);} catch (InterruptedException e) {break;}
					/*if ( state == GAME_FAILED || state == GAME_SUCCEED ) {
						continue;
					}*/
					
					switch ( state ) {
					case GAME_BARRIER_SELECT: bselector.draw(bufferScreen); break;
					case GAME_MAP_SELECT:	break;
					case GAME_RUNING: runningDraw(); break;
					case GAME_FAILED:
					case GAME_SUCCEED:
						if ( gresult != null ) gresult.draw(bufferScreen);
						break;
					}
					
					//draw the help window
					if ( gameHelp.isVisible() ) 
						bufferScreen.drawImage(gameHelp.getHelpImage(),
							(w_size.width - gameHelp.getWidth()) / 2,
							(w_size.height - gameHelp.getHeight()) / 2, null);
					
					//refresh the canvas
					canvas.repaint();
				}
			}
		}).start();
	}
	
	/**
	 * create a game result tip user interface . <br />
	 * 
	 * @param	ret
	 * @return	GameResult
	 */
	public GameResult createGR( int ret ) {
		ImageIcon icon = (ret == GAME_SUCCEED) ? ImageLoader.loadImageIcon("barriers/grass.gif")
				: ImageLoader.loadImageIcon("barriers/sand.gif");
		return new GameResult(w_size.width, w_size.height, ret, 2, icon);
	}
	
	/**
	 * draw the battlefield . <br /> 
	 */
	public void runningDraw() {
		//draw the background.
		drawBackground(bufferScreen);
		
		//hero tank
		if ( hero.isAlive() ) hero.draw(bufferScreen);
		else {
			state = GAME_FAILED;
			gresult = createGR(GAME_FAILED);
		}
			
		//enemy tank
		if ( tanks.size() == 1 ) {		//only the hero tank left
			state = GAME_SUCCEED;
			gresult = createGR(GAME_SUCCEED);
		}
		Iterator<Entry<Integer, Tank>> eit = tanks.entrySet().iterator();
		Tank etank;
		boolean _new = false;
		while ( eit.hasNext() ) {
			Entry<Integer, Tank> e = eit.next();
			etank = e.getValue();
			if ( ! etank.isAlive() ) {
				eit.remove();			//the tank is dead
				_new = true;
			}
			else etank.draw(bufferScreen);
		}
		if ( _new ) {	//add new tank
			if ( TankFactory.getTankOutput() 
					< barriersSetting[bselector.getBarriers() - 1].getEnemyNumber() ) {
				etank = TankFactory.createRandomTank(this, IConstants.T_WIDTH, IConstants.T_WIDTH, barriersSetting[bselector.getBarriers() - 1].getEnemyArgs());
				tanks.put(etank.getSerial(), etank);
			}
		}
		
		//bullets
		synchronized ( bullets ) {
			Iterator<Bullet> bit = bullets.iterator();
			Bullet btemp;
			while ( bit.hasNext() ) {
				btemp = bit.next();
				if ( ! btemp.isAlive() ) bit.remove();
				else btemp.draw(bufferScreen);
			}
		}
		
		//draw the map (after the tanks and before the explosion)
		tankmap.draw(bufferScreen);
		
		//explosion
		Iterator<Explosion> expit = explosions.iterator();
		Explosion exp;
		while ( expit.hasNext() ) {
			exp = expit.next();
			if ( exp.isAlive() ) exp.draw(bufferScreen);
			else expit.remove();
		}
		
		//draw the game info
		drawGameInfo(bufferScreen);
	}
	
	/**
	 * draw the game information . <br /> 
	 */
	private void drawGameInfo( Graphics g ) {
		String str = (mode<0?"Standard":"Simple")+" mode, Enemy:"+
				(barriersSetting[bselector.getBarriers() - 1]
						.getEnemyNumber() - TankFactory.getTankOutput() + tanks.size() - 1)
				+", Press h for Help";
		g.setFont(IConstants.iFont);
		g.setColor(IConstants.iColor);
		g.drawString(str,
			w_size.width - g.getFontMetrics().stringWidth(str) - 10,
			w_size.height - IConstants.iFont.getSize());
	}
	
	/**
	 * get the tank by a serial . <br />
	 * 
	 * @param	serial
	 */
	public Tank getTankBySerial( int serial ) {
		return tanks.get(serial);
	}
	
	/**
	 * add bullet to the battlefield in a synchronized block. <br />
	 * 
	 * @param	e
	 */
	public void addSynBullet( final Bullet e ) {
		synchronized ( bullets ) {
			bullets.add(e);
		}
		//start the shot sound
		/*if ( isSoundOpen() ) {
			tpool.execute(new Runnable() {
				@Override
				public void run() {
					shotClip[0].stop();
					shotClip[0].play();
				}
			});
		}*/
	}
	
	/**
	 * add a bullet to the battlefield . <br />
	 * 
	 * @param	e
	 */
	public void addBullet( final Bullet e ) {
		bullets.add(e);
	}
	
	/**
	 * add explosion to the battlefield . <br /> 
	 * 
	 * @param	exp
	 */
	public void addExplosion( final Explosion exp ) {
		explosions.add(exp);
		if ( isSoundOpen() ) {
			tpool.execute(new Runnable(){
				@Override
				public void run() {
					explosionClip[exp.getType()].play();
				}
			});
		}
	}
	
	/**
	 * draw the background . <br /> 
	 */
	public void drawBackground( Graphics g ) {
		int x = 0, y = 0;
		do {
			bufferScreen.drawImage(background[bg], x, y, this);
			x += bgWidth;
			if ( x >= getWidth() ) {
				x = 0;
				y += bgHeight;
			}
			if ( y >= getHeight() ) break;
		} while ( true );
	}
	
	/**
	 * hero tank key listener class . <br /> 
	 */
	private class TankKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			//esc response
			if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
				if ( state == GAME_PUSHED ) synchronized (lock) {lock.notify();}
				state = GAME_BARRIER_SELECT;
				return;
			}
			
			//start/stop witch
			if ( e.getKeyCode() == KeyEvent.VK_P ) {
				switch ( state ) {
				case GAME_PUSHED:		//stop, make it start
					synchronized ( lock ) {
						lock.notify();
						state = GAME_RUNING;
					}
					break;
				case GAME_RUNING: state = GAME_PUSHED; break;
				}
				return;
			}
			
			//game help window
			if ( e.getKeyCode() == KeyEvent.VK_H ) {
				gameHelp.setVisible(!gameHelp.isVisible());
			}
			
			switch ( state ) {
			case GAME_BARRIER_SELECT: bselector.keyPress(e); break;
			case GAME_MAP_SELECT: break;
			case GAME_RUNING:	
				if ( e.getKeyCode() == KeyEvent.VK_M ) {
					mode = ~mode;
					break;
				}
				hero.keyPress(e); 
				break;
			case GAME_FAILED:
				if ( e.getKeyCode() == KeyEvent.VK_R ) 
					play(bselector.getBarriers() - 1);
				break;
			case GAME_SUCCEED:
				if ( e.getKeyCode() == KeyEvent.VK_N && bselector.hasNext() ) {
					play(bselector.next() - 1);
				}
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {			
			switch ( state ) {
			case GAME_RUNING:	hero.keyRelease(e); break;
			}
		}
	}
	
	/**
	 * check if the game sound is start . <br /> 
	 * 
	 * @return	boolean
	 */
	public boolean isSoundOpen() {
		return sound;
	}
	
	/**
	 * set the game sound state . <br />
	 * 
	 * @param	sound
	 */
	public void setSound( boolean sound ) {
		this.sound = sound;
	}
	
	/**get the current game mode*/
	public int getGameMode() {
		return mode;
	}
	public void setGameMode( int mode ) {
		this.mode = mode;
	}
	
	/**
	 * set the current barrier . <br />
	 * 
	 * @param	barriers
	 */
	public void setBarriers( int barriers ) {
		if ( barriers < barriersSetting.length ) this.barriers = barriers;
	}
	
	public int getBarriers() {
		return barriers;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Battlefield battle = new Battlefield();
		battle.setVisible(true);
		battle.loadResource();
	}
}
