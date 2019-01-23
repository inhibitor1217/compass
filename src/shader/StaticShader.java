package shader;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_SHADER_FILENAME = "src/shader/static.vs";
	private static final String FRAGMENT_SHADER_FILENAME = "src/shader/static.ps";
	
	public StaticShader() {
		super(VERTEX_SHADER_FILENAME, FRAGMENT_SHADER_FILENAME);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "uv");
	}

}
