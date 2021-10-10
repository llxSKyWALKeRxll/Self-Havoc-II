package mainEngineExec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import renderEngine.ModelLoader;
import renderEngine.EntityRenderer;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

/**
 * Main game class
 * @author Vansh Pratap Singh
 *
 */
public class Main {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		ModelLoader modelLoader = new ModelLoader();
		
		
		RawModel model1 = OBJLoader.loadObjModel("tree1", modelLoader);
		RawModel model2 = OBJLoader.loadObjModel("tree2", modelLoader);
		
		ModelTexture grassTexture1 = new ModelTexture(modelLoader.loadTexture("grass1"));
		Terrain terrain1 = new Terrain(0,-1,modelLoader,grassTexture1);
		Terrain terrain2 = new Terrain(-1,-1,modelLoader,grassTexture1);

		ModelTexture texture = new ModelTexture(modelLoader.loadTexture("treeTexture1"));

		TexturedModel texturedModel1 = new TexturedModel(model1, texture);
		TexturedModel texturedModel2 = new TexturedModel(model2, texture);
		
		ModelTexture lightApplyTexture1 = texturedModel1.getModelTexture();
		lightApplyTexture1.setShineDamper(10);
		lightApplyTexture1.setReflectivity(1);
		
		ModelTexture lightApplyTexture2 = texturedModel1.getModelTexture();
		lightApplyTexture2.setShineDamper(10);
		lightApplyTexture2.setReflectivity(1);
		
//		Entity entity1 = new Entity(texturedModel1, new Vector3f(0,0f,-10f),0,0,0,0.5f);
//		Entity entity2 = new Entity(texturedModel2, new Vector3f(-35f,0f,-80f),0,0,0,1);
//		Entity entity3 = new Entity(texturedModel2, new Vector3f(35f,0f,-80f),0,0,0,1);
		
		Random random = new Random();
		
		List<Entity> treeModels = new ArrayList<Entity>();
		
		for(int i=0; i<600; i++) {
			float x = random.nextFloat()*400-250;
			float y = 0;
			float z = random.nextFloat()*-500;
			treeModels.add(new Entity(texturedModel1, new Vector3f(x,y,z), 0f,
					0f, 0f, 0.2f));
			x = random.nextFloat()*500-200;
			y = 0;
			z = random.nextFloat()*-300;
			treeModels.add(new Entity(texturedModel2, new Vector3f(x,y,z), 0f,
					0f, 0f, 1.4f));
		}
		
		Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			camera.move();
			
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);
//			entity1.increaseRotation(0, 1f, 0);
//			entity2.increaseRotation(0, 1f, 0);
//			entity3.increaseRotation(0, 1f, 0);
//			
			for(Entity tree: treeModels) {
				renderer.processEntity(tree);
			}
//			
//			renderer.processEntity(entity1);
//			renderer.processEntity(entity2);
//			renderer.processEntity(entity3);

			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.recycle();
		modelLoader.formatBin();

		DisplayManager.closeDisplay();

	}

}
