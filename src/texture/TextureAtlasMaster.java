package texture;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;

public class TextureAtlasMaster {

	private static ArrayList<Texture> textureAtlases = new ArrayList<Texture>();
	private static HashMap<String, TextureMetadata> atlasMap = new HashMap<String, TextureMetadata>();
	
	public static HashMap<String, TextureMetadata> createTextureAtlas(List<String> textureFilenames) {
		
		// read files from textureFilenames
		
		// generate data using packing algorithm
		// could have multiple texture atlases - should consider this
		int width = 0, height = 0;
		ByteBuffer data = BufferUtils.createByteBuffer(0);
		
		// create texture using data
		Texture texture = new Texture(width, height, data);
		textureAtlases.add(texture);
		
		return atlasMap;
		
	}
	
	public static TextureMetadata getMetadata(String filename) {
		return atlasMap.get(filename);
	}
	
	public static void shutdown() {
		for(Texture textureAtlas : textureAtlases) {
			textureAtlas.shutdown();
		}
	}
	
}
