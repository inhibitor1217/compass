package tester;

import engine.*;
import component.*;
import model.*;
import object.GameObject;
import shader.*;
import texture.*;

public class Main extends DisplayManager {

	private Loader loader;
	private ModelRenderer renderer;
	private StaticShader shader;
	
	private TextureAtlas spriteSheet;
	private TexturedModel characterModel;
	private GameObject camera;

	@Override
	protected void init() {
		loader = new Loader();
		renderer = new ModelRenderer();
		shader = new StaticShader();
		
		spriteSheet = new TextureAtlas("spritesheets/char.png", "spritesheets/char.json", loader);
		characterModel = new TexturedModel(spriteSheet, loader, WIDTH, HEIGHT);
		
		camera = new GameObject(new Transform2D());
	
		GameObject player = new GameObject(new Transform2D().scale(2), characterModel);
		player.addComponent(Animator.loadAnimation("res/animations/char/anim.json"));
		player.addComponent(new PlayerMovement());
		
		for(GameObject o: GameObject.getAllGameObjects()) {
			for(Component component: o.getComponents()) {
				component.awake();
			}
		}
	}
	
	@Override
	protected void start() {
		for(GameObject object: GameObject.getAllGameObjects()) {
			for(Component component: object.getComponents()) {
				component.start();
			}
		}
	}
	
	@Override
	protected void update() {
		renderer.prepare();
		shader.start();
		shader.loadProjection(camera.getTransform().getProjectionMatrix());
		
		for(GameObject object: GameObject.getAllGameObjects()) {
			for(Component component: object.getComponents()) {
				component.update();
			}
		}
		
		for(GameObject object: GameObject.getAllGameObjects()) {
			renderer.render(object, shader);
		}
		
		shader.stop();
	}

	@Override
	protected void shutdown() {
		loader.shutdown();
		shader.shutdown();
	}
	
	public static void main(String[] args) {
		new Main().run();
	}

}
