package tester;

import component.*;
import engine.Timer;
import input.KeyboardHandler;

public class TileMovement extends Component {

	private Transform2D transform;
	
	private final float v = 3;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_LEFT)) {
			transform.translate(-v * Timer.deltaTime(), 0);
		}
		if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_RIGHT)) {
			transform.translate(v * Timer.deltaTime(), 0);
		}
	}

}
