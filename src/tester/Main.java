package tester;

import engine.*;
import shader.*;

public class Main extends DisplayManager {

	private Loader loader;
	private ModelRenderer renderer;
	private StaticShader shader;
	
	private RawModel model;

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
		
		model = loader.loadToVAO(vertices, indices);
	}
	
	@Override
	protected void update() {
		renderer.prepare();
		shader.start();
		renderer.render(model);
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