package models;

/**
 * This class represents any 3D model that is stored in memory
 * @author Vansh Pratap Singh
 *
 */
public class RawModel {
	
	private int VAOid;
	private int vertexCtr;
	
//	Constructor: Initializes the VAOid and vertexCtr
	public RawModel(int VAOid, int vertexCtr) {
		this.VAOid = VAOid;
		this.vertexCtr = vertexCtr;
	}

	/**
	 * Getter method for Vertex Array Object ID
	 * @return int: VAOid
	 */
	public int getVAOid() {
		return VAOid;
	}

	/**
	 * Getter method for Vertex Counter
	 * @return int: vertexCtr
	 */
	public int getVertexCtr() {
		return vertexCtr;
	}
	

}
