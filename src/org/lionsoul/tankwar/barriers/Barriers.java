package org.lionsoul.tankwar.barriers;

/**
 * tank war barriers control class . <br />
 * 
 * @author chenxin<chenxin619315@gmail.com>
 */
public class Barriers {
	
	private int enemy;			/*number of enemy*/
	//direction, offset, blood, bomb, missile
	private int[] heroArgs;
	//direction, offset, blood, bomb, missile, move interval, shot interval
	private int[] enemyArgs;
	private short[][] heroBulletArgs;
	private short[][] enemyBulletArgs;
	
	/**construct method .*/
	public Barriers( int enemy, int[] heroArgs, 
			int[] enemyArgs, short[][] heroBulletArgs, short[][] enemyBulletArgs ) {
		this.enemy = enemy;
		this.heroArgs = heroArgs;
		this.enemyArgs = enemyArgs;
		this.heroBulletArgs = heroBulletArgs;
		this.enemyBulletArgs = enemyBulletArgs;
	}
	
	public int getEnemyNumber() {
		return enemy;
	}
	
	public int[] getHeroArgs() {
		return heroArgs;
	}
	
	public int[] getEnemyArgs() {
		return enemyArgs;
	}
	
	public short[][] getHeroBulletArgs() {
		return heroBulletArgs;
	}
	
	public short[][] getEnemyBulletArgs() {
		return enemyBulletArgs; 
	}
}
