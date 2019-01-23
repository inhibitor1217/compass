package tester;

import engine.*;
import model.*;
import shader.*;
import texture.*;

public class Main extends DisplayManager {

	private Loader loader;
	private ModelRenderer renderer;
	private StaticShader shader;
	
	private RawModel model;
	private TexturedModel texturedModel;
	private Texture texture;

	@Override
	protected void init() {
		loader = new Loader();
		renderer = new ModelRenderer();
		shader = new StaticShader();
		
		float[] vertices = {
				-0.5f, 0.5f,
				-0.5f, -0.5f,
				0.5f, -0.5f,
				0.5f, 0.5f
		};
		
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] uvs = {
				0, 0,
				0, 1,
				1, 1,
				1, 0
		};
		
		model = loader.loadToVAO(vertices, uvs, indices);
		texture = loader.loadTexture("Star.png");
		texturedModel = new TexturedModel(model, texture);
	}
	
	@Override
	protected void update() {
		renderer.prepare();
		shader.start();
		renderer.render(texturedModel);
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
