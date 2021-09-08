package mainEngineExec;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import renderEngine.RawModel;

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
		
//		Object vertices
		float[] vertices = {
			    0.5f, 0.5f, 0f,
			    -0.5f, -0.5f, 0f,
			    0.5f, -0.5f, 0f,
			    -0.5f, -0.5f, 0f,
			    0.5f, 0.5f, 0f,
			    0.5f, 0.5f, 0f
			  };
		
//		Loading positions to VAO
		RawModel model = modelLoader.loadToVAO(vertices);
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			
//			Prepare the Renderer / Color
			modelRenderer.prepare();
//			Render the object
			modelRenderer.renderModel(model);
//			Update the display
			DisplayManager.updateDisplay();
			
		}
		
//		Recycle the VAOs and the VBOs
		modelLoader.formatBin();
//		Close the display window
		DisplayManager.closeDisplay();

	}

}
