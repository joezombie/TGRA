package com.tgra;
import java.nio.FloatBuffer;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;


public class Assignment3Base implements ApplicationListener
{
	Camera cam;
	FloatBuffer vertexBuffer;
	
	Arrow arrow;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
									  0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
		vertexBuffer.rewind();
		
		cam = new Camera();
		cam.lookAt(new Point3D(0.0f, 0.0f, 5.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		cam.perspective(90.0f, 1.333333f, 1.0f, 10.0f);
		
		arrow = new Arrow();
		arrow.create();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	boolean movingRight = false;
	boolean movingUp = false;
	
	float arrowRotation = 0.0f;

	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		arrowRotation += 180.0f * deltaTime;

		//cam.pitch(90.0f * deltaTime);
		if(movingRight)
		{
			cam.slide(1.0f * deltaTime, 0, 0);
			if(cam.eye.x >= 2.0f)
			{
				movingRight = false;
			}
		}
		else
		{
			cam.slide(-1.0f * deltaTime, 0, 0);
			if(cam.eye.x <= -2.0f)
			{
				movingRight = true;
			}
		}

		if(movingUp)
		{
			cam.slide(0, 1.0f * deltaTime, 0);
			if(cam.eye.y >= 1.0f)
			{
				movingUp = false;
			}
		}
		else
		{
			cam.slide(0, -1.0f * deltaTime, 0);
			if(cam.eye.y <= -1.0f)
			{
				movingUp = true;
			}
		}
	}
	
	private void drawBox()
	{
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
	}
	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

		//Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		//Gdx.gl11.glLoadIdentity();
		//Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 10.0f);
		
		//Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		//Gdx.gl11.glLoadIdentity();
		//Gdx.glu.gluLookAt(Gdx.gl11, -1.0f, -1.0f, 3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		
		for(int i = 0; i < 2; i++)
		{
			if(i == 0)
			{
				Gdx.gl11.glViewport(0, 0, 640, 480);
			}
			else
			{
				Gdx.gl11.glViewport(150, 50, 320, 240);
				Gdx.gl11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}	
	
			//cam.setProjectionMatrix();
			//cam.setModelViewMatrix();
			cam.setMatrices();
			
	
			float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);
	
			float[] lightPosition = {5.0f, 10.0f, 15.0f, 0.0f};
			Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);
	
			float[] materialDiffuse = {1.0f, 1.0f, 0.0f, 1.0f};
			Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);
	
			drawBox();
	
			materialDiffuse[0] = 0.0f;
			materialDiffuse[1] = 0.0f;
			materialDiffuse[2] = 1.0f;
			materialDiffuse[3] = 1.0f;
	
			Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);
	
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(3.0f, 1.0f, 0.0f);
			drawBox();
			Gdx.gl11.glPopMatrix();

			materialDiffuse[0] = 1.0f;
			materialDiffuse[1] = 1.0f;
			materialDiffuse[2] = 1.0f;
			materialDiffuse[3] = 1.0f;
	
			Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(0.0f, 0.0f, 2.0f);
			Gdx.gl11.glRotatef(arrowRotation, 0.0f, 1.0f, 0.0f);
			arrow.display();
			Gdx.gl11.glPopMatrix();
		}
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
