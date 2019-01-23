package engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;



public abstract class DisplayManager {
	
	private long window;
	
	protected static int WIDTH = 1280;
	protected static int HEIGHT = 720;
	protected static String APP_TITLE = "compass";
	
	public void run() {
		System.out.println("Project running on LWJGL version " + Version.getVersion());
		
		initWindows();
		init();
		loop();
		shutdown();
		shutdownWindow();
	}
	
	private void initWindows() {
		// Setup an error callback.
		// The default implementation will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		// Configure GLFW.
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		
		// Create the window.
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, APP_TITLE, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				// close window when ESCAPE key is pressed
				// will detect this in the rendering loop
				glfwSetWindowShouldClose(window, true);
			}
		});
		
		// Get the thread stack and push a new frame.
		try(MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			
			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		
		// Make the OpenGL context current.
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);
		
		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
	}
	
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		// Set the clear color.
		glClearColor(0, 0, 0, 0);
		
		// Run the rendering loop until the user attempted to close
		// the window or pressed the ESCAPE key.
		while(!glfwWindowShouldClose(window)) {
			// clear the frame buffer
			// depth buffer is not used in this project
			glClear(GL_COLOR_BUFFER_BIT);
			
			update();
			
			glfwSwapBuffers(window); // swap the color buffers
			
			// Poll for window events. The key callback above will only be invoked during this call.
			glfwPollEvents();
		}
	}
	
	private void shutdownWindow() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	protected abstract void init();
	protected abstract void update();
	protected abstract void shutdown();
	
}
