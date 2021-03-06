package mainEngineExec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 * Main game class
 * @author Vansh Pratap Singh
 *
 */
public class Main {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		ModelLoader modelLoader = new ModelLoader();
		
		TerrainTexture backgroundTexture = new TerrainTexture(modelLoader.loadTexture("grass1"));
		TerrainTexture rTexture = new TerrainTexture(modelLoader.loadTexture("sandPathway1"));
		TerrainTexture gTexture = new TerrainTexture(modelLoader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(modelLoader.loadTexture("tilePathway1"));
		TerrainTexture blendMap = new TerrainTexture(modelLoader.loadTexture("blendMap"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, 
				gTexture, bTexture);
		
		RawModel mainPlayerModel1 = OBJLoader.loadObjModel("playerModel", modelLoader);
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
		ModelTexture mainPlayerTexture1 = new ModelTexture(modelLoader.loadTexture("playerTexture"));
		
		Terrain groundTerrain1 = new Terrain(0,-1,modelLoader,texturePack,blendMap,"heightMap3");
//		Terrain groundTerrain2 = new Terrain(-1,-1,modelLoader,texturePack,blendMap,"heightMap3");

		TexturedModel treeTexturedModel1 = new TexturedModel(treeModel1, treeTexture1);
		TexturedModel treeTexturedModel2 = new TexturedModel(treeModel2, treeTexture1);
		TexturedModel grassTexturedModel1 = new TexturedModel(grassModel1, grassTexture1);
		TexturedModel bushTexturedModel1 = new TexturedModel(bushModel1, bushTexture1);
		TexturedModel flowerTexturedModel1 = new TexturedModel(flowerModel1, flowerTexture1);
		TexturedModel mainPlayerTexturedModel1 = new TexturedModel(mainPlayerModel1, mainPlayerTexture1);
		
		grassTexturedModel1.getModelTexture().setTransparent(true);
		bushTexturedModel1.getModelTexture().setTransparent(true);
		flowerTexturedModel1.getModelTexture().setTransparent(true);
		grassTexturedModel1.getModelTexture().setUseFakeLighting(true);
		bushTexturedModel1.getModelTexture().setUseFakeLighting(true);
		flowerTexturedModel1.getModelTexture().setUseFakeLighting(true);
		mainPlayerTexturedModel1.getModelTexture().setUseFakeLighting(true);
		
		ModelTexture lightApplyTexture1 = treeTexturedModel1.getModelTexture();
		lightApplyTexture1.setShineDamper(10);
		lightApplyTexture1.setReflectivity(1);
		
		ModelTexture lightApplyTexture2 = treeTexturedModel1.getModelTexture();
		lightApplyTexture2.setShineDamper(10);
		lightApplyTexture2.setReflectivity(1);
		
		List<Entity> treeModels = new ArrayList<Entity>();
		List<Entity> grassModels = new ArrayList<Entity>();
		List<Entity> bushModels = new ArrayList<Entity>();
		List<Entity> flowerModels = new ArrayList<Entity>();
		List<Entity> allEntities = new ArrayList<Entity>();
		
		Random random = new Random(676452);
		
		for(int i=0; i<600; i++) {
			if(i%20 == 0) {
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = groundTerrain1.getHeightOfTerrain(x, z);
				allEntities.add(new Entity(bushTexturedModel1, new Vector3f(x,y,z), 0f,
						random.nextFloat()*360, 0, 0.9f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = groundTerrain1.getHeightOfTerrain(x, z);
				allEntities.add(new Entity(treeTexturedModel1, new Vector3f(x,y,z), 0f,
						random.nextFloat()*360, 0, 0.6f));
			}
			if(i%5==0) {
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = groundTerrain1.getHeightOfTerrain(x, z);
				allEntities.add(new Entity(treeTexturedModel2, new Vector3f(x,y,z), 0f,
						random.nextFloat()*360, 0, 3.9f));
			}
			if(i%7==0) {
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = groundTerrain1.getHeightOfTerrain(x, z);
				allEntities.add(new Entity(grassTexturedModel1, new Vector3f(x,y,z), 0f,
						random.nextFloat()*360, 0, 0.6f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = groundTerrain1.getHeightOfTerrain(x, z);
				allEntities.add(new Entity(flowerTexturedModel1, new Vector3f(x,y,z), 0f,
						random.nextFloat()*360, 0, 0.6f));
			}
//			float x = random.nextFloat()*300-230;
//			float y = 0;
//			float z = random.nextFloat()*-600;
//			treeModels.add(new Entity(treeTexturedModel1, new Vector3f(x,y,z), 0f,
//					0f, 0f, 0.2f));
//			x = random.nextFloat()*800-100;
//			y = 0;
//			z = random.nextFloat()*-300;
//			treeModels.add(new Entity(treeTexturedModel2, new Vector3f(x,y,z), 0f,
//					0f, 0f, 1.4f));
//			x = random.nextFloat()*500-230;
//			y = 0;
//			z = random.nextFloat()*-300;
//			grassModels.add(new Entity(grassTexturedModel1, new Vector3f(x,y,z), 0f,
//					0f, 0f, 0.4f));
//			x = random.nextFloat()*500-230;
//			y = 0;
//			z = random.nextFloat()*-300;
//			bushModels.add(new Entity(bushTexturedModel1, new Vector3f(x,y,z), 0f,
//					0f, 0f, 0.3f));
//			x = random.nextFloat()*600-300;
//			y = 0;
//			z = random.nextFloat()*-400;
//			flowerModels.add(new Entity(flowerTexturedModel1, new Vector3f(x,y,z), 0f,
//					0f, 0f, 0.8f));
		}
		
		Light light = new Light(new Vector3f(20000,40000,20000), new Vector3f(1,1,1));
		
		MasterRenderer renderer = new MasterRenderer();
		
		Vector3f playerPosition = new Vector3f(0,0,0);
		Player player = new Player(mainPlayerTexturedModel1, playerPosition,0,0,0,1);
		player.setScale(0.2f);
		
		Camera camera = new Camera(player);
		
//		Main game loop
		while(!Display.isCloseRequested())
		{
			camera.move();
			player.move(groundTerrain1);
			
			renderer.processEntity(player);
			renderer.processTerrain(groundTerrain1);
//			renderer.processTerrain(groundTerrain2);
			
			for(Entity entity: allEntities) {
				renderer.processEntity(entity);
			}
			
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

			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.recycle();
		modelLoader.formatBin();

		DisplayManager.closeDisplay();

	}

}
