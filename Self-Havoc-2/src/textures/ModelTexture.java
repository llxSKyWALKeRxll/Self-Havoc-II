package textures;

/**
 * This class is a generic representation of any texture that can be used in the game
 * @author Vansh Pratap Singh
 *
 */
public class ModelTexture {
	
	private int textureId;
	private float shineDamper = 1;
	private float reflectivity = 0;
	
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

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	

}
