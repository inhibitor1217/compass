package engine;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.*;

import model.RawModel;
import texture.*;

public class Loader {

	private static final String RESOURCE_PATH = "res/";
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Texture> textures = new ArrayList<Texture>();
	
	public RawModel loadToVAO(float[] positions, float[] uvs, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		storeDataInAttributeList(1, 2, uvs);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	public Texture loadTexture(String filename) {
		ByteBuffer image;
		int width, height;
		
		try(MemoryStack stack = stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			stbi_set_flip_vertically_on_load(true);
			image = stbi_load(RESOURCE_PATH + filename, w, h, comp, 4);
			if (image == null)
				throw new RuntimeException("Failed to load a texture file." + System.lineSeparator() + stbi_failure_reason());
			
			width = w.get();
			height = h.get();
		}
		
		Texture texture = new Texture(width, height, image);
		textures.add(texture);
		
		return texture;
	}
	
	public void shutdown() {
		for(int vao: vaos) {
			glDeleteVertexArrays(vao);
		}
		for(int vbo: vbos) {
			glDeleteBuffers(vbo);
		}
		for(Texture texture: textures) {
			texture.shutdown();
		}
	}
	
	private int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int dataDimension, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, dataDimension, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO() {
		glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
