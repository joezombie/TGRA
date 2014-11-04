package is.ru.tgra.cube.engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import is.ru.tgra.cube.helpers.*;
import is.ru.tgra.cube.shapes.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Gunnar Heidarsson on 9.10.2014.
 */
public class CubeEngine implements ApplicationListener {
    final int[] glLight = new int[] {GL11.GL_LIGHT0, GL11.GL_LIGHT1, GL11.GL_LIGHT2, GL11.GL_LIGHT3, GL11.GL_LIGHT4, GL11.GL_LIGHT5, GL11.GL_LIGHT6, GL11.GL_LIGHT7};
    SpriteBatch fontBatch;
    BitmapFont font;
    Integer width = 1024;
    Integer height = 768;
    float ratio;
    Camera mainCamera;
    List<Light> lights;
    Light light1;
    Light sphereLight;
    List<Shape> shapes;
    List<GroundCube> groundCubes;
    List<Shape> enemies;
    SkyBox skyBox;
    CubeLogger log = CubeLogger.getInstance();
    Guy guy;
    Sphere sphere;
    boolean controlCamera = false;
    int fps;
    Point3D checkPoint;
    float runTime = 0;
    Vector3D cameraOffset;

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
        Gdx.gl11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        // Font
        this.fontBatch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Cameras
        mainCamera = new Camera();
        cameraOffset = new Vector3D(0, 3.0f, 10.0f);
        mainCamera.lookAt(new Point3D(0.0f, 3.0f, 11.0f), new Point3D(0.0f, 2.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
        //                       fov, aspect_ratio, nearPlane, farPlane
        mainCamera.perspective(90.0f, ratio, 10.0f, 100.0f);

        // Shapes
        shapes = new ArrayList<Shape>();

        // -Skybox
        SkyBox.loadVertices();
        skyBox = new SkyBox(new Point3D(0,0,0), 110);

        // -Guy
        Guy.loadVertices();
        checkPoint = new Point3D(0.0f, 0.5f, 0.0f);
        guy = new Guy(new Point3D(checkPoint), 0.7f, new ColorRGB(0, 1, 0));
        guy.setDiffuse(new ColorRGB(1.0f, 1.0f, 1.0f));
        guy.setSpecular(new ColorRGB(0, 0, 0));
        guy.setEmission(new ColorRGB(0, 0, 0));
        guy.setShininess(50);
        guy.setSpeed(5.0f);

        // -GroundCubes
        groundCubes = new ArrayList<GroundCube>();
        GroundCube.loadVertices();
        GroundCube.setSize(2f);
        GroundCube.setDiffuse(new ColorRGB(1.0f, 1.0f, 1.0f));
        GroundCube.setEmission(new ColorRGB());
        GroundCube.setSpecular(new ColorRGB());
        GroundCube.setShininess(0);

        groundCubes.add(new GroundCube(new Point3D(0.0f, -2.5f, 0.0f)));
        groundCubes.add(new GroundCube(new Point3D(3.0f, -2.5f, 0.0f), new Point3D(7.0f, -2.5f, 0.0f), new Vector3D(3.0f, 0.0f, 0.0f)));
        groundCubes.add(new GroundCube(new Point3D(10.0f, -1.0f, 0.0f)));
        groundCubes.add(new GroundCube(new Point3D(10.0f, -1.0f, -5.0f)));
        groundCubes.add(new GroundCube(new Point3D(15.0f, 0, -5.0f)));

        // Enemies
        this.enemies = new ArrayList<Shape>();
        // -Sphere
        sphere = new Sphere(64, 128);
        sphere.setPosition(new Point3D(6.0f, 0.5f, 0.0f));
        sphere.setMovement(new Point3D(5.0f, 2.0f, -2.0f), new Point3D(2.0f, -2.0f, -2.0f), new Point3D(8.0f, -2.0f, 2.0f), new Point3D(9.0f, 2.0f, 2.0f));
        sphere.setDiffuse(new ColorRGB(1f, 1f, 1f));
        sphere.setSpecular(new ColorRGB(1f, 0f, 0f));
        sphere.setEmission(new ColorRGB(0.5f, 0f, 0f));
        sphere.setShininess(90.0f);
        sphere.setSize(0.4f);
        shapes.add(sphere);
        enemies.add(sphere);

        Sphere sphere1 = new Sphere(64, 128);
        sphere1.setPosition(new Point3D(4.0f, 0.2f, 0.0f));
        sphere1.setDiffuse(new ColorRGB(0.2f, 0.2f, 0.5f));
        sphere1.setSpecular(new ColorRGB());
        sphere1.setEmission(new ColorRGB());
        sphere1.setShininess(0);
        sphere1.setSize(0.5f);
        shapes.add(sphere1);
        enemies.add(sphere1);


        // Lights
        lights = new ArrayList<Light>();

        light1 = new Light(GL11.GL_LIGHT0);
        light1.setLightPosition(new Point3D(0.0f, 3.0f, 11.0f));
        light1.setLightDiffuse(new ColorRGB(0.5f, 0.5f, 0.5f));
        light1.setLightSpecular(new ColorRGB(0.3f, 0.3f, 0.3f));
        lights.add(light1);

        sphereLight = new Light(GL11.GL_LIGHT1);
        sphereLight.setLightPosition(sphere.getPosition());
        sphereLight.setLightDiffuse(new ColorRGB(1.0f, 0, 0));
        sphereLight.setLightSpecular(new ColorRGB(1.0f, 0, 0));
        lights.add(sphereLight);

        for(int i = 0; i < lights.size() && i < glLight.length; i++){
            Gdx.gl11.glEnable(glLight[i]);
            log.debug("Enabling light: " + i);
        }

        // InputProcessor
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
                        break;
                    case Input.Keys.F1:
                        log.debug("F1 pressed");
                        cameraOffset = new Vector3D(0, 3.0f, 11.0f);
                        guy.setDirection(new Vector3D(1.0f, 0, 0));
                        break;
                    case Input.Keys.F2:
                        log.debug("F2 pressed");
                        cameraOffset = new Vector3D(11.0f, 3.0f, 0);
                        guy.setDirection(new Vector3D(0, 0, -1.0f));
                        break;
                    case Input.Keys.F3:
                        log.debug("F3 pressed");
                        cameraOffset = new Vector3D(0, 3.0f, -11.0f);
                        guy.setDirection(new Vector3D(-1.0f, 0, 0));
                        break;
                    case Input.Keys.F4:
                        log.debug("F4 pressed");
                        cameraOffset = new Vector3D(-11.0f, 3.0f, 0);
                        guy.setDirection(new Vector3D(0, 0, 1.0f));
                        break;
                }
                return false;
            }
        });
    }



    private void update(){
        fps = Gdx.graphics.getFramesPerSecond();
        float deltaTime = Gdx.graphics.getDeltaTime();
        runTime += deltaTime;
        //log.debug("Runtime=" + runTime);

        if(controlCamera) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                mainCamera.slide(0, 0, -3.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                mainCamera.slide(0, 0, 2.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                mainCamera.slide(-3.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                mainCamera.slide(3.0f * deltaTime, 0, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mainCamera.yaw(30.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mainCamera.yaw(-30.0f * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                mainCamera.slide(0, 3.0f * deltaTime, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                mainCamera.slide(0, -3.0f * deltaTime, 0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                log.debug("mainCamera eye: " + mainCamera.eye);
            }
        } else {
            if(!guy.isDying()) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    guy.moveLeft(deltaTime);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    guy.moveRight(deltaTime);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    if (!(guy.isJumping() || guy.isFalling())) {
                        guy.setJumping(true);
                        guy.startJump(runTime);
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                }
            }
        }

        if(!guy.isDying()) {
            for (Shape s : enemies) {
                if (s.collides(guy)) {
                    guy.setFalling(false);
                    guy.startDying(runTime);

                }
            }
        }

        // Reset guy if he falls too far
        if(guy.getPosition().y < -20){
            if(!guy.isDying()) {
                guy.setFalling(false);
                guy.startDying(runTime);
            }
        }

        // Check if guy is falling
        log.debug("Dying: " + guy.isDying() + " Falling: " + guy.isFalling());
        if(!guy.isDying()) {
            guy.startFall(runTime);
            for (GroundCube gc : groundCubes) {
                //log.debug("Guy=" + guy.getPosition() + " shape=" + s.getPosition());
            /*if((guy.getPosition().y - 0.5) - (s.getPosition().y + 2.5) < 0.01f){
                if(guy.getPosition().x - 0.5 > s.getPosition().x - 2.5 && guy.getPosition().x + 1 < s.getPosition().x + 2.5){
                    guy.setFalling(false);
                }
            }*/
                if (gc.collides(guy)) {
                    if (gc.collidesTop(guy)) {
                        guy.stopFall();
                        guy.setPosition(new Point3D(guy.getPosition().x, gc.getPosition().y + GroundCube.getRadius() + guy.getRadius(), guy.getPosition().z));
                        if(gc.isMoving()){
                            guy.getPosition().add(Vector3D.scale(gc.getMovement(), deltaTime));
                        }
                    } else {
                        guy.moveBack();
                    }
                }
            }
        }

        // Move sphere
        if(sphere.isMoving()){
            sphere.updateMovement(runTime);
        } else {
            sphere.startMovement(runTime, 1.0f);
        }

        // Update moving groundCubes
        for(GroundCube gc : groundCubes){
            if(gc.isMoving()){
                gc.updateMovement(deltaTime);
            }
        }

        // Move guy up or down if jumping or falling
        if(guy.isJumping()){
            log.debug("Jumping Y=" + guy.getPosition().y);
            guy.updateJump(runTime);
        }else if(guy.isFalling()){
            //guy.getPosition().add(new Vector3D(0, -15.0f * deltaTime, 0));
            log.debug("Falling start:" + guy.getFallStartTime() + " End:" + guy.getFallAccelerationEndTime());
            guy.updateFall(runTime, deltaTime);
        }

        if(guy.isDying()){
            if(guy.isDyingDone()){
                guy.stopDying();
                reset();
            } else {
                guy.updateDying(runTime);
            }
        }

        // Move camera with guy
        if(!controlCamera) {
            mainCamera.lookAt(Point3D.Add(guy.position, cameraOffset), guy.position, new Vector3D(0, 1, 0));
        }
        // Move light with camera
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

        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, skyBox.getDiffuse().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, skyBox.getSpecular().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_EMISSION, skyBox.getEmission().getArray(), 0);
        Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, skyBox.getShininess());
        skyBox.draw();

        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, guy.getDiffuse().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, guy.getSpecular().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_EMISSION, guy.getEmission().getArray(), 0);
        Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, guy.getShininess());
        guy.draw();

        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, GroundCube.getDiffuse().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, GroundCube.getSpecular().getArray(), 0);
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_EMISSION, GroundCube.getEmission().getArray(), 0);
        Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, GroundCube.getShininess());
        for(GroundCube gc : groundCubes){
            gc.draw();
        }

        for(Shape s : shapes){
            Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, s.getDiffuse().getArray(), 0);
            Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, s.getSpecular().getArray(), 0);
            Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_EMISSION, s.getEmission().getArray(), 0);
            Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, s.getShininess());
            s.draw();
        }

        Gdx.gl11.glDisable(GL11.GL_LIGHTING);
        fontBatch.begin();
        font.setColor(Color.WHITE);
        if(controlCamera){
            font.draw(fontBatch, "Camera", 20, height - 20);
        } else {
            font.draw(fontBatch, "Guy", 20, height - 20);
        }
        font.draw(fontBatch, "FPS: " + fps, 20, height - 40);
        fontBatch.end();
        Gdx.gl11.glEnable(GL11.GL_LIGHTING);
    }

    public void reset(){
        guy.stopFall();
        guy.startFall(runTime);
        guy.setPosition(Point3D.Add(checkPoint, new Vector3D(0, 2.0f, 0)));
        mainCamera.lookAt(Point3D.Add(checkPoint, new Vector3D(0, 3.0f, 11.0f)), checkPoint, new Vector3D(0, 1, 0));
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
