package com.tgra;

import com.badlogic.gdx.graphics.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class First3D_Core implements ApplicationListener
{
	Camera cam;
	Sphere sphere;
	Cube cube;
	
	float light_angle;
	
	@Override
	public void create()
	{
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);

		Gdx.gl11.glEnable(GL11.GL_FOG);
		Gdx.gl11.glFogf(GL11.GL_FOG_START, 5);
		Gdx.gl11.glFogf(GL11.GL_FOG_END, 10);
		Gdx.gl11.glFogf(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		float[] fogColor = {0.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glFogfv(GL11.GL_FOG_COLOR, fogColor, 0);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 60, 1.333333f, 0.2f, 15.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		cam = new Camera(new Point3D(3.0f, 3.0f, 3.0f), new Point3D(0.0f, 3.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		sphere = new Sphere(60, 120);
		cube = new Cube();
		
		light_angle = 0.0f;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			cam.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			cam.pitch(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			cam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			cam.slide(0.0f, 0.0f, -10.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			cam.slide(0.0f, 0.0f, 10.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			cam.slide(-10.0f * deltaTime, 0.0f, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			cam.slide(10.0f * deltaTime, 0.0f, 0.0f);
		}
		

		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F))
		{
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
		}
		
		light_angle += Math.PI * deltaTime;
	}

	private void drawFloor()
	{
		for(float fx = 0.0f; fx < 10.0f; fx += 1.0)
		{
			for(float fz = 0.0f; fz < 10.0f; fz += 1.0)
			{
				Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef(fx, 0.0f, fz);
				Gdx.gl11.glScalef(0.95f, 0.95f, 0.95f);
				cube.draw();
				Gdx.gl11.glPopMatrix();
			}
		}
	}
	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
/*
		float[] lightPosition1 = {0, 0, 0, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);

		float[] lightSpotDirection = {0, 0, -1, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPOT_DIRECTION, lightSpotDirection, 0);
*/
		
		cam.setModelViewMatrix();
		

		float[] lightDiffuse = {0.5f, 0.0f, 0.2f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightSpecular = {1.0f, 0.5f, 0.4f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_SPECULAR, lightSpecular, 0);

		float[] lightPosition = {10.0f * (float)Math.cos(light_angle), 10.0f, 10.0f * (float)Math.sin(light_angle), 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		
		
		
		
		

		float[] lightDiffuse1 = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

		float[] lightSpecular1 = {0.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPECULAR, lightSpecular1, 0);

		float[] lightPosition1 = {cam.eye.x, cam.eye.y, cam.eye.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);

		float[] lightSpotDirection = {-cam.n.x, -cam.n.y, -cam.n.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPOT_DIRECTION, lightSpotDirection, 0);

		Gdx.gl11.glLightf(GL11.GL_LIGHT1, GL11.GL_SPOT_EXPONENT, 30.0f);
		Gdx.gl11.glLightf(GL11.GL_LIGHT1, GL11.GL_SPOT_CUTOFF, 30.0f);
		
		
		
		
		
		
		float[] materialDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

		float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, materialSpecular, 0);
		
		Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 90.0f);

		drawFloor();

		Gdx.gl11.glTranslatef(0.0f, 0.0f, 0.0f);
		sphere.draw();
	}

	@Override
	public void render()
	{
		update();
		display();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
