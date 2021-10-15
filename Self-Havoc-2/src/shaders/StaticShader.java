package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import utils.Maths;

public class StaticShader extends ShaderProgram{
	
	private static final String vertexFile = "src/shaders/vertex.shader";
	private static final String fragmentFile = "src/shaders/fragment.shader";
	
	private int transformationMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int lightPositionLocation;
	private int lightColourLocation;
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int useFakeLightingLocation;
	private int skyColourLocation;

	public StaticShader() {
//		Call parent constructor to create, load and read shaders
		super(vertexFile, fragmentFile);
	}

	/**
	 * Binds attributes to variables initialized inside the shaders
	 */
	@Override
	protected void bindAttributes() {
//		Bind attribute to variable in the shader (vertex)
		super.bindAttribute(0, "pos");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	/**
	 * Fetches the location of all the uniform variables currently attached to the program object
	 */
	@Override
	protected void getAllUniformLocations() {
		transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
		projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
		viewMatrixLocation = super.getUniformLocation("viewMatrix");
		lightPositionLocation = super.getUniformLocation("lightPosition");
		lightColourLocation = super.getUniformLocation("lightColour");
		shineDamperLocation = super.getUniformLocation("shineDamper");
		reflectivityLocation = super.getUniformLocation("reflectivity");
		useFakeLightingLocation = super.getUniformLocation("useFakeLighting");
		skyColourLocation = super.getUniformLocation("skyColour");
	}
	
	/**
	 * Load the sky colour (for emulation of fog in game)
	 * @param r
	 * @param g
	 * @param b
	 */
	public void loadSkyColour(float r, float g, float b) {
		super.loadUniformVector(skyColourLocation, new Vector3f(r,g,b));
	}
	
	/**
	 * Load the passed value onto the fake lighting boolean
	 * @param use
	 */
	public void loadFakeLightingBoolean(boolean use) {
		super.loadUniformBoolean(useFakeLightingLocation, use);
	}
	
	/**
	 * Loads the matrix onto the uniform transformation matrix in the vertex shader
	 * @param matrix matrix object that is to be loaded onto the uniform transformation matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		 super.loadUniformMatrix(transformationMatrixLocation, matrix);
	}
	
	/**
	 * Loads the matrix onto the uniform projection matrix in the vertex shader
	 * @param matrix
	 */
	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadUniformMatrix(projectionMatrixLocation, matrix);
	}
	
	/**
	 * Loads the matrix onto the uniform view matrix in the vertex shader
	 * @param matrix
	 */
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadUniformMatrix(viewMatrixLocation, viewMatrix);
	}
	
	/**
	 * Loads the light object onto the shader program
	 * @param light Light object
	 */
	public void loadLight(Light light)
	{
		super.loadUniformVector(lightPositionLocation, light.getPosition());
		super.loadUniformVector(lightColourLocation, light.getColour());
	}
	
	/**
	 * Loads parameters necessary for specular lighting onto the shader
	 * @param damper Damper factor
	 * @param reflectivity Reflectivity factor
	 */
	public void loadShineFactors(float damper, float reflectivity)
	{
		super.loadUniformFloat(shineDamperLocation, damper);
		super.loadUniformFloat(reflectivityLocation, reflectivity);
	}
	

}
