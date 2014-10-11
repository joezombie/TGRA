package com.tgra;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Assignment3Base implements ApplicationListener
{
	Camera camFirstPerson;
	
	FloatBuffer vertexBuffer2DBox;
	
	List<Box> boxes;
	
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);


		
		camFirstPerson = new Camera();
		camFirstPerson.lookAt(new Point3D(0.0f, 0.0f, 5.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		camFirstPerson.perspective(75.0f, 1.333333f, 0.2f, 10.0f);

		vertexBuffer2DBox = BufferUtils.newFloatBuffer(8);
		vertexBuffer2DBox.put(new float[] {0,0, 0,1, 1,0, 1,1});
		vertexBuffer2DBox.rewind();
		
		Box.loadVertices();
		
		boxes = new ArrayList<Box>();
		
		int totalLevels = 5;

		for(int level = 0; level < totalLevels; level++)
		{
			for(int i = 0; i < totalLevels - level; i++)
			{
				for(int j = 0; j < totalLevels - level; j++)
				{
					boxes.add(new Box(new Point3D(level*0.6f + i*1.2f,level*1.2f,level*0.6f + j*1.2f), 1, new Color3(1,1,0)));
				}
			}
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	float pyramidRotate = 0.0f;
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		pyramidRotate += 180.0f * deltaTime;
		
		//camFirstPerson.yaw(45.0f * deltaTime);
		//camFirstPerson.pitch(78.0f * deltaTime);
		//camFirstPerson.roll(78.0f * deltaTime);

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			camFirstPerson.roll(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			camFirstPerson.roll(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			camFirstPerson.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			camFirstPerson.pitch(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.I))
		{
			camFirstPerson.slide(0,0, -5.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.K))
		{
			camFirstPerson.slide(0,0, 5.0f * deltaTime);
		}
	}
	

	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

		Gdx.gl11.glViewport(0, 0, 640, 480);
		//cam.setProjectionMatrix();
		//cam.setModelViewMatrix();
		camFirstPerson.setMatrices();
	

		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightPosition = {5.0f, 10.0f, 15.0f, 0.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);


		Gdx.gl11.glPushMatrix();
		
		Gdx.gl11.glTranslatef(5.0f * MathUtils.cos(0.2f * pyramidRotate * (float)Math.PI / 180.0f), 0.0f, 0.0f);

		Gdx.gl11.glRotatef(pyramidRotate, 0, 1, 0);
		Gdx.gl11.glTranslatef(-2.4f, 0.0f, -2.4f);
		for(Box b:boxes)
		{
			b.draw();
		}
		Gdx.gl11.glPopMatrix();
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
