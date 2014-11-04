package com.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Lazers {

	private Point3D position;
	private Vector3D u;
	private Vector3D v;
	private Vector3D n;
	
	private final float speed = 10.0f;

	FloatBuffer vertexBuffer;
	FloatBuffer texCoordBuffer;
	Texture tex;
	
	public Lazers(Point3D pos, Vector3D u, Vector3D v, Vector3D n)
	{
		position = pos;
		this.u = u;
		this.v = v;
		this.n = n;
		

		vertexBuffer = BufferUtils.newFloatBuffer(12);
		vertexBuffer.put(new float[] {-0.5f, 0, 0.5f, -0.5f, 0, -0.5f,
									  0.5f, 0, 0.5f, 0.5f, 0, -0.5f});
		vertexBuffer.rewind();
		
		texCoordBuffer = BufferUtils.newFloatBuffer(8);
		texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
		texCoordBuffer.rewind();
		
		tex = new Texture(Gdx.files.internal("textures/star03.bmp"));
		
	}
	
	public void update(float deltaTime)
	{
		position.add(Vector3D.mult(-speed * deltaTime, n));
	}
	
	public void setLights()
	{
		Gdx.gl11.glEnable(GL11.GL_LIGHT2);

		float[] lightDiffuse = {1.0f, 0.2f, 0.4f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightAmbient = {0.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_AMBIENT, lightAmbient, 0);

		float[] lightPosition = {position.x, position.y, position.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPosition, 0);
		
		Gdx.gl11.glLightf(GL11.GL_LIGHT2, GL11.GL_LINEAR_ATTENUATION, 0.5f);
	}
	
	public void draw()
	{
		float[] matrix = new float[16];
		matrix[0] = u.x;	matrix[4] = v.x;	matrix[8] = n.x;	matrix[12] = position.x;
		matrix[1] = u.y;	matrix[5] = v.y;	matrix[9] = n.y;	matrix[13] = position.y;
		matrix[2] = u.z;	matrix[6] = v.z;	matrix[10] = n.z;	matrix[14] = position.z;
		matrix[3] = 0;		matrix[7] = 0;		matrix[11] = 0;		matrix[15] = 1;
		
		
		


		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		tex.bind();  //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

		Gdx.gl11.glEnable(GL11.GL_BLEND);
		Gdx.gl11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		//Gdx.gl11.glDisable(GL11.GL_DEPTH_TEST);

		float[] materialEm = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, materialEm, 0);
		float[] materialDiffuse = {0.0f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, materialDiffuse, 0);

		Gdx.gl11.glPushMatrix();
		
		Gdx.gl11.glMultMatrixf(matrix, 0);
		

		Gdx.gl11.glTranslatef(0.0f, -0.8f, 0);
		Gdx.gl11.glScalef(0.5f, 1.0f, 2.0f);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);

		Gdx.gl11.glTranslatef(1.0f, 0, 0);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glTranslatef(-2.0f, 0, 0);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

		Gdx.gl11.glPopMatrix();

		

		materialEm[0] = 0.0f;
		materialEm[1] = 0.0f;
		materialEm[2] = 0.0f;
		materialEm[3] = 1.0f;
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, materialEm, 0);


		//Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);

		Gdx.gl11.glDisable(GL11.GL_BLEND);

		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
}
