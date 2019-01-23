package shader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
		
		vertexShaderID = loadShader(vertexShaderFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShaderFile, GL_FRAGMENT_SHADER);
		
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		glLinkProgram(programID);
		glValidateProgram(programID);
		
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
	
}
