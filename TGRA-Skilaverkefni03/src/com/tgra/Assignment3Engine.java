package com.tgra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.Shapes.Arrow;
import com.tgra.Shapes.Box;
import com.tgra.Shapes.Shape;
import com.tgra.Shapes.Wall;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 28.9.2014.
 */
public class Assignment3Engine implements ApplicationListener{
    final int[] glLight = new int[] {GL11.GL_LIGHT0, GL11.GL_LIGHT1, GL11.GL_LIGHT2, GL11.GL_LIGHT3, GL11.GL_LIGHT4, GL11.GL_LIGHT5, GL11.GL_LIGHT6, GL11.GL_LIGHT7};
    Integer width = 800;
    Integer height = 600;
    float ratio;
    Boolean isThirdPerson = false;
    Camera camFirstPerson;
    Camera camThirdPerson;
    Camera camTopDown;
    List<Light> lights;
    List<Shape> shapes;
    //FloatBuffer vertexBuffer;
    FloatBuffer vertexBuffer2DBox;
    Arrow arrow;

    @Override
    public void create() {
        // Set ratio
        ratio = width.floatValue() / height.floatValue();
        // OpenGL setup
        Gdx.gl11.glEnable(GL11.GL_LIGHTING);
        Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
        Gdx.gl11.glEnable(GL11.GL_NORMALIZE);
        Gdx.gl11.glClearColor(0.2f, 0.0f, 0.1f, 1.0f);
        Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        // Lights
        lights = new ArrayList<Light>();
        Light light01 = new Light();
        light01.setLightDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
        light01.setLightPosition(new Point3D(5.0f, 10.0f, 15.0f));

        Light light02 = new Light();
        light02.setLightDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
        light02.setLightPosition(new Point3D(2.0f, 1.0f, -5.0f));

        lights.add(light01);
        lights.add(light02);

        for(int i = 0; i < lights.size() && i < glLight.length; i++){
            Gdx.gl11.glEnable(glLight[i]);
        }

        // Cameras
        camFirstPerson = new Camera();
        camFirstPerson.lookAt(new Point3D(0.0f, 0.0f, 5.0f), new Point3D(0.0f, 0.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
        //                       fov, aspect_ratio, nearPlane, farPlane
        camFirstPerson.perspective(75.0f, ratio, 1.0f, 10.0f);

        camTopDown = new Camera();
        camTopDown.perspective(40.0f, ratio, 5.0f, 20.0f);

        camThirdPerson = new Camera();
        camThirdPerson.perspective(40.0f, ratio, 5.0f, 20.0f);

        // Shapes
        shapes = new ArrayList<Shape>();
        // Walls
        Wall.loadVertices();

        shapes.add(new Wall(new Point3D(5.0f, 0.0f, 5.0f), 1.0f, new ColorRGB(1.0f, 1.0f, 0f)));

        // Boxes
        Box.loadVertices();

        shapes.add(new Box(new Point3D(0, 0, 0), 10.0f, new ColorRGB(1.0f, 0, 0)));

        // Other

        arrow = new Arrow();
        arrow.create();


/*        vertexBuffer = BufferUtils.newFloatBuffer(72);
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
        vertexBuffer.rewind();*/

        vertexBuffer2DBox = BufferUtils.newFloatBuffer(8);
        vertexBuffer2DBox.put(new float[] {0,0, 0,1, 1,0, 1,1});
        vertexBuffer2DBox.rewind();

        setInputProcessor();

    }

    private void setInputProcessor(){
        Gdx.input.setInputProcessor(new Assignment3InputProcessor() {
            @Override
            public boolean keyDown(int i) {
                switch (i){
                    case Input.Keys.V:
                        isThirdPerson = !isThirdPerson;
                }
                return false;
            }
        });
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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camFirstPerson.yaw(30.0f * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camFirstPerson.yaw(-30.0f * deltaTime);
        }

        if(isThirdPerson){
            camThirdPerson.lookAt(new Point3D(camFirstPerson.eye.x, camFirstPerson.eye.y + 5.0f, camFirstPerson.eye.z + 7.0f),
                    camFirstPerson.eye, new Vector3D(0, 1, 0));
        }

    }

    private void display(){
        Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

        for(int i = 0; i < 2; i++) {
            if (i == 0) {
                Gdx.gl11.glViewport(0, 0, width, height);

                if(isThirdPerson) {
                    camThirdPerson.setMatrices();
                } else {
                    camFirstPerson.setMatrices();
                }

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

                camTopDown.lookAt(
                        new Point3D(camFirstPerson.eye.x, 12.0f, camFirstPerson.eye.z),
                        camFirstPerson.eye,
                        new Vector3D(0, 0, -1));

                camTopDown.setMatrices();



            }

            for(int li = 0; li < lights.size() && li < glLight.length; li++){
                Gdx.gl11.glLightfv(glLight[li], GL11.GL_DIFFUSE, lights.get(li).lightDiffuse, 0);
                Gdx.gl11.glLightfv(glLight[li], GL11.GL_POSITION, lights.get(li).lightPosition, 0);
            }

            /*
            float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
            Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

            float[] lightPosition = {5.0f, 10.0f, 15.0f, 0.0f};
            Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);
            */
/*            float[] materialDiffuse = {1.0f, 1.0f, 0.0f, 1.0f};
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
            Gdx.gl11.glPopMatrix();*/


            for(Shape s : shapes){
                s.draw();
            }

            if (i == 1 || isThirdPerson) {
                float[] materialDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};

                Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

                Gdx.gl11.glPushMatrix();

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

/*    private void drawBox()
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
    }*/

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
