package texture;

public class TextureMetadata {

	public int textureAtlasIndex;
	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	
	public TextureMetadata(int textureAtlasIndex, float minX, float minY, float maxX, float maxY) {
		this.textureAtlasIndex = textureAtlasIndex;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
}
