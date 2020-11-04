package core;

public class GameState {

	public static final int SPLASH = 0;
	public static final int SUSPENDED = 1;
	public static final int ACTIVE = 2;
	public static final int GAME_OVER = 3;
	public static final int LEVEL_START = 4;

	public int score = 0;
	public int nivel = 1;
	public int vidas = 3;
	public int state = SPLASH;
}
