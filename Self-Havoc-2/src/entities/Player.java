package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

/**
 * Represents the main player in the third person mode
 * @author Vansh Pratap Singh
 *
 */
public class Player extends Entity {
	
	private static final float turnSpeed = 160;
	private static float runSpeed = 20;
	private static final float gravity = -60, jumpMagnitude = 40;
	private static final float terrainHeight = 0;
	private float currentSpeed = 0, currentTurnSpeed = 0;
	private float upSpeed;
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, 
			float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move() {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upSpeed += gravity * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < terrainHeight) {
			upSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump() {
		if(!isInAir) {
			this.upSpeed = jumpMagnitude;
			isInAir = true;
		}
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			runSpeed = 40;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			runSpeed = 20;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.currentSpeed = runSpeed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.currentSpeed = -runSpeed;
		}
		else {
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.currentTurnSpeed = turnSpeed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.currentTurnSpeed = -turnSpeed;
		}
		else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
