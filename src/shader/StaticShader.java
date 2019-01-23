package shader;

import org.lwjgl.util.vector.*;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_SHADER_FILENAME = "src/shader/static.vs";
	private static final String FRAGMENT_SHADER_FILENAME = "src/shader/static.ps";
	
	private int transformationLocation;
	
	public StaticShader() {
		super(VERTEX_SHADER_FILENAME, FRAGMENT_SHADER_FILENAME);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "uv");
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		transformationLocation = super.getUniformLocation("transformation");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix4fUniform(transformationLocation, matrix);
	}

}
