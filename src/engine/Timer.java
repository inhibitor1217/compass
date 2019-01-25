package engine;

import org.lwjgl.glfw.GLFW;

public class Timer {

	private static float curTime;
	private static float lastTime;
	
	public static void start() {
		curTime = 0.0f;
		lastTime = 0.0f;
		GLFW.glfwSetTime(0);
	}
	
	public static void frame() {
		lastTime = curTime;
		curTime = (float) GLFW.glfwGetTime();
	}
	
	public static float deltaTime() {
		return curTime - lastTime;
	}
	
}
