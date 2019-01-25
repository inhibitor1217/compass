package tester;

import component.*;
import engine.Timer;

public class TileMovement extends Component {

	private Transform2D transform;
	
	private final float r = 3f;
	private final float v = 1.0f;
	private float angle = 0.0f;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		transform.setPosition(r * (float) Math.cos(angle), r * (float) Math.sin(angle));
		angle += v * Timer.deltaTime();
	}

}
