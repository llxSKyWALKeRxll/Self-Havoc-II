package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
		GL11.glClearColor(0, 1, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * This method renders the model onto the display window
	 * @param model The model that is to be rendered
	 */
	public void renderModel(RawModel model) {
//		Bind/Activate the VAO
		GL30.glBindVertexArray(model.getVAOid());
//		Enable the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
//		Render model
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCtr());
//		Disable attribute list after its usage
		GL20.glDisableVertexAttribArray(0);
//		Unbind VAO
		GL30.glBindVertexArray(0);
	}

}
