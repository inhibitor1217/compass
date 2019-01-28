package model;

import engine.Loader;
import texture.*;

public class TexturedModel {

	private RawModel rawModel;
	private TextureAtlas textureAtlas;
	
	public TexturedModel(TextureAtlas textureAtlas, Loader loader, int screenWidth, int screenHeight) {
		
		this.textureAtlas = textureAtlas;
		
		Texture texture = this.textureAtlas.getTexture();
		int width = texture.getWidth();
		int height = texture.getHeight();
		
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
			
			indices[6 * i + 0] = 8 * i + 0;
			indices[6 * i + 1] = 8 * i + 1;
			indices[6 * i + 2] = 8 * i + 2;
			indices[6 * i + 3] = 8 * i + 1;
			indices[6 * i + 4] = 8 * i + 3;
			indices[6 * i + 5] = 8 * i + 2;
			
			float uvMinX = (float) metadata[i].x / (float) width;
			float uvMinY = (float) metadata[i].y / (float) height;
			float uvMaxX = uvMinX + (float) metadata[i].w / (float) width;
			float uvMaxY = uvMinY + (float) metadata[i].h / (float) height;
			
			uvs[8 * i + 0] = uvMinX;
			uvs[8 * i + 1] = uvMinY; // V0
			uvs[8 * i + 2] = uvMinX;
			uvs[8 * i + 3] = uvMaxY; // V1
			uvs[8 * i + 4] = uvMaxX;
			uvs[8 * i + 5] = uvMinY; // V2
			uvs[8 * i + 6] = uvMaxX;
			uvs[8 * i + 7] = uvMaxY; // V3
			
		}
		
		this.rawModel = loader.loadToVAO(vertices, uvs, indices);
	}
	
	public RawModel getRawModel() {
		return this.rawModel;
	}
	
	public TextureAtlas getTextureAtlas() {
		return this.textureAtlas;
	}
	
}
