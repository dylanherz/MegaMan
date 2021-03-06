package rbadia.voidspace.main;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.BulletBoss;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends BaseScreen{
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_ASTEROID_2_DELAY = 500;
	private static final int NEW_BIG_ASTEROID_DELAY = 500;
	private static final int NEW_BOSS_DELAY = 500;
	private static final int NEW_BULLET_BOSS_DELAY = 500;

	//	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastAsteroid2Time;
	private long lastBigAsteroidTime;
	private long lastBossTime;
	private long lastBulletTime;

	private Rectangle asteroidExplosion;
	private Rectangle bigAsteroidExplosion;
	private Rectangle asteroid2Explosion;
	//	private Rectangle shipExplosion;
	private Rectangle bossExplosion;

	private JLabel LivesValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel levelValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;
	//private InputHandler input;
	private Platform[] platforms;

	private int boom=0;
	private int level=1;
	private int damage=0;
	private int bigAsteroidPos = 0;


	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	protected void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		MegaMan megaMan = gameLogic.getMegaMan();
		Floor[] floor = gameLogic.getFloor();
		Platform[] numPlatforms = gameLogic.getNumPlatforms();
		List<Bullet> bullets = gameLogic.getBullets();
		Asteroid asteroid = gameLogic.getAsteroid();
		List<BigBullet> bigBullets = gameLogic.getBigBullets();
		Asteroid asteroid2 = gameLogic.getAsteroid2();
		BigAsteroid bigAsteroid = gameLogic.getBigAsteroid();
		List<BulletBoss> bulletsBoss = gameLogic.getBulletBoss();
		//		List<BulletBoss2> bulletsBoss2 = gameLogic.getBulletBoss2();		
		Boss boss = gameLogic.getBoss();
		//		Boss boss2 = gameLogic.getBoss2();

		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars && background pictures in each level
		if(status.getLevel() == 1){
			g2d.drawImage(graphicsMan.getMegaMan1Img(), null, 0, 0);
			drawStars(50);
		}
		if(status.getLevel() == 2){
			g2d.drawImage(graphicsMan.getMegaMan2Img(), null, 0, 0);
			drawStars(50);
		}
		if(status.getLevel() == 3){
			g2d.drawImage(graphicsMan.getMegaMan3Img(), null, 0, 0);
			drawStars(50);
		}
		if(status.getLevel() == 4){
			g2d.drawImage(graphicsMan.getMegaMan4Img(), null, 0, 0);
			drawStars(50);
		}
		if(status.getLevel() == 5) {
			victory();
//			status.setGameStarted(true);
//			gameLogic.gameOver();
			drawStars(50);
		}
		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastAsteroid2Time) < NEW_ASTEROID_2_DELAY){
				graphicsMan.drawAsteroid2Explosion(asteroid2Explosion, g2d, this);
			}
			if((currentTime - lastBigAsteroidTime) < NEW_BIG_ASTEROID_DELAY){
				graphicsMan.drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);
			}
			if((currentTime - lastBossTime) < NEW_BOSS_DELAY){
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}
			return;
		}

		//if the game is won, draw the "You Win!!!" message
		if(status.isGameWon()){
			// draw the message
			drawYouWin();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastAsteroid2Time) < NEW_ASTEROID_2_DELAY){
				graphicsMan.drawAsteroid2Explosion(asteroid2Explosion, g2d, this);
			}
			if((currentTime - lastBigAsteroidTime) < NEW_BIG_ASTEROID_DELAY){
				graphicsMan.drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);
			}
			if((currentTime - lastBossTime) < NEW_BOSS_DELAY){
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}
			return;
		}

		if(status.isGameWon()){
			
		}
		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}
		
		

		//draw Floor
		for(int i=0; i<9; i++){
			graphicsMan.drawFloor(floor[i], g2d, this, i);	
		}
		
		for(int i=0; i<8; i++){
				graphicsMan.drawPlatform(numPlatforms[i], g2d, this, i);
			}	

		//draw MegaMan
		if(!status.isNewMegaMan()){
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))){
				graphicsMan.drawMegaFallR(megaMan, g2d, this);
			}
		}

		if((Fire() == true || Fire2()== true) && (Gravity()==false)){
			graphicsMan.drawMegaFireR(megaMan, g2d, this);
		}

		if((Gravity()==false) && (Fire()==false) && (Fire2()==false)){
			graphicsMan.drawMegaMan(megaMan, g2d, this);
		}
		
		//Implementation of methods depending in level
		if(status.getLevel() == 1 || status.getLevel() == 2){
			smallAsteroid(asteroid);
		}
		if (status.getLevel() == 2 || status.getLevel() == 3){
			smallAsteroid2(asteroid2);
		}
		if(status.getLevel() == 3){
			bigAsteroid(bigAsteroid);
		}
		if(status.getLevel() == 4){
			Boss(boss);
		}
		if(status.getLevel() == 5 || boom == 26){
			victory();
//			status.setGameStarted(true);
//			gameLogic.gameOver();
			
		}
		
		// draw bullets   
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}

		// draw big bullets
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			graphicsMan.drawBigBullet(bigBullet, g2d, this);

			boolean remove = gameLogic.moveBigBullet(bigBullet);
			if(remove){
				bigBullets.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < bulletsBoss.size(); i++){
			BulletBoss bulletBoss = bulletsBoss.get(i);
			graphicsMan.drawBulletBoss(bulletBoss, g2d, this);
			
			boolean remove = gameLogic.moveBulletBoss(bulletBoss);
			if(remove){
				bulletsBoss.remove(i);
				i--;
			}
		}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);

				removeAsteroid(asteroid);
				
				boom=boom + 1;
				
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			
			if(asteroid2.intersects(bullet)){
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);

				removeAsteroid2(asteroid2);

				boom=boom + 1;
				
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;	
			}
		}
		for(int i=0; i<bullets.size(); i++){
		Bullet bullet = bullets.get(i);
		if(bigAsteroid.intersects(bullet)) {
			//  increase big asteroids destroyed count
			bigAsteroid.setDamage(bigAsteroid.getDamage() + 1);
			if(bigAsteroid.getHealth() <= bigAsteroid.getDamage()){
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 300);
				
				removeBigAsteroid(bigAsteroid);
				
				boom = boom + 1;
				bigAsteroid.setDamage(0);
			}
			bullets.remove(i);
			break;
			}
		}
		
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(boss.intersects(bullet)) {
				//  increase boss destroyed count
				boss.setBossDamage(boss.getBossDamage() + 1);
				if(boss.getBossHealth() <= boss.getBossDamage()){
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 5000);
					
					removeBoss(boss);
					
					boom = boom + 1;
					boss.setBossDamage(0);
				}
				bullets.remove(i);
				break;
			}
		}

		// check big bullet-asteroid collisions
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(asteroid.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);

				removeAsteroid(asteroid);

					boom=boom + 1;
				
					bigBullets.remove(i);
					break;
			}
		}
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(asteroid2.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);

				removeAsteroid2(asteroid2);

					boom=boom + 1;
				
					bigBullets.remove(i);
					break;
				}
			}
		
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(bigAsteroid.intersects(bigBullet)) {
				//  increase big asteroids destroyed count
				bigAsteroid.setDamage(bigAsteroid.getDamage() + 2);
				if(bigAsteroid.getHealth() <= bigAsteroid.getDamage()){
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 400);
					
					removeBigAsteroid(bigAsteroid);
					
					boom = boom + 1;
					bigAsteroid.setDamage(0);
				}
				
				bigBullets.remove(i);
				break;
			}
		}
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(boss.intersects(bigBullet)) {
				//  increase boss destroyed count
				boss.setBossDamage(boss.getBossDamage() + 1);
				if(boss.getBossHealth() <= boss.getBossDamage()){
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 5000);
						
					removeBoss(boss);
						
					boom = boom + 1;
					boss.setBossDamage(0);
					}
					bullets.remove(i);
					break;
			}
		}
		

		//MM-Asteroid collision
		if(asteroid.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid(asteroid);
		}
		if(asteroid2.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid2(asteroid2);
		}
		if(bigAsteroid.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeBigAsteroid(bigAsteroid);
		}
		if(boss.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
		for(int i = 0; i < bulletsBoss.size(); i ++){
		if(bulletsBoss.get(i).intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			bulletsBoss.remove(i);
			}
		}
		//Asteroid-Floor collision
		for(int i=0; i<9; i++){
			if(asteroid.intersects(floor[i])){
				removeAsteroid(asteroid);
			}
		}
		for(int i=0; i<9; i++){
			if(asteroid2.intersects(floor[i])){
				removeAsteroid2(asteroid2);
			}
		}
		for(int i=0; i<9; i++){
		if(bigAsteroid.intersects(floor[i])){
			removeBigAsteroid(bigAsteroid);	
			}
		}
		
		System.out.println("Boom = " + boom);
		System.out.println("Level = " + status.getLevel());
		
		//Restructure methods depending in boom quantity
		if(boom == 5){
			restructure();
			status.setLevel(status.getLevel() + 1);
			boom = boom++;
		}
		if(boom == 12){
			restructure();
			status.setLevel(status.getLevel() + 1);
			boom = boom++;
		}
		if(boom == 24){
			restructure();
			status.setLevel(status.getLevel() + 1);
			boom = boom++;
		}
		
		if(boom == 26){
			restructure();
			status.setLevel(status.getLevel() + 1);
		}
		
		status.getAsteroidsDestroyed();
		status.getLivesLeft();
		status.getLevel();

		// update asteroids destroyed label  
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
		
		// update Lives left label
		LivesValueLabel.setText(Integer.toString(status.getLivesLeft()));

		//update level label
		levelValueLabel.setText(Long.toString(status.getLevel()));
		
}
	// Method for bigAsteroid creation
	private void bigAsteroid(BigAsteroid bigAsteroid) {
		// LEVEL 3
		if (!status.isNewBigAsteroid() &&  boom > 12 &&  boom <= 24){
			if((bigAsteroid.getX() + bigAsteroid.getBigAsteroidWidth() > 0)){
				bigAsteroid.translate(-bigAsteroid.getSpeed2()*2,(bigAsteroidPos));
				bigAsteroidPos++;
				if(bigAsteroidPos == 7){
					bigAsteroidPos = -7;
					System.out.println("testing if");
				}
				graphicsMan.drawbigAsteroid(bigAsteroid, g2d, this);
			}
			else if(boom >= 5) {
				bigAsteroid.setLocation(((this.getWidth() - bigAsteroid.getBigAsteroidWidth())),
						rand.nextInt(this.getHeight() - bigAsteroid.getBigAsteroidHeight() - 64));
				}
			}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBigAsteroidTime) > NEW_BIG_ASTEROID_DELAY){
				// draw a new asteroid
				lastBigAsteroidTime = currentTime;
				status.setNewBigAsteroid(false);
				bigAsteroid.setLocation((this.getWidth() - bigAsteroid.getBigAsteroidWidth()),
						rand.nextInt(this.getHeight() - bigAsteroid.getBigAsteroidHeight() - 64));		
			}
			else{
					// draw explosion
					graphicsMan.drawBigAsteroidExplosion(bigAsteroidExplosion, g2d, this);
				}
			}
}
	//Method for smallAsteroid creation
	private void smallAsteroid(Asteroid asteroid) {
		if(!status.isNewAsteroid() && boom <= 5){

			//LEVEL 1
			if((asteroid.getX() + asteroid.getAsteroidWidth() >  0 && (boom<=8))){
				int asteroidPos = 0;
				if(this.getWidth() > 200){
				asteroid.setSpeed(asteroid.getSpeed() + asteroidPos );
				asteroid.translate(-asteroid.getSpeed(), rand.nextInt(asteroid.getSpeed()));
				asteroidPos++;
				graphicsMan.drawAsteroid(asteroid, g2d, this);	
				}
				else if(this.getWidth() > 200){
					asteroid.setSpeed(asteroid.getSpeed() - asteroidPos );
					asteroid.translate(-asteroid.getSpeed(), rand.nextInt(asteroid.getSpeed()));
					asteroidPos--;
					graphicsMan.drawAsteroid(asteroid, g2d, this);	
					}
			}
			else if (boom <= 8){
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
				}	
			}

		//LEVEL 2
		else if(!status.isNewAsteroid() && boom > 5 && boom <= 12){
			if((asteroid.getX() + asteroid.getAsteroidWidth() >  0)) {
				asteroid.translate(-asteroid.getSpeed(), (asteroid.getSpeed())/4);
				asteroid.translate(-asteroid.getRandomSpeed(), -asteroid.getRandomSpeed()/4);
				graphicsMan.drawAsteroid(asteroid, g2d, this);	
			}
			else if (boom > 5){
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
			}	
		
		}
		
		//LEVEL 3
		else if (!status.isNewAsteroid() && boom > 12 && boom <= 24){
			if((asteroid.getX() + asteroid.getAsteroidWidth() >0)){
				asteroid.translate(-asteroid.getSpeed(), -(asteroid.getSpeed()/2));
				graphicsMan.drawAsteroid(asteroid, g2d, this);
			}
			else if(boom > 5) {
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(), 
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() -32));
				}
			}
		
		//LEVEL 4
		else if (!status.isNewAsteroid() && boom > 24){
			if((asteroid.getX() + asteroid.getAsteroidWidth() >0)){
				asteroid.translate(-asteroid.getSpeed(), -(asteroid.getSpeed()/2));
				graphicsMan.drawAsteroid(asteroid, g2d, this);
			}
			else if(boom > 5) {
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(), 
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() -32));
				}
			}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(this.getWidth() - asteroid.getAsteroidWidth(),
						rand.nextInt(this.getHeight() - asteroid.getAsteroidHeight() - 32));
			}
		else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}
	//Method for smallAsteroid2 creation
	private void smallAsteroid2(Asteroid asteroid2) {
		if(!status.isNewAsteroid2() && boom <= 5){
			
			//LEVEL 1
			if((asteroid2.getX() + asteroid2.getAsteroid2Width() >  0 && boom <= 8 || boom == 18)){
				asteroid2.translate(-asteroid2.getSpeed(), asteroid2.getSpeed()/2);
				graphicsMan.drawAsteroid2(asteroid2, g2d, this);	
			}
			else if (boom <= 8){
				asteroid2.setLocation(this.getWidth() - asteroid2.getAsteroid2Width(),
						rand.nextInt(this.getHeight() - asteroid2.getAsteroid2Height() - 32));
				}	
			}

		//LEVEL 2
		else if(!status.isNewAsteroid2() && boom > 5 && boom <= 12){
			if((asteroid2.getX() + asteroid2.getAsteroid2Width() >  0)) {
				asteroid2.translate(-asteroid2.getRandomSpeed2(), -(asteroid2.getRandomSpeed2()/4));
				graphicsMan.drawAsteroid2(asteroid2, g2d, this);	
			}
			else if (boom > 5){
				asteroid2.setLocation(this.getWidth() - asteroid2.getAsteroid2Width(),
						rand.nextInt(this.getHeight() - asteroid2.getAsteroid2Height() - 32));
			}	
		
		}
		
		//LEVEL 3
		else if (!status.isNewAsteroid2() && boom > 12 && boom <= 24){
			if((asteroid2.getX() + asteroid2.getAsteroid2Width() >0)){
				asteroid2.translate(-asteroid2.getSpeed(), (asteroid2.getSpeed()/2));
				graphicsMan.drawAsteroid2(asteroid2, g2d, this);
			}
			else if(boom > 5) {
				asteroid2.setLocation(this.getWidth() - asteroid2.getAsteroid2Width(), 
						rand.nextInt(this.getHeight() - asteroid2.getAsteroid2Height() -32));
				}
			}
		//LEVEL 4
		else if (!status.isNewAsteroid2() && boom > 24){
			if((asteroid2.getX() + asteroid2.getAsteroid2Width() >0)){
				asteroid2.translate(-asteroid2.getSpeed(), (asteroid2.getSpeed()/2));
				graphicsMan.drawAsteroid2(asteroid2, g2d, this);
			}
			else if(boom > 5) {
				asteroid2.setLocation(this.getWidth() - asteroid2.getAsteroid2Width(), 
						rand.nextInt(this.getHeight() - asteroid2.getAsteroid2Height() -32));
				}
			}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroid2Time) > NEW_ASTEROID_2_DELAY){
				// draw a new asteroid
				lastAsteroid2Time = currentTime;
				status.setNewAsteroid2(false);
				asteroid2.setLocation(this.getWidth() - asteroid2.getAsteroid2Width(),
						rand.nextInt(this.getHeight() - asteroid2.getAsteroid2Height() - 32));
			}	
		else{
				// draw explosion
				graphicsMan.drawAsteroid2Explosion(asteroid2Explosion, g2d, this);
			}
		}
	}
	
	//Method for Boss creation
	private void Boss(Boss boss) {
	
		//LEVEL 4
		if (!status.isNewBoss() && boom > 24){
			if((boss.getX() + boss.getBossWidth() >0)){
				if(boss.getY() > 300){
					boss.setSpeed(-boss.getSpeed3());
				}
				else if(boss.getY() < 0){
					boss.setSpeed(-boss.getSpeed3());
				}
				boss.translate(0, (boss.getSpeed3()));
				graphicsMan.drawBoss(boss, g2d, this);
			}
			else if(boom <= 26) {
				boss.setLocation(this.getWidth() - boss.getBossWidth(), 
						rand.nextInt(this.getHeight() - boss.getBossHeight() -83));
				}
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBulletTime) > 1000 / 1.2){
				lastBulletTime = currentTime;
				gameLogic.fireBulletBoss(boss);
				System.out.println("testing boss shoot");
			}
		}
		
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastBossTime) > NEW_BOSS_DELAY){
				// draw a new asteroid
				lastBossTime = currentTime;
				status.setNewBoss(false);
				boss.setLocation(this.getWidth() - boss.getBossWidth(),
				rand.nextInt(this.getHeight() - boss.getBossHeight() - 83));
			}
		else{
				// draw explosion
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}
		}
	}

	/**
	 * Draws the "Game Over" message.
	 */
	protected void drawGameOver() {
		String gameOverStr = "GAME OVER";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D() - 20;
		bigFont = currentFont.deriveFont(fontSize + 10).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);

		boomReset();
		healthReset();
		delayReset();
	}

	/*
	 * Draw drawYouWin message
	 */
	protected void drawYouWin() {
		if(status.getLevel() == 2){
		String youWinStr = "Level 2";
		g2d.drawImage(graphicsMan.getMegaMan2Img(), null, 0, 0);
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(youWinStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(youWinStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(youWinStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Next level starting soon";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 40;
		g2d.setPaint(Color.CYAN);
		g2d.drawString(newGameStr, strX, strY);

		//Switch boom quantity to one more in order for the next level to start
		if(boom == 5){
			boom = 6;
		}
		if(boom == 12){
			boom = 13;
		}
		if(boom == 24){
			boom = 25;
		}
		if (boom == 26){
			boom = 27;
		}
	}
		if(status.getLevel() == 3){
			String youWinStr = "Level 3";
			g2d.drawImage(graphicsMan.getMegaMan3Img(), null, 0, 0);
			Font currentFont = biggestFont == null? bigFont : biggestFont;
			float fontSize = currentFont.getSize2D();
			bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
			FontMetrics fm = g2d.getFontMetrics(bigFont);
			int strWidth = fm.stringWidth(youWinStr);
			if(strWidth > this.getWidth() - 10){
				biggestFont = currentFont;
				bigFont = biggestFont;
				fm = g2d.getFontMetrics(bigFont);
				strWidth = fm.stringWidth(youWinStr);
			}
			int ascent = fm.getAscent();
			int strX = (this.getWidth() - strWidth)/2;
			int strY = (this.getHeight() + ascent)/2;
			g2d.setFont(bigFont);
			g2d.setPaint(Color.YELLOW);
			g2d.drawString(youWinStr, strX, strY);

			g2d.setFont(originalFont);
			fm = g2d.getFontMetrics();
			String newGameStr = "Next level starting soon";
			strWidth = fm.stringWidth(newGameStr);
			strX = (this.getWidth() - strWidth)/2;
			strY = (this.getHeight() + fm.getAscent())/2 + ascent + 40;
			g2d.setPaint(Color.CYAN);
			g2d.drawString(newGameStr, strX, strY);

			//Switch boom quantity to one more in order for the next level to start
			if(boom == 5){
				boom = 6;
			}
			if(boom == 12){
				boom = 13;
			}
			if(boom == 24){
				boom = 25;
			}
			
			if (boom == 26){
				boom = 27;
			}
		}
		if(status.getLevel() == 4){
			String youWinStr = "Boss Challenge";
			g2d.drawImage(graphicsMan.getMegaMan4Img(), null, 0, 0);
			Font currentFont = biggestFont == null? bigFont : biggestFont;
			float fontSize = currentFont.getSize2D() - 90;
			bigFont = currentFont.deriveFont(fontSize).deriveFont(Font.BOLD);
			FontMetrics fm = g2d.getFontMetrics(bigFont);
			int strWidth = fm.stringWidth(youWinStr);
			if(strWidth > this.getWidth() - 10){
				biggestFont = currentFont;
				bigFont = biggestFont;
				fm = g2d.getFontMetrics(bigFont);
				strWidth = fm.stringWidth(youWinStr);
			}
			int ascent = fm.getAscent();
			int strX = (this.getWidth() - strWidth)/2;
			int strY = (this.getHeight() + ascent)/2;
			g2d.setFont(bigFont);
			g2d.setPaint(Color.YELLOW);
			g2d.drawString(youWinStr, strX, strY);

			g2d.setFont(originalFont);
			fm = g2d.getFontMetrics();
			String newGameStr = "Next level starting soon";
			strWidth = fm.stringWidth(newGameStr);
			strX = (this.getWidth() - strWidth)/2;
			strY = (this.getHeight() + fm.getAscent())/2 + ascent + 40;
			g2d.setPaint(Color.CYAN);
			g2d.drawString(newGameStr, strX, strY);

			//Switch boom quantity to one more in order for the next level to start
			if(boom == 5){
				boom = 6;
			}
			if(boom == 12){
				boom = 13;
			}
			if(boom == 24){
				boom = 25;
			}
			if (boom == 26){
				boom = 27;
			}
		}
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	protected void drawGetReady() {
		String readyStr = "Get Ready!"; 
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 40));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent - 300)/2;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	protected void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	protected void initialMessage() {
		String gameTitleStr = "Definitely Not";
		g2d.drawImage(graphicsMan.getMegaManMenuImg(), null, 0, -70);
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 100){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - 137;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 30;
		g2d.setPaint(Color.ORANGE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Press <I> for Item Menu.";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Press <S> for Shop Menu.";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		LivesValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastAsteroid2Time = -NEW_ASTEROID_2_DELAY;
		lastBigAsteroidTime = -NEW_BIG_ASTEROID_DELAY;
		lastBossTime = -NEW_BOSS_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		LivesValueLabel.setForeground(Color.BLACK);
		LivesValueLabel.setText(Integer.toString(status.getLivesLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
		levelValueLabel.setText(Long.toString(status.getLevel()));
	}

	/*
	 * SETTERS && GETTERS
	 */
	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param LivesValueLabel the label to set
	 */
	public void setLivesValueLabel(JLabel LivesValueLabel) {
		this.LivesValueLabel = LivesValueLabel;
	}

	public void setLevelValueLabel(JLabel levelValueLabel){
		this.levelValueLabel = levelValueLabel;
	}

	public int getBoom(){
		return boom;
	}
	
	public void setBoom(int numBooms){
		boom = numBooms;
	}
	public int boomReset(){
		boom= 0;
		return boom;
	}
	public long healthReset(){
		boom= 0;
		return boom;
	}
	public long delayReset(){
		boom= 0;
		return boom;
	}

	
	protected boolean Gravity(){
		MegaMan megaMan = gameLogic.getMegaMan();
		Floor[] floor = gameLogic.getFloor();

		for(int i=0; i<9; i++){
			if((megaMan.getY() + megaMan.getMegaManHeight() -17 < this.getHeight() - floor[i].getFloorHeight()/2) 
					&& Fall() == true){

				megaMan.translate(0 , 2);
				return true;

			}
		}
		return false;
	}
	//Bullet fire pose
	protected boolean Fire(){
		MegaMan megaMan = gameLogic.getMegaMan();
		List<Bullet> bullets = gameLogic.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((bullet.getX() > megaMan.getX() + megaMan.getMegaManWidth()) && 
					(bullet.getX() <= megaMan.getX() + megaMan.getMegaManWidth() + 60)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean BossFire(){
		Boss boss = gameLogic.getBoss();
		List<BulletBoss> bulletsBoss = gameLogic.getBulletBoss();
		for (int i=0; i<bulletsBoss.size(); i++) {
			BulletBoss bulletboss = bulletsBoss.get(i);
			if((bulletboss.getX() < boss.getX() + boss.getBossWidth()) && 
				(bulletboss.getX() >= boss.getX() + boss.getBossWidth() + 60)) {
					return true;
				}
		}
		
		return false;
	}

	//BigBullet fire pose
	protected boolean Fire2(){
		MegaMan megaMan = gameLogic.getMegaMan();
		List<BigBullet> bigBullets = gameLogic.getBigBullets();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if((bigBullet.getX() > megaMan.getX() + megaMan.getMegaManWidth()) && 
					(bigBullet.getX() <= megaMan.getX() + megaMan.getMegaManWidth() + 60)){
				return true;
			}
		}
		return false;
	}

	//Platform Gravity
	public boolean Fall(){
		MegaMan megaMan = gameLogic.getMegaMan(); 
		Platform[] platform = gameLogic.getNumPlatforms();
		for(int i=0; i<8; i++){
			if((((platform[i].getX() < megaMan.getX()) && (megaMan.getX()< platform[i].getX() + platform[i].getPlatformWidth()))
					|| ((platform[i].getX() < megaMan.getX() + megaMan.getMegaManWidth()) 
							&& (megaMan.getX() + megaMan.getMegaManWidth()< platform[i].getX() + platform[i].getPlatformWidth())))
					&& megaMan.getY() + megaMan.getMegaManHeight() == platform[i].getY()
					){
				return false;
			}
		}
		return true;
	}

	/*
	 * Restructure method for level 2
	 */
	public void restructure(){
		Platform[] platform = gameLogic.getNumPlatforms();
		for(int i=0; i<8; i++){
			if(status.getLevel() == 2 || boom == 5 && boom <= 12){
			if(i<4)	platform[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platform[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int n=4;
				platform[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-n)*40 );
				n=n+2;
			
			System.out.println("Testing = " + n);
				}
			}
		}
		System.out.println("BEFORE " + (status.getLevel() - 1));
		
		System.out.println("AFTER " + status.getLevel());
		
		//LEVEL 3
		 for(int i=0; i<8; i++){
		 	if(status.getLevel() == 3 || boom == 12 &&  boom <= 24){
		 		if(i== 0 || i==7) platform[i].setLocation(122 + i*30, getHeight()/2 -50);
		 		if(i== 1 || i== 6)	platform[i].setLocation(50 + i*50, getHeight()/2 + 120);
				if(i== 3 || i== 4) platform[i].setLocation(15 + i*60, getHeight()/2 + 10);
		 		}
		 			System.out.println("BEFORE " + (status.getLevel() -1));
		 			
		 			System.out.println("AFTER " + status.getLevel());
		 		}
		 			
		 //LEVEL 4
		 for(int i=0; i<8; i++){
			 if(status.getLevel() == 4 || boom == 25) {
				 if(i== 0 || i==7) platform[i].setLocation(122 + i*30, getHeight()/2 + 130);
				 if(i== 1 || i== 6)	platform[i].setLocation(50 + i*50, getHeight()/2 - 20);
				 if(i== 3 || i== 4) platform[i].setLocation(15 + i*60, getHeight()/2 - 80);
		 		}
		 			System.out.println("BEFORE " + status.getLevel());
		 			
		 			System.out.println("AFTER " + status.getLevel());
		}
		 // LEVEL 5
			 if(status.getLevel() == 5 || boom == 26) {
				
				 victory();
//				 gameLogic.gameOver();
		 			
				 System.out.println("BEFORE " + (status.getLevel() - 1));
		 			
				 System.out.println("AFTER " + status.getLevel());
		}
	}
	/* 
	 * "remove" asteroid
	 */
	public void removeAsteroid(Asteroid asteroid){
	
		asteroidExplosion = new Rectangle(
				asteroid.x,
				asteroid.y,
				asteroid.width,
				asteroid.height);
		asteroid.setLocation(-asteroid.width, -asteroid.height);
		status.setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();

		// play asteroid explosion sound
		soundMan.playAsteroidExplosionSound();
	}
	
	/*
	 * "Remove" asteroid 2
	 */
	private void removeAsteroid2(Asteroid asteroid2) {
		asteroid2Explosion = new Rectangle(
				asteroid2.x,
				asteroid2.y,
				asteroid2.width,
				asteroid2.height);
		asteroid2.setLocation(-asteroid2.width, -asteroid2.height);
		status.setNewAsteroid2(true);
		lastAsteroid2Time = System.currentTimeMillis();

		// play asteroid explosion sound
		soundMan.playAsteroid2ExplosionSound();
	}
	
	/*
	 * Remove big asteroid
	 */
	public void removeBigAsteroid(BigAsteroid bigAsteroid){
		// "remove" big asteroid
		bigAsteroidExplosion = new Rectangle(
				bigAsteroid.x,
				bigAsteroid.y,
				bigAsteroid.width,
				bigAsteroid.height);
		bigAsteroid.setLocation(-bigAsteroid.width, -bigAsteroid.height);
		status.setNewBigAsteroid(true);
		lastBigAsteroidTime = System.currentTimeMillis();

		// play asteroid explosion sound
		soundMan.playBigAsteroidExplosionSound();
	}
	
	/*
	 * Remove boss
	 */
	public void removeBoss(Boss boss){
		// "remove" big asteroid
		bossExplosion = new Rectangle(
				boss.x,
				boss.y,
				boss.width,
				boss.height);
		boss.setLocation(-boss.width, -boss.height);
		status.setNewBoss(true);
		lastBossTime = System.currentTimeMillis();
		gameLogic.gameOver();
		// play asteroid explosion sound
		soundMan.playBigAsteroidExplosionSound();
	}
	
	public void victory(){
		if(status.getLevel() == 5 || boom == 26) {
		String gameTitleStr = "VICTORY";
		g2d.drawImage(graphicsMan.getVictoryImg(), null, 0, 0);
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D() - 60;
		bigFont = currentFont.deriveFont(fontSize + 1);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - 137;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Origninal Game Design by: J. Agosto";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent;
		g2d.setPaint(Color.ORANGE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String itemGameStr = "Edited for Project Purposes: JDCreations ";
		strWidth = fm.stringWidth(itemGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(itemGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String shopGameStr = "Member: Dylan Hernandez";
		strWidth = fm.stringWidth(shopGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(shopGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Member: Jairo Rosado";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 20;
		g2d.drawString(exitGameStr, strX, strY);
		}
//		Timer timer = new Timer(7000, new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				status.setGameOver(true);
//			}
//		});
//		timer.setRepeats(false);
//		timer.start();
		
//		gameLogic.gameOver();
	}
//	public void removeBulletBoss(BulletBoss bulletBoss) {
//		
//		bulletBoss = new Rectangle(
//				);
//				
//		
//	}

}
