package model;

import org.lwjgl.util.vector.*;

import engine.Loader;
import texture.*;

public class TexturedModel {

	private RawModel rawModel;
	private TextureAtlas textureAtlas;
	
	private String frame;
	private boolean mirror = false;
	
	public TexturedModel(TextureAtlas textureAtlas, Loader loader, int screenWidth, int screenHeight) {
		
		this.textureAtlas = textureAtlas;
		
		// Build 2d mesh and UV map from textureAtlas
		int numFrames = textureAtlas.getNumFrames();
		TextureMetadata[] metadata = textureAtlas.getMetadata();
		
		float[] vertices = new float[numFrames * 8];
		int[] indices = new int[numFrames * 6];
		float[] uvs = new float[numFrames * 8];
		
		for(int i = 0; i < numFrames; i++) {
			
			float minX = -0.5f * (float) metadata[i].w / (float) screenWidth;
			float minY = (float) metadata[i].h / (float) screenHeight;
			float maxX = -minX;
			float maxY = 0;
			
			vertices[8 * i + 0] = minX;
			vertices[8 * i + 1] = minY; // V0
			vertices[8 * i + 2] = minX;
			vertices[8 * i + 3] = maxY; // V1
			vertices[8 * i + 4] = maxX;
			vertices[8 * i + 5] = minY; // V2
			vertices[8 * i + 6] = maxX;
			vertices[8 * i + 7] = maxY; // V3
			
			indices[6 * i + 0] = 4 * i + 0;
			indices[6 * i + 1] = 4 * i + 1;
			indices[6 * i + 2] = 4 * i + 2;
			indices[6 * i + 3] = 4 * i + 1;
			indices[6 * i + 4] = 4 * i + 3;
			indices[6 * i + 5] = 4 * i + 2;
			
			uvs[8 * i + 0] = metadata[i].uvMinX;
			uvs[8 * i + 1] = metadata[i].uvMinY; // V0
			uvs[8 * i + 2] = metadata[i].uvMinX;
			uvs[8 * i + 3] = metadata[i].uvMaxY; // V1
			uvs[8 * i + 4] = metadata[i].uvMaxX;
			uvs[8 * i + 5] = metadata[i].uvMinY; // V2
			uvs[8 * i + 6] = metadata[i].uvMaxX;
			uvs[8 * i + 7] = metadata[i].uvMaxY; // V3
			
		}
		
		this.rawModel = loader.loadToVAO(vertices, uvs, indices);
		this.frame = textureAtlas.getFrames()[0];
	}
	
	public RawModel getRawModel() {
		return this.rawModel;
	}
	
	public TextureAtlas getTextureAtlas() {
		return this.textureAtlas;
	}
	
	public String getFrame() {
		return this.frame;
	}
	
	public void setFrame(String frame) {
		this.frame = frame;
	}
	
	public int getFrameOffset() {
		return this.textureAtlas.getFrameMap().get(frame);
	}
	
	public boolean getMirror() {
		return this.mirror;
	}
	
	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}
	
	public Vector4f getUVBoundBox() {
		TextureMetadata metadata = textureAtlas.getMetadata(frame);
		return new Vector4f(metadata.uvMinX, metadata.uvMinY, metadata.uvMaxX, metadata.uvMaxY);
	}
	
}
