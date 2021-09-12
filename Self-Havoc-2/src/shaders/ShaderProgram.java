package shaders;

import java.io.BufferedReader;
import java.io.FileReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * A generic template class for shaders. Shaders can be loaded and debugged by using this class.
 * @author Vansh Pratap Singh
 *
 */
public abstract class ShaderProgram {
	
	private int programId, vertexShaderId, fragmentShaderId;
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
//		Bridge b/w vertex & fragment shaders
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		bindAttributes();
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
