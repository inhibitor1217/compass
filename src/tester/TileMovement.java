package tester;

import component.*;

public class TileMovement extends Component {

	private Transform2D transform;
	
	private final float r = 3.0f;
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
		angle += 0.02f;
	}

}
