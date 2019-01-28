package component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Animator extends Component {

	public Animator(String filename) {
		
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}
	
	class Animation {
		String animationName;
		int numFrames;
		int[] frames;
		
		Animation(String animationName, int[] frames) {
			this.animationName = animationName;
			this.numFrames = frames.length;
			this.frames = frames;
		}
	}
	
	static class AnimationExportData {
		String animationName;
		int numFrames;
		
		AnimationExportData(String animationName, int numFrames) {
			this.animationName = animationName;
			this.numFrames = numFrames;
		}
		
		@Override
		public String toString() {
			return "[" + animationName + ", " + numFrames + "]";
		}
	}
	
	public static void exportAnimationData(String[] frames, String exportFilename) {
		
		ArrayList<AnimationExportData> animations = new ArrayList<AnimationExportData>();
		
		for(String frame: frames) {			
			String[] parsedString = frame.split("_|\\.");
			String animationName = parsedString[0];
			int frameNum = Integer.parseInt(parsedString[1]);
			
			boolean found = false;
			for (AnimationExportData exportData: animations) {
				if (exportData.animationName.equals(animationName)) {
					if (exportData.numFrames < frameNum + 1)
						exportData.numFrames = frameNum + 1;
					found = true;
				}
			}
			if (!found) {
				animations.add(new AnimationExportData(animationName, frameNum));
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
