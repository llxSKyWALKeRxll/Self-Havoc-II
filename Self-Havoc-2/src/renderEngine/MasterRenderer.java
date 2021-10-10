package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import shaders.StaticShader;

/**
 * Optimization class: Ensure that the same data is not loaded, binded & removed
 * n times to load different models
 * Similar to dynamic programming?
 * @author Vansh Pratap Singh
 *
 */
public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private ModelRenderer renderer = new ModelRenderer(shader);
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	
	/**
	 * Render all the entities in the scene
	 * @param light Light source
	 * @param camera Camera view
	 */
	public void render(Light light, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		entities.clear();
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> using = entities.get(entityModel);
		if(using != null) {
			using.add(entity);
		}
		else {
			List<Entity> newUsing = new ArrayList<Entity>();
			newUsing.add(entity);
			entities.put(entityModel, newUsing);
		}
	}
	
	/**
	 * Recycle the shader
	 */
	public void recycle() {
		shader.recycle();
	}

}
