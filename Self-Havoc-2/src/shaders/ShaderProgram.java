package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * A generic template class for shaders. Shaders can be loaded and debugged by using this class.
 * @author Vansh Pratap Singh
 *
 */
public abstract class ShaderProgram {
	
	private int programId, vertexShaderId, fragmentShaderId;
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
//		Bridge b/w vertex & fragment shaders
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		bindAttributes();
		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		getAllUniformLocations();
	}
	
	/**
	 * Fetches the location of all uniform variables currently attached to the program object
	 */
	protected abstract void getAllUniformLocations();
	
	/**
	 * Fetches the location of the uniform variable passed as the parameter
	 * @param varname Uniform variable whose location is to be fetched
	 * @return int Location
	 */
	protected int getUniformLocation(String varname)
	{
		return GL20.glGetUniformLocation(programId, varname);
	}
	
	/**
	 * Loads the passed float value onto the uniform variable for the shader object
	 * @param location Location where the value is to be attached
	 * @param val Value that is to be loaded
	 */
	protected void loadUniformFloat(int location, float val)
	{
		GL20.glUniform1f(location, val);
	}
	
	/**
	 * Loads the passed vector value onto the uniform variable for the shader object
	 * @param location Location where the value is to be attached
	 * @param vector Value that is to be loaded
	 */
	protected void loadUniformVector(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	/**
	 * Loads the passed boolean value onto the uniform variable for the shader object
	 * @param location Location where the value is to be attached
	 * @param val Value that is to be loaded
	 */
	protected void loadUniformBoolean(int location, boolean val)
	{
		float intVal = 0;
		if(val) intVal = 1;
		GL20.glUniform1f(location, intVal);
	}
	
	/**
	 * Loads the passed matrix value onto the uniform variable for the shader object
	 * @param location Location where the value is to be attached
	 * @param matrix Value that is to be loaded
	 */
	protected void loadUniformMatrix(int location, Matrix4f matrix)
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	/**
	 * Starts execution of shader program
	 */
	public void start() {
		GL20.glUseProgram(programId);
	}
	
	/**
	 * Stops execution of shader program
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	/**
	 * Recycles the currently active shader programs
	 */
	public void recycle() {
		stop();
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(programId);
	}
	
	/**
	 * This method links the inputs to the shaders
	 */
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attributeIndex, String varName) {
		GL20.glBindAttribLocation(programId, attributeIndex, varName);
	}
	
	/**
	 * This method loads any shader from the text file onto a StringBuilder object
	 * @param fileName Specified file path
	 * @param shaderType Type of Shader
	 * @return int Shader Id
	 */
	private static int loadShader(String fileName, int shaderType) {
//		Create an empty string builder for storing shader
		StringBuilder shaderSource = new StringBuilder();
//		Reading the shader
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch(Exception e) {
			System.err.println("File could not be read successfully!");
			e.printStackTrace();
			System.exit(-1);
		}
//		Create and generate shader id
		int shaderId = GL20.glCreateShader(shaderType);
		GL20.glShaderSource(shaderId, shaderSource);
		GL20.glCompileShader(shaderId);
//		If shader could not be compiled, then print the log as output
		if(GL20.glGetShader(shaderType, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderId, 1000));
			System.out.println("An error was encountered!\nThe shader could not be compiled successfully!");
			System.exit(-1);
		}
		return shaderId;
	}
	
}
