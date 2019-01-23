package model;

import texture.*;

public class TexturedModel {

	private RawModel rawModel;
	private Texture texture;
	
	public TexturedModel(RawModel model, Texture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public Texture getTexture() {
		return texture;
	}
	
}
