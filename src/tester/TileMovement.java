package tester;

import component.*;
import engine.Timer;

public class TileMovement extends Component {

	private Transform2D transform;
	
	private final float rx = 2;
	private final float ry = 2;
	private final float v = 1;
	private float angle = 0;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		transform.setLocalPosition(rx * (float) Math.cos(angle), ry * (float) Math.sin(angle));
		transform.rotate(v * Timer.deltaTime());
		angle += v * Timer.deltaTime();
	}

}
