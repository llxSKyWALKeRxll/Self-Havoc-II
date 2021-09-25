package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

/**
 * This class is used to apply mathematical transformations onto the transformation matrix
 * @author Vansh Pratap Singh
 *
 */
public class Maths {
	
	/**
	 * Creates a 4x4 transformation matrix object from the passed parameters
	 * @param translation Translation vector 3x3
	 * @param rx Rotation value (x)
	 * @param ry Rotation value (y)
	 * @param rz Rotation value (z)
	 * @param scale Scaling factor
	 * @return
	 */
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry
			, float rz, float scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	
	/**
	 * Creates a 4x4 view matrix object for camera simulation
	 * @param camera Camera object (the view matrix is going to be the inverse of this)
	 * @return viewMatrix object
	 */
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(camera.getPitch()), 
				new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float)Math.toRadians(camera.getYaw()), 
				new Vector3f(0,1,0), viewMatrix, viewMatrix);
		Vector3f cameraPosition = camera.getPosition();
		Vector3f negativeCameraPosition = new Vector3f(-cameraPosition.x, 
				-cameraPosition.y, -cameraPosition.z);
		Matrix4f.translate(negativeCameraPosition, viewMatrix, viewMatrix);
		return viewMatrix;
	}

}
