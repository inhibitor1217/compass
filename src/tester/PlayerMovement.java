package tester;

import component.*;
import engine.Timer;
import input.KeyboardHandler;

public class PlayerMovement extends Component {

	private Transform2D transform;
	private Animator animator;
	
	private final float v = .1f;
	
	private static final String WALK_ANIMATION = "walk1";
	private static final String IDLE_ANIMATION = "stand1";
	
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
			transform.translate(-v * Timer.deltaTime(), 0);
			if (!animator.getCurrentAnimation().equals(WALK_ANIMATION)) {
				animator.setCurrentAnimation(WALK_ANIMATION);
			}
			animator.setMirror(false);
		}
		else if (KeyboardHandler.isKeyDown(KeyboardHandler.KEY_RIGHT)) {
			transform.translate(v * Timer.deltaTime(), 0);
			if (!animator.getCurrentAnimation().equals(WALK_ANIMATION)) {
				animator.setCurrentAnimation(WALK_ANIMATION);
			}
			animator.setMirror(true);
		}
		else if (!animator.getCurrentAnimation().equals(IDLE_ANIMATION))
			animator.setCurrentAnimation(IDLE_ANIMATION);
		
	}

}
