package texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.*;

public class Texture {

	protected int textureID;
	
	private int width, height;
	
	public Texture(int width, int height, ByteBuffer data) {
		textureID = glGenTextures();
		this.width = width;
		this.height = height;
		
		bind();
		
		// Set border options (clamp / repeat)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		
		// Set texture sampling options (nearest / linear)
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		// Set image data to the texture
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		unbind();
	}
	
	public int getTextureID() {
		return this.textureID;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void shutdown() {
		glDeleteTextures(textureID);
	}
	
}
