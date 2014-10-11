package com.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class Cube {

	FloatBuffer vertexBuffer;
	public Cube()
	{
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
	}
	

	public void draw()
	{
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
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
}
