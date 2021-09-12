package mainEngineExec;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import renderEngine.RawModel;
import shaders.StaticShader;

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
		
//		Loading positions to VAO
		RawModel model = modelLoader.loadToVAO(vertices, indices);
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			
//			Prepare the Renderer / Color
			modelRenderer.prepare();
//			Activate the shaders
			shader.start();
//			Render the object
			modelRenderer.renderModel(model);
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
