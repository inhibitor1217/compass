package tester;

import engine.*;
import model.*;
import shader.*;
import texture.*;

public class Main extends DisplayManager {

	private Loader loader;
	private ModelRenderer renderer;
	private StaticShader shader;
	
	private RawModel model1, model2;
	private TexturedModel texturedModel1, texturedModel2;
	private Texture texture1, texture2;

	@Override
	protected void init() {
		loader = new Loader();
		renderer = new ModelRenderer();
		shader = new StaticShader();
		
		float[] vertices1 = {
				-0.7f, 0.5f,
				-0.7f, -0.5f,
				-0.2f, -0.5f,
				-0.2f, 0.5f
		};
		
		float[] vertices2 = {
				0.2f, 0.5f,
				0.2f, -0.5f,
				0.7f, -0.5f,
				0.7f, 0.5f
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
		
		model1 = loader.loadToVAO(vertices1, uvs, indices);
		model2 = loader.loadToVAO(vertices2, uvs, indices);
		
		texture1 = loader.loadTexture("grass.png");
		texture2 = loader.loadTexture("Star.png");
		
		texturedModel1 = new TexturedModel(model1, texture1);
		texturedModel2 = new TexturedModel(model2, texture2);
	}
	
	@Override
	protected void update() {
		renderer.prepare();
		shader.start();
		renderer.render(texturedModel1);
		renderer.render(texturedModel2);
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
