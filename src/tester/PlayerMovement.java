package tester;

import component.*;
import engine.Timer;

import input.KeyboardHandler;

public class PlayerMovement extends Component {

	private static final String WALK_ANIMATION = "walk1";
	private static final String IDLE_ANIMATION = "stand1";
	
	private static final int WALK_STATE_NONE = 0;
	private static final int WALK_STATE_LEFT = 1;
	private static final int WALK_STATE_RIGHT = 2;
	
	private Transform2D transform;
	private Animator animator;
	
	private final float v = .1f;
	
	private int prevWalkState = WALK_STATE_NONE;
	
	@Override
	public void awake() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
		animator = (Animator) getGameObject().getComponent(Animator.class);
		animator.initAnimationName = IDLE_ANIMATION;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_LEFT)) {
			if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_RIGHT)) {
				if (prevWalkState == WALK_STATE_LEFT)
					walk(false);
				else
					walk(true);
			}
			else
				walk(false);
		}
		else if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_RIGHT))
			walk(true);
		else if (!animator.getCurrentAnimation().equals(IDLE_ANIMATION)) {
			animator.setCurrentAnimation(IDLE_ANIMATION);
			prevWalkState = WALK_STATE_NONE;
		}
	}
	
	private void walk(boolean direction) {
		// direction == false : walk left
		// direction == true  : walk right
		if (!animator.getCurrentAnimation().equals(WALK_ANIMATION)) {
			animator.setCurrentAnimation(WALK_ANIMATION);
		}
		animator.setMirror(direction);

		transform.translate((direction ? 1.0f : -1.0f) * v * Timer.deltaTime(), 0);

		prevWalkState = direction ? WALK_STATE_RIGHT : WALK_STATE_LEFT;
	}

}
