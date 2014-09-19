package com.tgra;

import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.Gdx;

public class Camera
{
	Point3D eye;
	Vector3D u;
	Vector3D v;
	Vector3D n;

	public Camera(Point3D pEye, Point3D pCenter, Vector3D up)
	{
		eye = pEye;
		n = Vector3D.difference(pEye, pCenter);
		n.normalize();
		u = Vector3D.cross(up, n);
		u.normalize();
		v = Vector3D.cross(n, u);
	}
	
	public void setModelViewMatrix()
	{
		Vector3D minusEye = Vector3D.difference(new Point3D(0,0,0), eye);
		
		float[] matrix = new float[16];
		matrix[0] = u.x;	matrix[4] = u.y;	matrix[8] = u.z;	matrix[12] = Vector3D.dot(minusEye, u);
		matrix[1] = v.x;	matrix[5] = v.y;	matrix[9] = v.z;	matrix[13] = Vector3D.dot(minusEye, v);
		matrix[2] = n.x;	matrix[6] = n.y;	matrix[10] = n.z;	matrix[14] = Vector3D.dot(minusEye, n);
		matrix[3] = 0;		matrix[7] = 0;		matrix[11] = 0;		matrix[15] = 1;
		
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadMatrixf(matrix, 0);
	}
}
