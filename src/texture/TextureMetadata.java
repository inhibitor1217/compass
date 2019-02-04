package texture;

public class TextureMetadata {

	public float x, y, w, h, uvMinX, uvMinY, uvMaxX, uvMaxY;
	public TextureMetadata(float x, float y, float w, float h, float textureWidth, float textureHeight) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.uvMinX = x / textureWidth;
		this.uvMinY = y / textureHeight;
		this.uvMaxX = uvMinX + w / textureWidth;
		this.uvMaxY = uvMinY + h / textureHeight;
	}
	
}
