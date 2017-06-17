package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int asteroid2Width = 32;
	private int asteroid2Height = 32;
	private int speed = DEFAULT_SPEED;
	private int randomSpeed;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		this.setLocation(
				screen.getWidth() - asteroidWidth,
        		rand.nextInt(screen.getHeight() - asteroidHeight - 32)
        		);
		this.setSize(asteroidWidth, asteroidHeight);
	}
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 * @return 
	 */
	public void Asteroid2(GameScreen screen) {
		this.setLocation(
				screen.getWidth() - asteroid2Width,
        		rand.nextInt(screen.getHeight() - asteroid2Height - 32)
        		);
		this.setSize(asteroid2Width, asteroid2Height);
	}
	
	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
	}

	public int getAsteroid2Width() {
		return asteroidWidth;
	}
	public int getAsteroid2Height() {
		return asteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getRandomSpeed(){
		randomSpeed = rand.nextInt(7);
		return randomSpeed;
	}
	
	public void setRandomSpeed(int speed){
		this.speed = speed;
	}
	
	public int getRandomSpeed2(){
		randomSpeed = rand.nextInt(9);
		return randomSpeed;
	}
	
	public void setRandomSpeed2(int randomSpeed){
		this.randomSpeed = speed;
	}
	
	
}
