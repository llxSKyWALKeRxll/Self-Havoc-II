package textures;

/**
 * This class is a generic representation of any texture that can be used in the game
 * @author Vansh Pratap Singh
 *
 */
public class ModelTexture {
	
	private int textureId;
	
	public ModelTexture(int textureId) {
		this.textureId = textureId;
	}
	
	/**
	 * Getter method for texture id
	 * @return textureId
	 */
	public int getTextureId() {
		return this.textureId;
	}

}
