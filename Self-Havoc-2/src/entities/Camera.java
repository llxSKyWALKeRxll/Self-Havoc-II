package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Class that handles the camera or the simulation of the camera in the game
 * @author Vansh Pratap Singh
 *
 */
public class Camera {
	
	private Vector3f position = new Vector3f(0,2f,0);
	private float pitch, yaw, roll;
	
	public Camera()
	{
		
	}
	
	/**
	 * Update the movement of the camera
	 */
	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= 0.15f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += 0.15f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= 0.15f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += 0.15f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			position.y += 0.15f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			position.y -= 0.15f;
		}
	}

//	Getter and setter methods
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	

}
