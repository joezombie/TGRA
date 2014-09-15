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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CannonEngine implements ApplicationListener{
    int width = 800;
    int height = 600;

    Cannon cannon;
    CannonBall cannonBall;
    List<Line> lines;
    FloatBuffer vertexBuffer;

    @Override
    public void create() {
        this.lines = new ArrayList<Line>();
        lines.add(new Line(100, 100, 200, 200));

        Vector<Point2D> vertexList = new Vector<Point2D>();

        this.cannon = new Cannon(35, 150, vertexList, width/2, 0);
        this.cannonBall = new CannonBall(15, 64, vertexList, 200, 200);

        int floatBufferSize = vertexList.size() * 2;
        vertexBuffer = BufferUtils.newFloatBuffer(floatBufferSize);

        float[] array = new float[floatBufferSize];

        for(int i = 0; i < vertexList.size(); i++){
            array[i*2] = vertexList.get(i).x;
            array[i*2+1] = vertexList.get(i).y;
        }

        vertexBuffer.put(array);
        vertexBuffer.rewind();

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

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            cannon.angle += 10 * deltaTime;
            if (cannon.angle > 60){
                cannon.angle = 60;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            cannon.angle -= 10 * deltaTime;
            if (cannon.angle < -60){
                cannon.angle = -60;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            cannonBall.visible = true;
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            lines.add(new Line(Gdx.input.getX(), Gdx.input.getY(), 0, 0));
        }
    }

    private void draw() {
        Gdx.gl11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(org.lwjgl.opengl.GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity();
        Gdx.glu.gluOrtho2D(Gdx.gl10, 0, width, 0, height);
        Gdx.gl11.glColor4f(1f, 1f, 1f, 1f);

        cannon.draw(vertexBuffer);
        cannonBall.draw(vertexBuffer);

        for(Line line: lines){
            line.draw();
        }
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
