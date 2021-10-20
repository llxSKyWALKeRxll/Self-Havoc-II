package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.ModelLoader;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 * This class represents the terrain of our game
 * @author Vansh Pratap Singh
 *
 */
public class Terrain {
	
	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float PIXEL_COLOUR_RANGE = 256*256*256;
	private float x, z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;

	public Terrain(int xGrid, int zGrid, ModelLoader loader, 
			TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap) {
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = xGrid*SIZE;
		this.z = zGrid*SIZE;
		this.model = generateTerrain(loader, heightMap);
	}
	
	/**
	 * Generate a straight terrain
	 * @param loader ModelLoader
	 * @return RawModel
	 */
	private RawModel generateTerrain(ModelLoader loader, String heightMap){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = getHeight(j, i, image);
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, image); 
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, indices, textureCoords, normals);
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}
	
	/**
	 * Returns the normal for different parts (vertex) of the terrain with varying heights
	 * @param x x coordinate
	 * @param z z coordinate
	 * @param image heightmap image
	 * @return normal vector
	 */
	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x-1, z, image);
		float heightR = getHeight(x+1, z, image);
		float heightU = getHeight(x, z+1, image);
		float heightD = getHeight(x, z-1, image);
		Vector3f normal = new Vector3f(heightL-heightR,2f,heightD-heightU);
		normal.normalise();
		return normal;
	}
	
	/**
	 * Return the height represented by a any given pixel on the heighmap
	 * @return height 
	 */
	private float getHeight(int x, int z, BufferedImage image) {
		if(x<0 || x>= image.getHeight() || z<0 || z>= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += PIXEL_COLOUR_RANGE/2f;
		height /= PIXEL_COLOUR_RANGE/2f;
		height *= MAX_HEIGHT;
		return height;
	}

	
	
}
