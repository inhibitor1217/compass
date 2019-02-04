package component;

import java.io.*;
import java.util.*;

import com.google.gson.*;

import engine.Timer;
import model.TexturedModel;

public class Animator extends Component {

	private static final float FPS = 4.0f;
	
	private HashMap<String, Animation> animations;
	
	private TexturedModel texturedModel;
	
	private Animation currentAnimation;
	private int currentFrame;
	
	private float t = 0.0f;
	
	public String initAnimationName = null;
	
	public Animator() {
		this(new HashMap<String, Animation>());
	}
	
	public Animator(HashMap<String, Animation> animations) {
		this.animations = animations;
	}
	
	@Override
	public void awake() {
		texturedModel = getGameObject().getTexturedModel();
	}
	
	@Override
	public void start() {
		if (initAnimationName != null)
			setCurrentAnimation(initAnimationName);
	}

	@Override
	public void update() {
		t += Timer.deltaTime();
		if (t > 1.0f / FPS) {
			t -= 1.0f / FPS;
			
			currentFrame++;
			
			if (currentFrame >= currentAnimation.frames.length)
				currentFrame -= currentAnimation.frames.length;
			
			texturedModel.setFrame(currentAnimation.animationName + "_" + currentAnimation.frames[currentFrame] + ".png");
		}
	}
	
	public String getCurrentAnimation() {
		return this.currentAnimation.animationName;
	}
	
	public void setCurrentAnimation(String newAnimationName) {
		setCurrentAnimation(newAnimationName, 0);
	}
	
	public void setCurrentAnimation(String newAnimationName, int startFrame) {
		if (!animations.containsKey(newAnimationName))
			throw new IllegalStateException("Animator does not contain requested animation");
		
		Animation newAnimation = animations.get(newAnimationName);
		if (startFrame >= newAnimation.frames.length)
			throw new IllegalStateException("Frame number is out of bounds");
		
		currentAnimation = newAnimation;
		currentFrame = startFrame;
		
		texturedModel.setFrame(currentAnimation.animationName + "_" + currentAnimation.frames[currentFrame] + ".png");
	}
	
	public boolean getMirror() {
		if (texturedModel == null)
			throw new IllegalStateException("TexturedModel is null");
		return texturedModel.getMirror();
	}
	
	public void setMirror(boolean mirror) {
		if (texturedModel == null)
			throw new IllegalStateException("TexturedModel is null");
		texturedModel.setMirror(mirror);
	}
	
	static class Animation {
		private String animationName;
		private int[] frames;
		
		Animation(String animationName, int[] frames) {
			this.animationName = animationName;
			this.frames = frames;
		}
		
		Animation(String animationName, int numFrames) {
			int[] frames = new int[numFrames];
			for(int i = 0; i < numFrames; i++)
				frames[i] = i;
			this.animationName = animationName;
			this.frames = frames;
		}
	}
	
	static class AnimationExport {
		private String animationName;
		private int numFrames;
		
		AnimationExport(String animationName, int numFrames) {
			this.animationName = animationName;
			this.numFrames = numFrames;
		}
	}
	
	public static Animator loadAnimation(String filename) {
		
		JsonArray animationData;
		try {
			// Parse JSON string
			JsonParser parser = new JsonParser();
			animationData = parser.parse(new BufferedReader(new FileReader(filename))).getAsJsonArray();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found: " + filename);
		}
		
		HashMap<String, Animation> animations = new HashMap<String, Animation>();
		
		for(JsonElement e: animationData) {
			JsonObject o = e.getAsJsonObject();
			String animationName = o.get("animationName").getAsString();
			if (o.has("numFrames"))
				animations.put(animationName, new Animation(animationName, o.get("numFrames").getAsInt()));
			else if (o.has("frames")){
				JsonArray frameArray = o.get("frames").getAsJsonArray();
				int[] frames = new int[frameArray.size()];
				for(int i = 0; i < frames.length; i++)
					frames[i] = frameArray.get(i).getAsInt();
				animations.put(animationName, new Animation(animationName, frames));
			}
		}
		
		Animator animator = new Animator(animations);
		
		return animator;
		
	}
	
	public static void exportAnimationData(String[] frames, String exportFilename) {
		
		ArrayList<AnimationExport> animations = new ArrayList<AnimationExport>();
		
		for(String frame: frames) {			
			String[] parsedString = frame.split("_|\\.");
			String animationName = parsedString[0];
			int frameNum = Integer.parseInt(parsedString[1]);
			
			boolean found = false;
			for (AnimationExport exportData: animations) {
				if (exportData.animationName.equals(animationName)) {
					if (exportData.numFrames < frameNum + 1)
						exportData.numFrames = frameNum + 1;
					found = true;
				}
			}
			if (!found) {
				animations.add(new AnimationExport(animationName, frameNum));
			}
		}
		
		String json = new Gson().toJson(animations);
		
		try {
			PrintWriter out = new PrintWriter(exportFilename);
			out.print(json);
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + exportFilename);
			System.exit(-1);
		}
		
	}

}
