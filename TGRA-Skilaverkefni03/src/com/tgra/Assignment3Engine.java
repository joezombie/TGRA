package com.tgra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.utils.BufferUtils;
import com.tgra.Shapes.Arrow;
import com.tgra.Shapes.Box;
import com.tgra.Shapes.Shape;
import com.tgra.Shapes.Wall;

import java.io.IOException;
import java.io.InputStream;
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
    Box slidingBox;
    Box rotatingBox;
    boolean slidingLeft;
    FloatBuffer vertexBuffer2DBox;
    Arrow arrow;
    Mesh monkey;
    Vector3D monkeyR;

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

        // Monkey
        try {
            InputStream in = Gdx.files.internal("assets/monkey.obj").read();
            monkey =  ObjLoader.loadObj(in);
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        monkeyR = new Vector3D(0, 0, 0);

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
        camFirstPerson.perspective(75.0f, ratio, 1.0f, 100.0f);

        camTopDown = new Camera();
        camTopDown.perspective(40.0f, ratio, 5.0f, 20.0f);

        camThirdPerson = new Camera();
        camThirdPerson.perspective(40.0f, ratio, 5.0f, 100.0f);

        // Shapes
        shapes = new ArrayList<Shape>();
        // Walls
        Wall.loadVertices();

        shapes.add(new Wall(new Point3D(-2.5f, 0, 2.5f), 10.0f, false, new ColorRGB(0.0f, 1.0f, 0f)));
        shapes.add(new Wall(new Point3D(2.5f, 0, -2.5f), 10.0f, true, new ColorRGB(0.0f, 1.0f, 0f)));
        shapes.add(new Wall(new Point3D(7.5f, 0, 2.5f), 10.0f, false, new ColorRGB(0.0f, 1.0f, 0f)));
        shapes.add(new Wall(new Point3D(2.5f, 0, 7.5f), 10.0f, true, new ColorRGB(0.0f, 1.0f, 0f)));

        // Boxes
        Box.loadVertices();

        shapes.add(new Box(new Point3D(5.0f, 0.0f, 5.0f), 1.0f, new ColorRGB(1.0f, 0, 0)));

        rotatingBox = new Box(new Point3D(30.0f, 0.0f, 0.0f), 20.0f, new ColorRGB(0.3f, 0.1f, 0.1f));
        shapes.add(rotatingBox);


        slidingBox = new Box(new Point3D(2.0f, 0.0f, 2.0f), 1.0f, new ColorRGB(1.0f, 1.0f, 0));
        slidingLeft = true;
        shapes.add(slidingBox);

        // Other
        arrow = new Arrow();
        arrow.create();
        arrow.setPosition(camFirstPerson.eye);

        vertexBuffer2DBox = BufferUtils.newFloatBuffer(8);
        vertexBuffer2DBox.put(new float[] {0,0, 0,1, 1,0, 1,1});
        vertexBuffer2DBox.rewind();

        setInputProcessor();

    }

    private void setInputProcessor(){
        Gdx.input.setInputProcessor(new Assignment3InputProcessor() {
            @Override
            public boolean keyDown(int i) {
                switch (i) {
                    case Input.Keys.V:
                        isThirdPerson = !isThirdPerson;
                }
                return false;
            }
        });
    }

    private void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        monkeyR.x += 30.0f * deltaTime;
        monkeyR.z += 30.0f * deltaTime;

        Point3D oldEye = new Point3D(camFirstPerson.eye);
        //System.out.println(slidingBox.getPosition());


        if(slidingBox.getPosition().x > 3.0f){
            slidingLeft = true;
        }else if(slidingBox.getPosition().x < -3.0f){
            slidingLeft = false;
        }

        if(slidingLeft){
            slidingBox.getPosition().x -= 1.0f * deltaTime;
            //System.out.println(1.0f * deltaTime);

        } else {
            slidingBox.getPosition().add(new Vector3D(1.0f * deltaTime, 0, 0));
            System.out.println(1.0f * deltaTime);
        }

        slidingBox.getPosition().add(new Vector3D(1.0f * deltaTime,0, 0));
        boolean arrowCollides = false;



        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camFirstPerson.slide(0, 0, -1.0f * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camFirstPerson.slide(0, 0, 1.0f * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camFirstPerson.slide(-1.0f * deltaTime, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camFirstPerson.slide(1.0f * deltaTime, 0, 0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camFirstPerson.yaw(30.0f * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camFirstPerson.yaw(-30.0f * deltaTime);
        }

        arrow.setPosition(camFirstPerson.eye);

        for(Shape s : shapes){
            if(s.collides(arrow)){
                camFirstPerson.eye = oldEye;
            }
        }

        if(isThirdPerson){
            camThirdPerson.lookAt(new Point3D(camFirstPerson.eye.x, camFirstPerson.eye.y + 5.0f, camFirstPerson.eye.z + 7.0f),
                    camFirstPerson.eye, new Vector3D(0, 1, 0));

        }

        //lights.get(0).setLightPosition(new Point3D(camFirstPerson.eye.x - 10, camFirstPerson.eye.y + 10, camFirstPerson.eye.z - 10));
        //lights.get(1).setLightPosition(new Point3D(camFirstPerson.eye.x + 10, camFirstPerson.eye.y + 10, camFirstPerson.eye.z - 10));

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

            for(Shape s : shapes){
                s.draw();
            }

            Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, new float[]{0.545f, 0.271f, 0.075f, 1.0f}, 0);
            Gdx.gl11.glPushMatrix();
            Gdx.gl11.glTranslatef(0, 0, 2);
            Gdx.gl11.glRotatef(monkeyR.x, monkeyR.y, monkeyR.z, 1);
            Gdx.gl11.glScalef(0.5f, 0.5f, 0.5f);
            monkey.render(GL11.GL_TRIANGLES);

            Gdx.gl11.glPopMatrix();

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

    private boolean radiusHit(Shape a, Shape b){
        float distance = (float) Math.sqrt(Math.pow(a.getPosition().x - b.getPosition().x, 2)
                       + Math.pow(a.getPosition().z - b.getPosition().z, 2));

        if(distance <= a.getRadius() + b.getRadius()){
            return true;
        }
        return false;
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
