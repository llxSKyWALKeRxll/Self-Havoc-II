package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

/**
 * Used to load models that have been exported in the obj file format onto the game
 * @author Vansh Pratap Singh
 *
 */
public class OBJLoader {
	
//	Read the obj file associated with the passed model
	public static RawModel loadObjModel(String filePath, ModelLoader loader)
	{
		FileReader fileReader = null;
		try {
		fileReader = new FileReader(new File("res/" + filePath + ".obj"));
		}
		catch (Exception e) {
			System.out.println("Error loading the .obj file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		String line;
		float[] verticesArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		
//		read file loop
		try {
			
			while(true)
			{
				line = reader.readLine();
				String[] currentLine = line.split(" ");
//				Vertex coordinates
				if(line.startsWith("v "))
				{
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
//				Texture coordinates
				else if(line.startsWith("vt "))
				{
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
//				Normal coordinates
				else if(line.startsWith("vn "))
				{
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
//				Face coordinates
				else if(line.startsWith("f "))
				{
					texturesArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while(line != null)
			{
				if(!line.startsWith("f "))
				{
					line = reader.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
				
				line = reader.readLine();
			}
			
			reader.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		
		int vertexPtr = 0;
		
//		Loads the vertices into the vertices array
		for(Vector3f vertex: vertices)
		{
			verticesArray[vertexPtr++] = vertex.x;
			verticesArray[vertexPtr++] = vertex.y;
			verticesArray[vertexPtr++] = vertex.z;
		}
		
//		Load the indices into the indices array
		for(int i=0; i<indices.size(); i++)
		{
			indicesArray[i] = indices.get(i);
		}
		
		return loader.loadToVAO(verticesArray, indicesArray, texturesArray);
		
	}
	
//	Process each vertex individually
	private static void processVertex(String[] vertexData, List<Integer> indices,
			List<Vector2f> textures, List<Vector3f> normals, float[] texturesArray,
			float[] normalsArray) 
	{
		
		int currVertexPtr = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currVertexPtr);
		Vector2f currTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
		texturesArray[currVertexPtr * 2] = currTexture.x;
		texturesArray[currVertexPtr * 2 + 1] = 1- currTexture.y;
		Vector3f currNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currVertexPtr * 3] = currNormal.x;
		normalsArray[currVertexPtr * 3 + 1] = currNormal.y;
		normalsArray[currVertexPtr * 3 + 2] = currNormal.z;
	}

}
