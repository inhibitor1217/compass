package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import model.*;
import object.GameObject;
import texture.*;
import shader.*;

public class ModelRenderer {

	public void prepare() {
		
	}
	
	public void render(RawModel model) {
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void render(TexturedModel texturedModel, int textureAtlasIndex) {
		RawModel model = texturedModel.getRawModel();
		Texture texture = texturedModel.getTextureAtlas().getTexture();
		
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 6 * 4 * textureAtlasIndex);
		
		texture.unbind();
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
	public void render(GameObject object, StaticShader shader) {
		TexturedModel texturedModel = object.getTexturedModel();
		if (texturedModel != null) {
			shader.loadTransformation(object.getTransform().getTransformationMatrix());
			shader.loadBoundBox(texturedModel.getUVBoundBox());
			shader.loadMirror(texturedModel.getMirror());
			render(texturedModel, texturedModel.getFrameOffset());
			return;
		}
	}
	
}
