package tester;

import component.*;
import engine.Timer;
import input.KeyboardHandler;

public class TileMovement extends Component {

	private Transform2D transform;
	
	private final float v = 1;
	
	private float t = 0.0f;
	private final float FRAME_TIME = 0.25f;
	
	private String[] anim = {"swingO1_0.png", "swingO1_1.png", "swingO1_2.png", "swingO1_3.png"};
	private int anim_frameIdx = 0;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		transform = getGameObject().getTransform();
		getGameObject().getTexturedModel().setFrame(anim[0]);
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
		
		t += Timer.deltaTime();
		if (t > FRAME_TIME) {
			anim_frameIdx++;
			getGameObject().getTexturedModel().setFrame(anim[anim_frameIdx % 4]);
			t -= FRAME_TIME;
		}
	}

}
