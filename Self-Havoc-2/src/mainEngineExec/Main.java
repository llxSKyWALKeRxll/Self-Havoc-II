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
		
		
		RawModel treeModel1 = OBJLoader.loadObjModel("tree1", modelLoader);
		RawModel treeModel2 = OBJLoader.loadObjModel("tree2", modelLoader);
		RawModel grassModel1 = OBJLoader.loadObjModel("grassObject1", modelLoader);
		RawModel bushModel1 = OBJLoader.loadObjModel("bushObject1", modelLoader);
		RawModel flowerModel1 = OBJLoader.loadObjModel("grassObject1", modelLoader);
		
		ModelTexture groundTexture1 = new ModelTexture(modelLoader.loadTexture("grass1"));
		ModelTexture grassTexture1 = new ModelTexture(modelLoader.loadTexture("grassTexture1"));
		ModelTexture treeTexture1 = new ModelTexture(modelLoader.loadTexture("treeTexture1"));
		ModelTexture bushTexture1 = new ModelTexture(modelLoader.loadTexture("bushTexture1"));
		ModelTexture flowerTexture1 = new ModelTexture(modelLoader.loadTexture("flowerTexture1"));
		
		Terrain groundTerrain1 = new Terrain(0,-1,modelLoader,groundTexture1);
		Terrain groundTerrain2 = new Terrain(-1,-1,modelLoader,groundTexture1);

		TexturedModel treeTexturedModel1 = new TexturedModel(treeModel1, treeTexture1);
		TexturedModel treeTexturedModel2 = new TexturedModel(treeModel2, treeTexture1);
		TexturedModel grassTexturedModel1 = new TexturedModel(grassModel1, grassTexture1);
		TexturedModel bushTexturedModel1 = new TexturedModel(bushModel1, bushTexture1);
		TexturedModel flowerTexturedModel1 = new TexturedModel(flowerModel1, flowerTexture1);
		
		grassTexturedModel1.getModelTexture().setTransparent(true);
		bushTexturedModel1.getModelTexture().setTransparent(true);
		flowerTexturedModel1.getModelTexture().setTransparent(true);
		grassTexturedModel1.getModelTexture().setUseFakeLighting(true);
		bushTexturedModel1.getModelTexture().setUseFakeLighting(true);
		flowerTexturedModel1.getModelTexture().setUseFakeLighting(true);
		
		ModelTexture lightApplyTexture1 = treeTexturedModel1.getModelTexture();
		lightApplyTexture1.setShineDamper(10);
		lightApplyTexture1.setReflectivity(1);
		
		ModelTexture lightApplyTexture2 = treeTexturedModel1.getModelTexture();
		lightApplyTexture2.setShineDamper(10);
		lightApplyTexture2.setReflectivity(1);
		

//		Entity entity1 = new Entity(grassTexturedModel1, new Vector3f(0f,0f,-10f),0,0,0,0.5f);
//		Entity entity1 = new Entity(texturedModel1, new Vector3f(0,0f,-10f),0,0,0,0.5f);
//		Entity entity2 = new Entity(texturedModel2, new Vector3f(-35f,0f,-80f),0,0,0,1);
//		Entity entity3 = new Entity(texturedModel2, new Vector3f(35f,0f,-80f),0,0,0,1);
		
		Random random = new Random();
		
		List<Entity> treeModels = new ArrayList<Entity>();
		List<Entity> grassModels = new ArrayList<Entity>();
		List<Entity> bushModels = new ArrayList<Entity>();
		List<Entity> flowerModels = new ArrayList<Entity>();
		
		for(int i=0; i<300; i++) {
			float x = random.nextFloat()*300-230;
			float y = 0;
			float z = random.nextFloat()*-600;
			treeModels.add(new Entity(treeTexturedModel1, new Vector3f(x,y,z), 0f,
					0f, 0f, 0.2f));
			x = random.nextFloat()*800-100;
			y = 0;
			z = random.nextFloat()*-300;
			treeModels.add(new Entity(treeTexturedModel2, new Vector3f(x,y,z), 0f,
					0f, 0f, 1.4f));
			x = random.nextFloat()*500-230;
			y = 0;
			z = random.nextFloat()*-300;
			grassModels.add(new Entity(grassTexturedModel1, new Vector3f(x,y,z), 0f,
					0f, 0f, 0.4f));
			x = random.nextFloat()*500-230;
			y = 0;
			z = random.nextFloat()*-300;
			bushModels.add(new Entity(bushTexturedModel1, new Vector3f(x,y,z), 0f,
					0f, 0f, 0.3f));
			x = random.nextFloat()*600-300;
			y = 0;
			z = random.nextFloat()*-400;
			flowerModels.add(new Entity(flowerTexturedModel1, new Vector3f(x,y,z), 0f,
					0f, 0f, 0.8f));
		}
		
		Light light = new Light(new Vector3f(20000,40000,20000), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			camera.move();
			
			renderer.processTerrain(groundTerrain1);
			renderer.processTerrain(groundTerrain2);
//			entity1.increaseRotation(0, 1f, 0);
//			entity2.increaseRotation(0, 1f, 0);
//			entity3.increaseRotation(0, 1f, 0);
//			
			for(Entity tree: treeModels) {
				renderer.processEntity(tree);
			}
			
			for(Entity grass: grassModels) {
				renderer.processEntity(grass);
			}
			
			for(Entity bush: bushModels) {
				renderer.processEntity(bush);
			}
			
			for(Entity flower: flowerModels) {
				renderer.processEntity(flower);
			}
			
//			renderer.processEntity(entity1);
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
