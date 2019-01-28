package texture;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.*;

import engine.Loader;

public class TextureAtlas {

	private Texture texture;
	private HashMap<String, Integer> frameMap;
	private TextureMetadata[] metadata;
	
	private static final String RESOURCE_TEXTURE_PATH = "res/textures/";
	
	public TextureAtlas(String spriteFilename, String dataFilename, Loader loader) {
		
		this.texture = loader.loadTexture(spriteFilename);
		
		frameMap = new HashMap<String, Integer>();
		JsonObject data;
		
		try {
			// Parse JSON string
			JsonParser parser = new JsonParser();
			data = parser.parse(new BufferedReader(new FileReader(RESOURCE_TEXTURE_PATH + dataFilename))).getAsJsonObject();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found: " + dataFilename);
		}
		
		JsonArray frames = data.get("frames").getAsJsonArray();
		metadata = new TextureMetadata[frames.size()];
		
		int idx = 0;
		for(JsonElement frameElement: frames) {
			JsonObject frame = frameElement.getAsJsonObject();
			JsonObject frameData = frame.get("frame").getAsJsonObject();
			int x = frameData.get("x").getAsInt();
			int y = frameData.get("y").getAsInt();
			int w = frameData.get("w").getAsInt();
			int h = frameData.get("h").getAsInt();
			frameMap.put(frame.get("filename").getAsString(), idx);
			metadata[idx] = new TextureMetadata(x, y, w, h);
			idx++;
		}
		
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public HashMap<String, Integer> getFrameMap() {
		return this.frameMap;
	}
	
	public TextureMetadata[] getMetadata() {
		return this.metadata;
	}
	
	public int getNumFrames() {
		return this.frameMap.size();
	}
	
	public TextureMetadata getMetadata(String frame) {
		return this.metadata[this.frameMap.get(frame)];
	}

	public String[] getFrames() {
		return this.frameMap.keySet().toArray(new String[this.frameMap.size()]);
	}
	
}
