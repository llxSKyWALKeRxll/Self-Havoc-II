package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * This class configures, modifies & manages all the operations related to the display window
 * @author Vansh Pratap Singh
 *
 */
public class DisplayManager {
	
	private static final int WIDTH = 1366, HEIGHT = 768;
	private static final int FPS = 60;
	private static final String TITLE = "Self Havoc-II";
	
	/**
	 * This method creates the display window
	 */
	public static void createDisplay() {
		
//		Version 3.2 of OpenGL is passed as the parameter
		ContextAttribs attributes = new ContextAttribs(3,2);
		attributes.withForwardCompatible(true);
		attributes.withProfileCore(true);
		
		try {
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attributes);
			Display.setTitle(TITLE);
			
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			
		}
		
//		Use the complete display to render objects (bottomx, bottomy, WIDTH, HEIGHT) 
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	/**
	 * This method updates the display window per frame
	 */
	public static void updateDisplay() {
		
//		Synchronize the display to run w.r.t. to the set FPS
		Display.sync(FPS);
//		Update the display
		Display.update();
		
	}
	
	/**
	 * This method closes the display window as and when required
	 */
	public static void closeDisplay() {
		
//		Closes the display
		Display.destroy();
		
	}
	
}
