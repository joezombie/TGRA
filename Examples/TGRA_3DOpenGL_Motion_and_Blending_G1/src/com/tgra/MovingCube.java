package com.tgra;

import com.badlogic.gdx.Gdx;

public class MovingCube
{
	Cube cube;
	
	Point3D position;
	float startTime, endTime;
	Point3D P1, P2, P3, P4;
	Point3D tmpPos;
	
	float rotationAngle;
	
	public MovingCube()
	{
		cube = new Cube("first_texture.jpg");
		
		startTime = 2.0f;
		endTime = 5.0f;
		P1 = new Point3D(-1.0f, -1.0f, -1.0f);
		P2 = new Point3D(-1.5f, 1.0f, -0.5f);
		P3 = new Point3D(-1.0f, 1.3f, 0.3f);
		P4 = new Point3D(1.0f, 1.0f, 1.0f);
		
		position = new Point3D(0, 0, 0);
	}
	
	public void update(float elapsedTime)
	{
		rotationAngle = (elapsedTime * 180.0f);
		if(elapsedTime < startTime)
		{
			position.x = P1.x;
			position.y = P1.y;
			position.z = P1.z;
		}
		else if(elapsedTime > endTime)
		{
			position.x = P4.x;
			position.y = P4.y;
			position.z = P4.z;
		}
		else
		{
			float t = (elapsedTime - startTime) / (endTime - startTime);
			System.out.println("position = " + t);

			position.x = (1.0f - t)*(1.0f - t)*(1.0f - t)*P1.x
					   + 3*(1.0f - t)*(1.0f - t)*t*P2.x
					   + 3*(1.0f - t)*t*t*P3.x
					   + t*t*t*P4.x;

			position.y = (1.0f - t)*(1.0f - t)*(1.0f - t)*P1.y
					   + 3*(1.0f - t)*(1.0f - t)*t*P2.y
					   + 3*(1.0f - t)*t*t*P3.y
					   + t*t*t*P4.y;

			position.z = (1.0f - t)*(1.0f - t)*(1.0f - t)*P1.z
					   + 3*(1.0f - t)*(1.0f - t)*t*P2.z
					   + 3*(1.0f - t)*t*t*P3.z
					   + t*t*t*P4.z;
			//position.add(Vector3D.mult(t, Vector3D.difference(endPos, startPos)));

		}
	}
	
	public void display()
	{
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(position.x, position.y, position.z);
		Gdx.gl11.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
		cube.draw();
		Gdx.gl11.glPopMatrix();

	}
}
