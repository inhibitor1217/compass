package input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback {

	private static boolean keys[] = new boolean[65536];

	public static final int KEY_LEFT = GLFW_KEY_LEFT;
	public static final int KEY_RIGHT = GLFW_KEY_RIGHT;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
		
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
			// close window when ESCAPE key is pressed
			// will detect this in the rendering loop
			glfwSetWindowShouldClose(window, true);
		}
	}
	
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
}
