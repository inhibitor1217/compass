package shader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer buf = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
		
		vertexShaderID = loadShader(vertexShaderFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShaderFile, GL_FRAGMENT_SHADER);
		
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		
		bindAttributes();
		
		glLinkProgram(programID);
		glValidateProgram(programID);
		
		getAllUniformLocations();
		
	}
	
	public void start() {
		glUseProgram(programID);
	}
	
	public void stop() {
		 glUseProgram(0);
	}
	
	public void shutdown() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	protected int getUniformLocation(String uniformVariableName) {
		return glGetUniformLocation(programID, uniformVariableName);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName) {
		glBindAttribLocation(programID, attribute, variableName);
	}
	
	private static int loadShader(String filename, int type) {
		
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while((line = reader.readLine()) != null)
				shaderSource.append(line).append("\n");
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found: " + filename);
		} catch (IOException e) {
			throw new RuntimeException("Cannot read file: " + filename);
		}
		
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			throw new RuntimeException("Cannot compile shader: " + filename);
		}
		
		return shaderID;
		
	}
	
	protected void loadBooleanUniform(int location, boolean value) {
		glUniform1f(location, value ? 1.0f : 0.0f);
	}
	
	protected void loadFloatUniform(int location, float value) {
		glUniform1f(location, value);
	}
	
	protected void loadVector2fUniform(int location, Vector2f value) {
		glUniform2f(location, value.x, value.y);
	}
	
	protected void loadVector3fUniform(int location, Vector3f value) {
		glUniform3f(location, value.x, value.y, value.z);
	}
	
	protected void loadVector4fUniform(int location, float x, float y, float z, float w) {
		glUniform4f(location, x, y, z, w);
	}
	
	protected void loadVector4fUniform(int location, Vector4f value) {
		loadVector4fUniform(location, value.x, value.y, value.z, value.w);
	}
	
	protected void loadMatrix4fUniform(int location, Matrix4f value) {
		value.store(buf);
		buf.flip();
		glUniformMatrix4fv(location, false, buf);
	}
	
}
