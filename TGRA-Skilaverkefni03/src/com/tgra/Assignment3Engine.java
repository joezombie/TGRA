package com.tgra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Johannes Gunnar Heidarsson on 28.9.2014.
 */
public class Assignment3Engine implements ApplicationListener{
    int width = 800;
    int height = 600;
    Camera camFirstPerson;
    Camera camTopDown;
    FloatBuffer vertexBuffer;
    FloatBuffer vertexBuffer2DBox;
    Arrow arrow;

    @Override
    public void create() {
        Gdx.gl11.glEnable(GL11.GL_LIGHTING);
        Gdx.gl11.glEnable(GL11.GL_LIGHT0);
        Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);

        Gdx.gl11.glClearColor(0.2f, 0.0f, 0.1f, 1.0f);

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

        camFirstPerson = new Camera();
        camFirstPerson.lookAt(new Point3D(0.0f, 0.0f, 5.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
        camFirstPerson.perspective(90.0f, 1.333333f, 1.0f, 10.0f);

        camTopDown = new Camera();
        camTopDown.perspective(40.0f, 1.333333f, 5.0f, 20.0f);

        arrow = new Arrow();
        arrow.create();


        vertexBuffer2DBox = BufferUtils.newFloatBuffer(8);
        vertexBuffer2DBox.put(new float[] {0,0, 0,1, 1,0, 1,1});
        vertexBuffer2DBox.rewind();

    }

    private void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            camFirstPerson.slide(1.0f * deltaTime, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            camFirstPerson.slide(-1.0f * deltaTime, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            camFirstPerson.slide(0, 0, -1.0f * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            camFirstPerson.slide(0, 0, 1.0f * deltaTime);
        }

    }

    private void display(){
        Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

        //Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
        //Gdx.gl11.glLoadIdentity();
        //Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 10.0f);

        //Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
        //Gdx.gl11.glLoadIdentity();
        //Gdx.glu.gluLookAt(Gdx.gl11, -1.0f, -1.0f, 3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        for(int i = 0; i < 2; i++) {
            if (i == 0) {
                Gdx.gl11.glViewport(0, 0, width, height);
                //cam.setProjectionMatrix();
                //cam.setModelViewMatrix();
                camFirstPerson.setMatrices();
            } else {
                Gdx.gl11.glViewport(0, height - 240, 320, 240);
                Gdx.gl11.glClear(GL11.GL_DEPTH_BUFFER_BIT);


                Gdx.gl11.glDisable(GL11.GL_DEPTH_TEST);
                Gdx.gl11.glDisable(GL11.GL_LIGHTING);


                Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
                Gdx.gl11.glLoadIdentity();
                Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
                Gdx.gl11.glLoadIdentity();
                Gdx.glu.gluOrtho2D(Gdx.gl10, 0, 1, 0, 1);


                Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer2DBox);
                Gdx.gl11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

                Gdx.gl11.glEnable(GL11.GL_LIGHTING);
                Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);

                //camFirstPerson.setMatrices();
                camTopDown.lookAt(new Point3D(camFirstPerson.eye.x, 12.0f, camFirstPerson.eye.z),
                        camFirstPerson.eye, new Vector3D(0, 0, -1));
                camTopDown.setMatrices();

            }


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

            if (i == 1) {
                materialDiffuse[0] = 1.0f;
                materialDiffuse[1] = 1.0f;
                materialDiffuse[2] = 1.0f;
                materialDiffuse[3] = 1.0f;

                Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

                Gdx.gl11.glPushMatrix();
                //Gdx.gl11.glTranslatef(0.0f, 0.0f, 2.0f);
                //Gdx.gl11.glRotatef(arrowRotation, 0.0f, 1.0f, 0.0f);

                float[] matrix = new float[16];
                matrix[0] = camFirstPerson.u.x;
                matrix[4] = camFirstPerson.v.x;
                matrix[8] = camFirstPerson.n.x;
                matrix[12] = camFirstPerson.eye.x;
                matrix[1] = camFirstPerson.u.y;
                matrix[5] = camFirstPerson.v.y;
                matrix[9] = camFirstPerson.n.y;
                matrix[13] = camFirstPerson.eye.y;
                matrix[2] = camFirstPerson.u.z;
                matrix[6] = camFirstPerson.v.z;
                matrix[10] = camFirstPerson.n.z;
                matrix[14] = camFirstPerson.eye.z;
                matrix[3] = 0;
                matrix[7] = 0;
                matrix[11] = 0;
                matrix[15] = 1;

                Gdx.gl11.glMultMatrixf(matrix, 0);

                arrow.display();
                Gdx.gl11.glPopMatrix();
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

    @Override
    public void resume() {

    }

    @Override
    public void render() {
        update();
        display();
    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }
}
