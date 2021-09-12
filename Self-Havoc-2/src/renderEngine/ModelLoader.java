package renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * This class can be used to load models (VAO & VBO data storage)
 * @author Vansh Pratap Singh
 *
 */
public class ModelLoader {
	
//	Keep track of the VAOs and VBOs
	private List<Integer> VAOs = new ArrayList<Integer>();
	private List<Integer> VBOs = new ArrayList<Integer>();
	
	/**
	 * Load data to specified positions in Vertex Array Object
	 * @param positions Position where the data is to be loaded in the VAO
	 * @return RawModel Returns the VAO
	 */
	public RawModel loadToVAO(float[] positions, int[] indices) {
//		Create a Vertex Array Object
		int VAOid = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, positions);
		unbindVAO();
//		Positions divided by 3 since triangle has three vertices
		return new RawModel(VAOid, indices.length);
	}
	
	/**
	 * Create a Vertex Array Object
	 * @return int Returns VAO Id
	 */
	private int createVAO() {
//		Generate a VAO Id
		int VAOid = GL30.glGenVertexArrays();
		VAOs.add(VAOid);
//		Bind/Activate the VAO
		GL30.glBindVertexArray(VAOid);
		return VAOid;
	}
	
	/**
	 * Stores data within the VAO in an Attribute list
	 * @param attributeNumber The attribute number
	 * @param data The data to be stored within the attribute list
	 */
	private void storeDataInAttributeList(int attributeNumber, float[] data) {
//		Create a Vertex Buffer Object / VBO
		int VBOid = GL15.glGenBuffers();
		VBOs.add(VBOid);
//		Bind/Activate the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOid);
//		Convert data to FloatBuffer type
		FloatBuffer buffer = storeDataInFloatBuffer(data);
//		Store the data into the VBO
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
//		Store the VBO into the VAO
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
//		Unbind the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Converts data in a float array to float buffer (since data has to be stored in a VBO in a float buffer)
	 * @param data Data in a float array
	 * @return FloatBuffer
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
//		Create a float buffer
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
//		Store data to buffer
		buffer.put(data);
//		Make the buffer readable
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Binds data onto the indices buffer
	 * @param indices Indices where the data has to be stored
	 */
	private void bindIndicesBuffer(int[] indices) {
		int VBOid = GL15.glGenBuffers();
		VBOs.add(VBOid);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBOid);
//		Convert data to IntBuffer type
		IntBuffer buffer = storeDataInIntBuffer(indices);
//		Store to VBO
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Convert data of int type to IntBuffer (needed for Indices Buffer)
	 * @param data Data to be converted
	 * @return buffer
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
//		Create an IntBuffer
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
//		Store data to buffer
		buffer.put(data);
//		Make the buffer readable
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Unbinds the VAO
	 */
	private void unbindVAO() {
//		Unbind the VAO
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * This method basically recycles the VAOs and the VBOs that were stored or used previously
	 */
	public void formatBin() {
		
		for(int VAO: VAOs) {
			GL30.glDeleteVertexArrays(VAO);
		}
		
		for(int VBO: VBOs) {
			GL15.glDeleteBuffers(VBO);
		}
	}

}
