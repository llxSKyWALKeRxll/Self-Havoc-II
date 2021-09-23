package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	private static final String vertexFile = "src/shaders/vertex.shader";
	private static final String fragmentFile = "src/shaders/fragment.shader";
	
	private int transformationMatrixLocation;

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
	}
	
	/**
	 * Loads the passed values onto the uniform transformation matrix
	 * @param matrix matrix object that is to be loaded onto the uniform transformation matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		 super.loadUniformMatrix(transformationMatrixLocation, matrix);
	}
	
	

}
