package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.BulletBoss;
//import rbadia.voidspace.model.BulletBoss2;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage megaManImg;
	private BufferedImage megaFallRImg;
	private BufferedImage megaFireRImg;
	private BufferedImage floorImg;
	private BufferedImage platformImg;
	private BufferedImage bulletImg;
	private BufferedImage bulletBossImg;
	private BufferedImage bigBulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroid2Img;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage asteroid2ExplosionImg;
	private BufferedImage megaManExplosionImg;
	private BufferedImage boss1Img;
	//	private BufferedImage bossImg2;
	private BufferedImage bigAsteroidImg;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage MegaMan1Img;
	private BufferedImage MegaMan2Img;
	private BufferedImage MegaMan3Img;
	private BufferedImage MegaMan4Img;
	private BufferedImage MegaManMenuImg;
	private BufferedImage VictoryImg;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.megaManImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3.png"));
			this.megaFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.megaFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
			this.floorImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFloor.png"));
			this.platformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform3.png"));
			this.boss1Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/boss1.png"));
			//			this.bossImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/boss2.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroid2Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.asteroid2ExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			//this.megaManExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaManExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.bulletBossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));

			
			this.bigBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigBullet.png"));
			this.bigAsteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.MegaMan1Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MegaMan1.jpg"));
			this.MegaMan2Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MegaMan2.jpg"));
			this.MegaMan3Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MegaMan3.jpg"));
			this.MegaMan4Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MegaMan4.png"));
			this.MegaManMenuImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/MegaManMenu.jpg"));
			this.VictoryImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Victory.png"));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param megaMan the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */

	public void drawMegaMan (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallR (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallRImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireR (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireRImg, megaMan.x, megaMan.y, observer);	
	}

	public void drawFloor (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
			g2d.drawImage(floorImg, floor.x, floor.y, observer);				
	}
	public void drawPlatform(Platform platform, Graphics2D g2d, ImageObserver observer, int i){
			g2d.drawImage(platformImg, platform.x , platform.y, observer);	
	}
	
	public void drawPlatform2 (Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformImg, platform.x , platform.y, observer);	
}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBulletBoss(BulletBoss bulletBoss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bulletBoss.x, bulletBoss.y, observer);
	}
	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bigBullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigBullet(BigBullet bigBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigBulletImg, bigBullet.x, bigBullet.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	/**
	 * Draws an asteroid2 image to the specified graphics canvas.
	 * @param asteroid2 the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid2(Asteroid asteroid2, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroid2Img, asteroid2.x, asteroid2.y, observer);
	}
	/**
	 * Draws an asteroid2 image to the specified graphics canvas.
	 * @param boss the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBoss(Boss boss, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(boss1Img, boss.x, boss.y, observer);
	}

	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param bigAsteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawbigAsteroid(BigAsteroid bigAsteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidImg, bigAsteroid.x, bigAsteroid.y, observer);
	}
	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param megaManExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawMegaManExplosion(Rectangle megaManExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(megaManExplosionImg, megaManExplosion.x, megaManExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}
	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroid2Explosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid2Explosion(Rectangle asteroid2Explosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroid2ExplosionImg, asteroid2Explosion.x, asteroid2Explosion.y, observer);
	}
	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param bigAsteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}

	public BufferedImage getMegaMan1Img() {
		return MegaMan1Img;
	}

	public BufferedImage getMegaMan2Img() {
		return MegaMan2Img;
	}
	public BufferedImage getMegaMan3Img() {
		return MegaMan3Img;
	}
	
	public BufferedImage getMegaMan4Img(){
		return MegaMan4Img;
	}
	
	public BufferedImage getMegaManMenuImg(){
		return MegaManMenuImg;
	}
	public BufferedImage getVictoryImg(){
		return VictoryImg;
	}
	
	public BufferedImage getBoss1Img() {
		return boss1Img;
	}
	
	public BufferedImage getBulletBossImg() {
		return bulletBossImg;
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param bossExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d,ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}
}
