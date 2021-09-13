package models;

import textures.ModelTexture;

/**
 * This class represents any textured model of the game
 * @author Vansh Pratap Singh
 *
 */
public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture modelTexture;
	
	public TexturedModel(RawModel rawModel, ModelTexture modelTexture) {
		this.rawModel = rawModel;
		this.modelTexture = modelTexture;
	}

	/**
	 * Getter method for the raw model object
	 * @return
	 */
	public RawModel getRawModel() {
		return rawModel;
	}

	/**
	 * Getter method for the Model texture object
	 * @return
	 */
	public ModelTexture getModelTexture() {
		return modelTexture;
	}
	
	

}
