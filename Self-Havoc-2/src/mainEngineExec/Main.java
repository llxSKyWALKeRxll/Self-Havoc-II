package mainEngineExec;

import org.lwjgl.opengl.Display;

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
		ModelRenderer modelRenderer = new ModelRenderer();
		
//		Creating a static shader
		StaticShader shader = new StaticShader();
		
//		Object vertices
		float[] vertices = {
				-0.5f, 0.5f, 0,
				-0.5f, -0.5f, 0,
				0.5f, -0.5f, 0,
				0.5f, 0.5f, 0,
		  };
		
//		Object indices
		int[] indices = {
				0,1,3,
				3,1,2
		};
		
		float[] textureCoords = {
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
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			
//			Prepare the Renderer / Color
			modelRenderer.prepare();
//			Activate the shaders
			shader.start();
//			Render the object
			modelRenderer.renderModel(texturedModel);
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
