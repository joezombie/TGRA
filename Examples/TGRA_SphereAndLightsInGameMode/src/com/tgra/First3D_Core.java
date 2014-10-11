package com.tgra;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class First3D_Core implements ApplicationListener, InputProcessor {
	private Camera cam = null;
	private Sphere sphere = null;
	private float rotate_angle = 0;
	private boolean light_0 = true;
	private boolean light_1 = false;

	@Override
	public void create() {
		// This class implements input processor that we use
		// within this application listener.
		Gdx.input.setInputProcessor(this);

		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_LIGHT2);
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);

		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);

		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 0.2f, 30.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		cam = new Camera(new Point3D(3.0f, 3.0f, 3.0f), new Point3D(0.0f, 3.0f,
				0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		sphere = new Sphere(10, 30);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	private void update() {

		if (this.light_0)
			Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		else
			Gdx.gl11.glDisable(GL11.GL_LIGHT0);

		if (this.light_1)
			Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		else
			Gdx.gl11.glDisable(GL11.GL_LIGHT1);

		float deltaTime = Gdx.graphics.getDeltaTime();
		this.rotate_angle += 10 * deltaTime;

		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			cam.pitch(-90.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			cam.pitch(90.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			cam.yaw(-90.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			cam.yaw(90.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.W))
			cam.slide(0.0f, 0.0f, -10.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.S))
			cam.slide(0.0f, 0.0f, 10.0f * deltaTime);

		if (Gdx.input.isKeyPressed(Input.Keys.A))
			cam.slide(-10.0f * deltaTime, 0.0f, 0.0f);

		if (Gdx.input.isKeyPressed(Input.Keys.D))
			cam.slide(10.0f * deltaTime, 0.0f, 0.0f);

		if (Gdx.input.isKeyPressed(Input.Keys.R))
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);

		if (Gdx.input.isKeyPressed(Input.Keys.F))
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
	}

	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		cam.setModelViewMatrix();

		// Configure light 0
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, new float[] { 0.4f,
				0.5f, 0.3f, 1.0f }, 0);
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, new float[] {
				30.0f, 40.0f, 15.0f, 1.0f }, 0);

		// Configure light 1
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_SPECULAR, new float[] { 1f,
				1f, 1f, 1.0f }, 0);
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, new float[] {
				20.0f, 40.0f, -40.0f, 1.0f }, 0);

		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_AMBIENT, new float[] { 0.2f,
				0.2f, 0.2f, 1.0f }, 0);

		// Set diffuse material.
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, new float[] {
				1.0f, 1.0f, 1.0f, 1.0f }, 0);
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, new float[] {
				0.2f, .2f, .2f, 1.0f }, 0);
		Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 100);

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glRotatef(rotate_angle, 1, 1, 1);
		this.sphere.toggleDrawLines();
		sphere.draw();
		Gdx.gl11.glPopMatrix();

		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(3, 3.0f, 0.0f);

		Gdx.gl11.glRotatef(rotate_angle, 1, 1, 1);
		this.sphere.toggleDrawLines();
		sphere.draw();
		Gdx.gl11.glPopMatrix();
	}

	@Override
	public void render() {
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

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		if (Input.Keys.L == arg0) {
			this.sphere.toggleDrawLines();
		}

		if (Input.Keys.NUM_0 == arg0) {
			this.light_0 = this.light_0 ? false : true;
		}

		if (Input.Keys.NUM_1 == arg0) {
			this.light_1 = this.light_1 ? false : true;
		}
		
		if (Input.Keys.Q == arg0) {
			Gdx.app.exit();
		}

		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
