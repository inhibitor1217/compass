package shader;

import org.lwjgl.util.vector.*;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_SHADER_FILENAME = "src/shader/static.vs";
	private static final String FRAGMENT_SHADER_FILENAME = "src/shader/static.ps";
	
	private int transformationLocation;
	private int projectionLocation;
	private int boundBoxLocation;
	private int mirrorLocation;
	
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
		projectionLocation = super.getUniformLocation("projection");
		boundBoxLocation = super.getUniformLocation("boundBox");
		mirrorLocation = super.getUniformLocation("mirror");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix4fUniform(transformationLocation, matrix);
	}
	
	public void loadProjection(Matrix4f matrix) {
		super.loadMatrix4fUniform(projectionLocation, matrix);
	}
	
	public void loadBoundBox(float minX, float minY, float maxX, float maxY) {
		super.loadVector4fUniform(boundBoxLocation, minX, minY, maxX, maxY);
	}
	
	public void loadBoundBox(Vector4f value) {
		super.loadVector4fUniform(boundBoxLocation, value);
	}
	
	public void loadMirror(boolean value) {
		super.loadBooleanUniform(mirrorLocation, value);
	}

}
