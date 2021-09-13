package shaders;

public class StaticShader extends ShaderProgram{
	
	private static final String vertexFile = "src/shaders/vertex.shader";
	private static final String fragmentFile = "src/shaders/fragment.shader";

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
	
	

}
