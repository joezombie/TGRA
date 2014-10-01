package com.tgra.Shapes;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class Arrow {
	
	FloatBuffer vertexBuffer;

	public Arrow()
	{
		
	}
	
	public void create()
	{
		vertexBuffer = BufferUtils.newFloatBuffer(126);
		vertexBuffer.put(new float[] {-0.5f, 0.25f, -1.0f, 0.5f, 0.25f, -1.0f, -0.5f, 0.25f, 0.0f, 0.5f, 0.25f, 0.0f,
									  -1.0f, 0.25f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 0.25f, 0.0f,
									  -0.5f, -0.25f, -1.0f, 0.5f, -0.25f, -1.0f, -0.5f, -0.25f, 0.0f, 0.5f, -0.25f, 0.0f,
									  -1.0f, -0.25f, 0.0f, 0.0f, -0.25f, 1.0f, 1.0f, -0.25f, 0.0f,
									  -0.5f, 0.25f, -1.0f, 0.5f, 0.25f, -1.0f, -0.5f, -0.25f, -1.0f, 0.5f, -0.25f, -1.0f,
									  -1.0f, 0.25f, 0.0f, -0.5f, 0.25f, 0.0f, -1.0f, -0.25f, 0.0f, -0.5f, -0.25f, 0.0f,
									  1.0f, 0.25f, 0.0f, 0.5f, 0.25f, 0.0f, 1.0f, -0.25f, 0.0f, 0.5f, -0.25f, 0.0f,
									  -0.5f, 0.25f, -1.0f, -0.5f, 0.25f, 0.0f, -0.5f, -0.25f, -1.0f, -0.5f, -0.25f, 0.0f,
									  0.5f, 0.25f, -1.0f, 0.5f, 0.25f, 0.0f, 0.5f, -0.25f, -1.0f, 0.5f, -0.25f, 0.0f,
									  -1.0f, 0.25f, 0.0f, 0.0f, 0.25f, 1.0f, -1.0f, -0.25f, 0.0f, 0.0f, -0.25f, 1.0f,
									  0.0f, 0.25f, 1.0f, 1.0f, 0.25f, 0.0f, 0.0f, -0.25f, 1.0f, 1.0f, -0.25f, 0.0f});
		vertexBuffer.rewind();
	}
	
	public void display()
	{
		Gdx.gl11.glRotatef(180.0f, 0, 1, 0);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLES, 4, 3);

		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 7, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLES, 11, 3);

		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 14, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 18, 4);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 22, 4);
		

		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 26, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 30, 4);

		float sqrt2 = (float)Math.sqrt(2.0);
		Gdx.gl11.glNormal3f(-sqrt2, 0.0f, sqrt2);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 34, 4);
		Gdx.gl11.glNormal3f(sqrt2, 0.0f, sqrt2);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 38, 4);
	}
}
