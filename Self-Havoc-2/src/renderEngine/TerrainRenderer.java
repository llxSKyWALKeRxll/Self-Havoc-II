package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.TerrainShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexturePack;
import utils.Maths;

/**
 * Class for rendering terrains
 * @author Vansh Pratap Singh
 *
 */
public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains) {
		for(Terrain terrain: terrains) {
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCtr(), 
					GL11.GL_UNSIGNED_INT, 0);
			unbindTerrain();
		}
	}
	
	/**
	 * Prepare the terrain model for rendering
	 * @param model Model to be prepared
	 */
	private void prepareTerrain(Terrain terrain) {
		RawModel rawModel = terrain.getModel();
		GL30.glBindVertexArray(rawModel.getVAOid());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(terrain);
		shader.loadShineFactors(1, 0);
	}
	
	private void bindTextures(Terrain terrain) {
		TerrainTexturePack texturePack = terrain.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureId());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureId());
	}
	
	/**
	 * Unbind terrain model
	 */
	private void unbindTerrain() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * For each terrain model, prepare its entity as well
	 * @param entity Entity to be prepared
	 */
	private void loadModelMatrix(Terrain terrain) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(
				new Vector3f(terrain.getX(),0,terrain.getZ()), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}

}
