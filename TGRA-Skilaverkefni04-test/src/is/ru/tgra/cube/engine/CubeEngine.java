package is.ru.tgra.cube.engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import is.ru.tgra.cube.helpers.*;
import is.ru.tgra.cube.shapes.Box;
import is.ru.tgra.cube.shapes.Guy;
import is.ru.tgra.cube.shapes.Shape;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 9.10.2014.
 */
public class CubeEngine implements ApplicationListener {
    final int[] glLight = new int[] {GL11.GL_LIGHT0, GL11.GL_LIGHT1, GL11.GL_LIGHT2, GL11.GL_LIGHT3, GL11.GL_LIGHT4, GL11.GL_LIGHT5, GL11.GL_LIGHT6, GL11.GL_LIGHT7};
    private SpriteBatch fontBatch;
    private BitmapFont font;
    Integer width = 1024;
    Integer height = 768;
    float ratio;
    Camera mainCamera;
    List<Light> lights;
    Light light1;
    List<Shape> shapes;
    CubeLogger log = CubeLogger.getInstance();
    Guy guy;
    boolean controlCamera = false;
    int fps;

    @Override
    public void create() {
        log.setDebug(true);
        log.debug("Create");


        // Set ratio
        resize(width, height);

        // OpenGL setup
        Gdx.gl11.glEnable(GL11.GL_LIGHTING);
        Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
        Gdx.gl11.glEnable(GL11.GL_NORMALIZE);
        Gdx.gl11.glClearColor(0.2f, 0.0f, 0.1f, 1.0f);
        Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        // Font
        this.fontBatch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Lights
        lights = new ArrayList<Light>();

        light1 = new Light(GL11.GL_LIGHT0);
        light1.setLightPosition(new Point3D(0.0f, 3.0f, 11.0f));
        light1.setLightDiffuse(new ColorRGB(1.0f, 1.0f, 1.0f));
        light1.setLightSpecular(new ColorRGB(0.0f, 0.0f, 0.0f));
        lights.add(light1);

        for(int i = 0; i < lights.size() && i < glLight.length; i++){
            Gdx.gl11.glEnable(glLight[i]);
            log.debug("Enabling light: " + i);
        }

        // Cameras
        mainCamera = new Camera();
        mainCamera.lookAt(new Point3D(0.0f, 3.0f, 11.0f), new Point3D(0.0f, 2.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
        //                       fov, aspect_ratio, nearPlane, farPlane
        mainCamera.perspective(90.0f, ratio, 10.0f, 100.0f);

        // Shapes
        Guy.loadVertices();
        guy = new Guy(new Point3D(0.0f, 0.5f, 0.0f), 1.0f, new ColorRGB(0, 1, 0));

        shapes = new ArrayList<Shape>();

        // Boxes
        Box.loadVertices();

        shapes.add(new Box(new Point3D(0.0f, -2.5f, 0.0f), 5.0f, new ColorRGB(1.0f, 0, 0)));
        shapes.add(new Box(new Point3D(6.0f, -2.0f, 0.0f), 5.0f, new ColorRGB(1.0f, 0, 0)));
        shapes.add(new Box(new Point3D(12.0f, -1.0f, 0.0f), 5.0f, new ColorRGB(0.5f, 0.2f, 0)));

        setInputProcessor();

    }

    private void setInputProcessor(){
        Gdx.input.setInputProcessor(new InputProcessorAbstract() {
            @Override
            public boolean keyDown(int i) {
                log.debug("keyDown: " + i);
                switch (i){
                    case Input.Keys.C:
                        controlCamera = !controlCamera;
                }
                return false;
            }
        });
    }



    private void update(){
        fps = Gdx.graphics.getFramesPerSecond();
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(controlCamera) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                mainCamera.slide(0, 0, -1.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                mainCamera.slide(0, 0, 1.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                mainCamera.slide(-1.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                mainCamera.slide(1.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mainCamera.yaw(30.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mainCamera.yaw(-30.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                mainCamera.slide(0, 1.0f * deltaTime, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                mainCamera.slide(0, -1.0f * deltaTime, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                log.debug("mainCamera eye: " + mainCamera.eye);
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                guy.getPosition().add(new Vector3D(-5.0f * deltaTime, 0, 0));
                mainCamera.slide(-5.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                guy.getPosition().add(new Vector3D(5.0f * deltaTime, 0, 0));
                mainCamera.slide(5.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if( !(guy.isJumping() || guy.isFalling()) ){
                    guy.setJumping(true);
                    guy.setJumpHeight(3);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {

            }
        }

        if(guy.getPosition().y < -6){
            reset();
        }

        guy.setFalling(true);
        for(Shape s : shapes){
            log.debug("Guy=" + guy.getPosition() + " shape=" + s.getPosition());
            if((guy.getPosition().y - 0.5) - (s.getPosition().y + 2.5) < 0.01f){
                if(guy.getPosition().x - 0.5 > s.getPosition().x - 2.5 && guy.getPosition().x + 1 < s.getPosition().x + 2.5){
                    guy.setFalling(false);
                }
            }
        }

        if(guy.isJumping()){
            log.debug("Jumping Y=" + guy.getPosition().y);
            if(guy.getPosition().y > guy.getJumpEndY()){
                guy.setJumping(false);
            } else {
                guy.getPosition().add(new Vector3D(0, 3.0f * deltaTime, 0));
            }
        }else if(guy.isFalling()){
            guy.getPosition().add(new Vector3D(0, -3.0f * deltaTime, 0));
            log.debug("Falling");
        }

        light1.setLightPosition(new Point3D(mainCamera.eye.x, mainCamera.eye.y, mainCamera.eye.z));

    }

    private void display(){
        Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        Gdx.gl11.glViewport(0, 0, width, height);

        mainCamera.setMatrices();

        // Lights

        //Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, new float[] { 0.2f,0.2f, 0.2f, 1.0f }, 0);
        Gdx.gl11.glLightModelfv(GL11.GL_AMBIENT, new float[]{0.2f, 0.2f, 0.2f, 1.0f}, 0);



        for(Light l : lights){
            Gdx.gl11.glLightfv(l.ID, GL11.GL_DIFFUSE, l.getLightDiffuse(), 0);
            Gdx.gl11.glLightfv(l.ID, GL11.GL_SPECULAR, l.getLightSpecular(), 0);
            Gdx.gl11.glLightfv(l.ID, GL11.GL_POSITION, l.getLightPosition(), 0);
        }
        /*float[] lightSpotDirection = {-mainCamera.n.x, -mainCamera.n.y, -mainCamera.n.z, 1.0f};
        Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, lightSpotDirection, 0);
        Gdx.gl11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_EXPONENT, 10.0f);
        Gdx.gl11.glLightf(GL11.GL_LIGHT0, GL11.GL_SPOT_CUTOFF, 20.0f);
        */
        //



        float[] materialDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

        float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, materialSpecular, 0);

        Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 90.0f);

        guy.draw();

        for(Shape s : shapes){
            s.draw();

        }

        fontBatch.begin();
        font.setColor(Color.WHITE);
        if(controlCamera){
            font.draw(fontBatch, "Camera", 20, height - 20);
        } else {
            font.draw(fontBatch, "Guy", 20, height - 20);
        }
        font.draw(fontBatch, "FPS: " + fps, 20, height - 40);
        fontBatch.end();
    }

    public void reset(){
        guy.setPosition(new Point3D(0, 4, 0));
    }

    @Override
    public void render() {
        update();
        display();
    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        log.debug("resize");
        this.width = width;
        this.height = height;
        this.ratio = this.width.floatValue() / this.height.floatValue();
        log.debug("width=" + width + " height=" + height + " ratio=" + ratio);
        if(mainCamera != null) {
            mainCamera.perspective(90.0f, ratio, 1.0f, 100.0f);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }
}
