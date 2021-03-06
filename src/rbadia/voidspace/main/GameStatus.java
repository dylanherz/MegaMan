package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	private boolean gameWon = false;
	
	// status variables
	private boolean newMegaMan;
	private boolean newBoss;
	private boolean newAsteroid;
	private boolean newAsteroid2;
	private boolean newBigAsteroid;
	private boolean newFloor;
	private boolean newPlatform;
	private long asteroidsDestroyed = 0;
	private long asteroids2Destroyed = 0;
	private long bigAsteroidDestroyed = 0;
	private long bossDestroyed = 0;
	private int LivesLeft;
	private int level = 1;
	
	public GameStatus(){
		
	}
	
	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}
	
	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}
	
	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}
	
	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Indicates if the game has and the "You Win!!!" message is displaying.
	 * @return if the game has ended and the "You Win!!!" message is displaying.
	 */
	public synchronized boolean isGameWon() {
		return gameWon;
	}
	
	public synchronized void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
	
	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewMegaMan() {
		return newMegaMan;
	}

	public synchronized void setNewMegaMan(boolean newMegaMan) {
		this.newMegaMan = newMegaMan;
	}
	
//	public synchronized boolean isNewFloor(){
//		return newFloor;
//	}
//	
//	public synchronized void setNewFloor(boolean newFloor) {
//		this.newFloor = newFloor;
//	}
	
//	public synchronized boolean isNewPlatform(){
//		return newPlatform;
//	}
//	
//	public synchronized void setNewPlatform(boolean newPlatform){
//		this.newPlatform = newPlatform;
//	}
	
	public synchronized boolean isNewBoss() {
		return newBoss;
	}

	public synchronized void setNewBoss(boolean newBoss) {
		this.newBoss = newBoss;
	}

	public synchronized boolean isNewBoss2() {
		return newBoss;
	}

	public synchronized void setNewBoss2(boolean newBoss) {
		this.newBoss = newBoss;
	}
	
	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}
	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid2 should be created/drawn
	 */
	public synchronized boolean isNewAsteroid2() {
		return newAsteroid2;
	}
	
	public synchronized void setNewAsteroid2(boolean newAsteroid2) {
		this.newAsteroid2 = newAsteroid2;
	}
	
	/**
	 * Indicates if a new big asteroid should be created/drawn.
	 * @return if a new big asteroid should be created/drawn
	 */
	public synchronized boolean isNewBigAsteroid() {
		return newBigAsteroid;
	}

	public synchronized void setNewBigAsteroid(boolean newBigAsteroid) {
		this.newBigAsteroid = newBigAsteroid;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}
	
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroids2Destroyed() {
		return asteroids2Destroyed;
	}
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getBigAsteroidDestroyed() {
		
		return bigAsteroidDestroyed;
	}
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized void setBigAsteroidDestroyed(long bigAsteroidDestroyed) {
		
		this.bigAsteroidDestroyed = bigAsteroidDestroyed;
	}
	
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}
	
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */

	public synchronized void setAsteroids2Destroyed(long asteroids2Destroyed) {
		this.asteroids2Destroyed = asteroids2Destroyed;
	}
	
	/**
	 * Returns the number Lives/lives left.
	 * @return the number Lives left
	 */
	public synchronized int getLivesLeft() {
		return LivesLeft;
	}

	public synchronized void setLivesLeft(int LivesLeft) {
		this.LivesLeft = LivesLeft;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */

	public synchronized long getBossDestroyed() {
		return bossDestroyed;
	}
	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */

	public synchronized void setBossDestroyed(long bossDestroyed) {
		this.bossDestroyed = bossDestroyed;
	}
	
	
	
	/**
	 * Returns the current game level.
	 * @return the game level
	 */
}
