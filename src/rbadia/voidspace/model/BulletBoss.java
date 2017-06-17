package rbadia.voidspace.model;
import java.awt.Rectangle;
import java.util.Random;
import rbadia.voidspace.main.GameScreen;

/**
 * Represents a bullet fired by a ship.
 */
public class BulletBoss extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private int bulletBossWidth = 8;
	private int bulletBossHeight = 8;
	private int speed = 12;
	private Random rand;
	
	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param ship
	 */	
	public BulletBoss(Boss boss){
		this.setLocation(boss.x + boss.width/2 - bulletBossWidth/2,
				boss.y + boss.height);
		this.setSize(bulletBossWidth, bulletBossHeight);
	}
	

	public BulletBoss(GameScreen screen) {
		this.setLocation(
				screen.getWidth() - bulletBossWidth,
        		rand.nextInt(screen.getHeight() - bulletBossHeight - 8)
        		);
		this.setSize( bulletBossWidth, bulletBossHeight);
	}


	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
