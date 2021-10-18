package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Class that handles the camera or the simulation of the camera in the game
 * @author Vansh Pratap Singh
 *
 */
public class Camera {
	
	private Vector3f position = new Vector3f(0,1,0); //0, 2f, 0
	//pitch #20
	private float pitch = 20, yaw = 0, roll;
	private Player player;
	private float distanceFromPlayer = 30;
	private float angleAroundPlayer = 0; //0
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Update the movement of the camera
	 */
	public void move()
	{
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
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
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - xOffset;
		position.z = player.getPosition().z - zOffset;
		position.y = player.getPosition().y + verticalDistance;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		if(distanceFromPlayer >= 10) {
			float zoomMagnitude = Mouse.getDWheel() * 0.03f;
			distanceFromPlayer -= zoomMagnitude;
		}
		if(distanceFromPlayer < 10) {
			distanceFromPlayer = 10;
		}
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			if(pitch >= 5) {
				float pitchChange = Mouse.getDY() * 0.1f;
				pitch -= pitchChange;
			}
			if(pitch < 5) {
				pitch = 5;
			}
			if(pitch > 175) {
				pitch = 175;
			}
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

}
