package mainEngineExec;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class Main {

	public static void main(String[] args) {
		
//		Create the display window
		DisplayManager.createDisplay();
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			
//			Update the display
			DisplayManager.updateDisplay();
			
		}
		
//		Close the display window
		DisplayManager.closeDisplay();

	}

}
