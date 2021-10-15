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
	
	private boolean isTransparent = false;
	private boolean useFakeLighting = false;
	
	public ModelTexture(int textureId) {
		this.textureId = textureId;
	}
	
	/**
	 * Getter method to reveal as to whether a model is transparent or not
	 * @return isTransparent 
	 */
	public boolean isTransparent() {
		return isTransparent;
	}

	/**
	 * Setter method for transparency of an object
	 * @param isTransparent
	 */
	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}

	/**
	 * Getter method for fake lighting of an object
	 * @param useFakeLighting
	 */
	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	/**
	 * Setter method for fake lighting of an object
	 * @param useFakeLighting
	 */
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
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
