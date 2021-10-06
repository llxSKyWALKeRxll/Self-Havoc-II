package mainEngineExec;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import renderEngine.OBJLoader;
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
		
//		float[] vertices = {
//				-0.5f,0.5f,-0.5f,	
//				-0.5f,-0.5f,-0.5f,	
//				0.5f,-0.5f,-0.5f,	
//				0.5f,0.5f,-0.5f,		
//				
//				-0.5f,0.5f,0.5f,	
//				-0.5f,-0.5f,0.5f,	
//				0.5f,-0.5f,0.5f,	
//				0.5f,0.5f,0.5f,
//				
//				0.5f,0.5f,-0.5f,	
//				0.5f,-0.5f,-0.5f,	
//				0.5f,-0.5f,0.5f,	
//				0.5f,0.5f,0.5f,
//				
//				-0.5f,0.5f,-0.5f,	
//				-0.5f,-0.5f,-0.5f,	
//				-0.5f,-0.5f,0.5f,	
//				-0.5f,0.5f,0.5f,
//				
//				-0.5f,0.5f,0.5f,
//				-0.5f,0.5f,-0.5f,
//				0.5f,0.5f,-0.5f,
//				0.5f,0.5f,0.5f,
//				
//				-0.5f,-0.5f,0.5f,
//				-0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,0.5f
//		};
//		
////		Object indices
////		int[] indices = {
////				0,1,3,
////				3,1,2
////		};
//		
//		int[] indices = {
//				0,1,3,	
//				3,1,2,	
//				4,5,7,
//				7,5,6,
//				8,9,11,
//				11,9,10,
//				12,13,15,
//				15,13,14,	
//				16,17,19,
//				19,17,18,
//				20,21,23,
//				23,21,22
//		};
//		
////		float[] textureCoords = {
////			0,0,
////			0,1,
////			1,1,
////			1,0
////		};
//		
//		float [] textureCoords = {
//				0,0,
//				0,1,
//				1,1,
//				1,0,			
//				0,0,
//				0,1,
//				1,1,
//				1,0,			
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0
//		};
		
//		Loading positions to VAO
//		RawModel model = modelLoader.loadToVAO(vertices, indices, textureCoords);
		RawModel model1 = OBJLoader.loadObjModel("tree2", modelLoader);
		RawModel model2 = OBJLoader.loadObjModel("tree1", modelLoader);
//		Creating a new model texture
		ModelTexture texture = new ModelTexture(modelLoader.loadTexture("texture3"));
//		Creating a textured model
		TexturedModel texturedModel1 = new TexturedModel(model1, texture);
		TexturedModel texturedModel2 = new TexturedModel(model2, texture);
//		TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(modelLoader.loadTexture("stallTexture")));
		
		ModelTexture lightApplyTexture1 = texturedModel1.getModelTexture();
		lightApplyTexture1.setShineDamper(10);
		lightApplyTexture1.setReflectivity(1);
		
		ModelTexture lightApplyTexture2 = texturedModel2.getModelTexture();
		lightApplyTexture2.setShineDamper(10);
		lightApplyTexture2.setReflectivity(1);
		
		Entity entity1 = new Entity(texturedModel1, new Vector3f(0,-2f,-10f),0,0,0,1);
		Entity entity2 = new Entity(texturedModel2, new Vector3f(-35f,-4f,-80f),0,0,0,1);
		Entity entity3 = new Entity(texturedModel2, new Vector3f(35f,-4f,-80f),0,0,0,1);
		
		Light light = new Light(new Vector3f(0,0,-10), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
//			entity1.increasePosition(0, -1f, -0.05f);
			camera.move();
			entity1.increaseRotation(0, 1f, 0);
			entity2.increaseRotation(0, 1f, 0);
			entity3.increaseRotation(0, 1f, 0);
//			Prepare the Renderer / Color
			modelRenderer.prepare();
//			Activate the shaders
			shader.start();
			shader.loadLight(light);
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
