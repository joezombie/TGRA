package com.tgra;

import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class Camera
{
	public Point3D eye;
	public Vector3D u;
	public Vector3D v;
	public Vector3D n;

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

    public void slide(float delU, float delV, float delN){
        eye.add(Vector3D.scale(u, delU));
        eye.add(Vector3D.scale(v, delV));
        eye.add(Vector3D.scale(n, delN));
    }

    public void roll(float angle){
        float cos = MathUtils.cos(angle * MathUtils.PI / 100.0f);
        float sin = MathUtils.sin(angle * MathUtils.PI / 100.0f);

        Vector3D newU = Vector3D.add(Vector3D.scale(v, cos), Vector3D.scale(v, sin));
        v = Vector3D.add(Vector3D.scale(u, -sin), Vector3D.scale(v, cos));
        u = newU;
    }
}
