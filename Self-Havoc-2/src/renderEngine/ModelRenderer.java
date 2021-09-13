package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import models.TexturedModel;

/**
 * This class renders objects or models onto the display window
 * @author Vansh Pratap Singh
 *
 */
public class ModelRenderer {
	
	/**
	 * Background color of the display
	 */
	public void prepare() {
		GL11.glClearColor(0, 0.5f, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * This method renders the model onto the display window
	 * @param model The model that is to be rendered
	 */
	public void renderModel(TexturedModel texturedModel) {
		RawModel model = texturedModel.getRawModel();
//		Bind/Activate the VAO
		GL30.glBindVertexArray(model.getVAOid());
//		Enable the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
//		Activate the texture that is to be rendered
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getModelTexture().getTextureId());
//		Render model
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCtr(), GL11.GL_UNSIGNED_INT, 0);
//		Disable attribute list after its usage
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
//		Unbind VAO
		GL30.glBindVertexArray(0);
	}

}
