package tester;

import component.*;
import engine.Timer;
import input.KeyboardHandler;

public class TileMovement extends Component {

	private Transform2D transform;
	private Animator animator;
	
	private final float v = .1f;
	
	@Override
	public void awake() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
		animator = (Animator) getGameObject().getComponent(Animator.class);
		animator.initAnimationName = "stand1";
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_LEFT)) {
			transform.translate(-v * Timer.deltaTime(), 0);
			if (!animator.getCurrentAnimation().equals("walk1"))
				animator.setCurrentAnimation("walk1");
		}
		else if (!animator.getCurrentAnimation().equals("stand1"))
			animator.setCurrentAnimation("stand1");
		
	}

}
