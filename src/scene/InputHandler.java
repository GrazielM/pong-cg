package scene;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import core.GameState;

public class InputHandler implements KeyListener {
	private Stage stage;

	public InputHandler(Stage stage) {
		this.stage = stage;
	}

	public void keyPressed(KeyEvent e) {
		short key = e.getKeyCode();
		if (!e.isAutoRepeat()) switch (key) {
			case KeyEvent.VK_RIGHT:
				stage.movBuff.paddleDirection = 1;
				break;
			case KeyEvent.VK_LEFT:
				stage.movBuff.paddleDirection = -1;
				break;
			case KeyEvent.VK_P:
				stage.gs.state = stage.gs.state == GameState.SUSPENDED ? GameState.ACTIVE : GameState.SUSPENDED;
				break;
			case KeyEvent.VK_F:
				if (stage.gs.state == GameState.LEVEL_START) stage.gs.state = GameState.ACTIVE;
				break;
			case KeyEvent.VK_ENTER:
				if (stage.gs.state == GameState.SPLASH) stage.gs.state = GameState.LEVEL_START;
				break;
			case KeyEvent.VK_ESCAPE:
				stage.gs.state = GameState.GAME_OVER;
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		short key = e.getKeyCode();
		if (!e.isAutoRepeat() && (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT))
			stage.movBuff.paddleDirection = 0;
	}
}