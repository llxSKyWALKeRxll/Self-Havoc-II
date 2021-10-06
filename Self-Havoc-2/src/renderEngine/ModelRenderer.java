package renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import utils.Maths;

/**
 * This class renders objects or models onto the display window
 * @author Vansh Pratap Singh
 *
 */
public class ModelRenderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	
	public ModelRenderer(StaticShader shader)
	{
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	/**
	 * Background color of the display
	 */
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0.5f, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * This method renders the model onto the display window
	 * @param model The model that is to be rendered
	 */
	public void renderModel(Entity entity, StaticShader shader) {
		TexturedModel texturedModel = entity.getModel();
		RawModel model = texturedModel.getRawModel();
//		Bind/Activate the VAO
		GL30.glBindVertexArray(model.getVAOid());
//		Enable the attribute list where our data is stored
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
//		Load entity's transformation to shader through transformation matrix
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = texturedModel.getModelTexture();
		shader.loadShineFactors(texture.getShineDamper(), texture.getReflectivity());
//		Activate the texture that is to be rendered
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getModelTexture().getTextureId());
//		Render model
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCtr(), GL11.GL_UNSIGNED_INT, 0);
//		Disable attribute list after its usage
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
//		Unbind VAO
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Creates the projection matrix
	 */
	private void createProjectionMatrix()
	{
		float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
		float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float xScale = yScale/aspectRatio;
		float frustumLength = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * FAR_PLANE * NEAR_PLANE) / frustumLength);
		projectionMatrix.m33 = 0;
	}

}
