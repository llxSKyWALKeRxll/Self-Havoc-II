package mainEngineExec;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import shaders.StaticShader;
import textures.ModelTexture;

/**
 * Main game class
 * @author Vansh Pratap Singh
 *
 */
public class Main {

	public static void main(String[] args) {
		
//		Create the display window
		DisplayManager.createDisplay();
		
//		Objects for ModelLoader and ModelRenderer
		ModelLoader modelLoader = new ModelLoader();
		
//		Creating a static shader
		StaticShader shader = new StaticShader();
		ModelRenderer modelRenderer = new ModelRenderer(shader);
		
//		Object vertices
//		float[] vertices = {
//				-0.5f, 0.5f, 0,
//				-0.5f, -0.5f, 0,
//				0.5f, -0.5f, 0,
//				0.5f, 0.5f, 0,
//		  };
		
		float[] vertices = {
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		};
		
//		Object indices
//		int[] indices = {
//				0,1,3,
//				3,1,2
//		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		
//		float[] textureCoords = {
//			0,0,
//			0,1,
//			1,1,
//			1,0
//		};
		
		float [] textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		};
		
//		Loading positions to VAO
		RawModel model = modelLoader.loadToVAO(vertices, indices, textureCoords);
//		Creating a new model texture
		ModelTexture texture = new ModelTexture(modelLoader.loadTexture("texture2"));
//		Creating a textured model
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity entity1 = new Entity(texturedModel, new Vector3f(0,0,-2f),0,0,0,1);
		Entity entity2 = new Entity(texturedModel, new Vector3f(-3f,0,-4f),0,0,0,1);
		Entity entity3 = new Entity(texturedModel, new Vector3f(3f,0,-4f),0,0,0,1);
		
		Camera camera = new Camera();
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
//			entity.increasePosition(0, 0, -0.05f);
			camera.move();
			entity1.increaseRotation(1f, 1f, 0);
			entity2.increaseRotation(0, 1f, 0);
			entity3.increaseRotation(1f, 0, 0);
//			Prepare the Renderer / Color
			modelRenderer.prepare();
//			Activate the shaders
			shader.start();
			shader.loadViewMatrix(camera);
//			Render the object
			modelRenderer.renderModel(entity1, shader);
			modelRenderer.renderModel(entity2, shader);
			modelRenderer.renderModel(entity3, shader);
//			Stop the shaders
			shader.stop();
//			Update the display
			DisplayManager.updateDisplay();
			
		}
		
//		Recycling
		modelLoader.formatBin();
		shader.recycle();
//		Close the display window
		DisplayManager.closeDisplay();

	}

}
