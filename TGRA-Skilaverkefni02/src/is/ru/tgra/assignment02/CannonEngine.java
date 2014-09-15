package is.ru.tgra.assignment02;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Vector;

public class CannonEngine implements ApplicationListener{
    int width = 800;
    int height = 600;

    float x = 200;
    float y = 200;
    float angle = 0;

    ObjectReference littleBox;
    ObjectReference bigBox;
    ObjectReference circle;
    ObjectReference cannon;

    @Override
    public void create() {
        Vector<Point2D> vertexList = new Vector<Point2D>();

        littleBox = createBox(50, 50, vertexList);
        bigBox = createBox(100, 100, vertexList);
        circle = createCircle(100, 100, vertexList);
        cannon = createBox(30, 100, vertexList);

        int floatBufferSize = vertexList.size() * 2;
        FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(floatBufferSize);

        float[] array = new float[floatBufferSize];

        for(int i = 0; i < vertexList.size(); i++){
            array[i*2] = vertexList.get(i).x;
            array[i*2+1] = vertexList.get(i).y;
        }

        vertexBuffer.put(array);
        vertexBuffer.rewind();
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);

        // Enable vertex array.
        Gdx.gl11.glEnableClientState(org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY);

        // Select clear color for the screen.
        Gdx.gl11.glClearColor(.3f, .3f, .3f, 1f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        update();
        draw();
    }

    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.x += 20.0f * deltaTime;
        this.y += 20.0f * deltaTime;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            angle += 10 * deltaTime;
            if (angle > 60){
                angle = 60;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            angle -= 10 * deltaTime;
            if (angle < -60){
                angle = -60;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z)){

        }
    }

    private void draw() {
        Gdx.gl11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(org.lwjgl.opengl.GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity();
        Gdx.glu.gluOrtho2D(Gdx.gl10, 0, width, 0, height);
        Gdx.gl11.glColor4f(1f, 1f, 1f, 1f);

        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(width/2, 0, 0);
        Gdx.gl11.glRotatef(angle,0,0,1);
        drawObject(cannon);
        Gdx.gl11.glPopMatrix();
    }

    private void drawObject(ObjectReference or){
        Gdx.gl11.glDrawArrays(or.openGLPrimitiveType, or.firstIndex, or.vertexCount);
    }

    private ObjectReference createBox(float width, float height, Vector<Point2D> vertexList){
        ObjectReference or = new ObjectReference(vertexList.size(), 4, GL11.GL_TRIANGLE_STRIP);

        vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(-width/2.0f, height/2.0f));
        vertexList.add(new Point2D(width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(width/2.0f, height/2.0f));

        return or;
    }

    private ObjectReference createCircle(float radius, int sides, Vector<Point2D> vertexList){
        ObjectReference or = new ObjectReference(vertexList.size(), sides + 2, GL11.GL_TRIANGLE_FAN);

        float step = (MathUtils.PI *2) / sides;
        float angle = 0;

        vertexList.add(new Point2D(0.0f, 0.0f));
        for(int i = 0; i <= sides + 1; i++){
            vertexList.add(new Point2D(MathUtils.sin(angle) * radius, MathUtils.cos(angle) * radius));
            angle += step;
        }

        return or;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
