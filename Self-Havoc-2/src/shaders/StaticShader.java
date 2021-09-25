package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import utils.Maths;

public class StaticShader extends ShaderProgram{
	
	private static final String vertexFile = "src/shaders/vertex.shader";
	private static final String fragmentFile = "src/shaders/fragment.shader";
	
	private int transformationMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;

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
	}

	/**
	 * Fetches the location of all the uniform variables currently attached to the program object
	 */
	@Override
	protected void getAllUniformLocations() {
		transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
		projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
		viewMatrixLocation = super.getUniformLocation("viewMatrix");
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
	

}
