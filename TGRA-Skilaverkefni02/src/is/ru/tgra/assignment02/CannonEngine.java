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
    CannonBallNew cannonBall;
    List<Line> lines;
    FloatBuffer vertexBuffer;

    @Override
    public void create() {
        this.lines = new ArrayList<Line>();
        //lines.add(new Line(100, 200, 400, 200));
        lines.add(new Line(new Point2D(100, 300), new Point2D(700, 300)));

        Vector<Point2D> vertexList = new Vector<Point2D>();

        this.cannon = new Cannon(35, 150, new Point2D(width/2, 0) ,vertexList);
        this.cannonBall = new CannonBallNew(15, 64, new Point2D(200, 200) , vertexList);

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

        if(cannonBall.visible){


            for (Line line: lines){
                collide(cannonBall, line, deltaTime);
            }

            cannonBall.update(deltaTime);



            if((cannonBall.point.x > width || cannonBall.point.x < 0 || cannonBall.point.y > height || cannonBall.point.y < 0)){
                cannonBall.visible = false;
                //lines.clear();
            }

        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                cannon.angle += 50 * deltaTime;
                if (cannon.angle > 60) {
                    cannon.angle = 60;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                cannon.angle -= 50 * deltaTime;
                if (cannon.angle < -60) {
                    cannon.angle = -60;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                cannonBall.point = new Point2D(cannon.point);
                cannonBall.visible = true;
                float cannonBallAngle = (MathUtils.PI / 180) * (cannon.angle + 90);
                this.cannonBall.motion.x = 300.0f * MathUtils.cos(cannonBallAngle);
                this.cannonBall.motion.y = 300.0f * MathUtils.sin(cannonBallAngle);

            }

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                //lines.add(new Line(Gdx.input.getX(), Gdx.input.getY(), 0, 0));
            }
        }
    }

    private void draw() {
        Gdx.gl11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(org.lwjgl.opengl.GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity();
        Gdx.glu.gluOrtho2D(Gdx.gl11, 0, width, 0, height);

        cannonBall.draw(vertexBuffer);
        cannon.draw(vertexBuffer);


        for(Line line: lines){
            line.draw();
        }
    }

    private void collide(CannonBallNew cannonBall, Line line, float deltaTime){
        Vector2D n = new Vector2D();
        n.x = -(line.pointB.y-line.pointA.y);
        n.y = line.pointB.x - line.pointA.x;

        float tHit = n.x * (line.pointA.x - cannonBall.point.x) + n.y * (line.pointA.y - cannonBall.point.y)
                     / (n.x * cannonBall.motion.x + n.y * cannonBall.motion.y);

        if(tHit <= deltaTime && tHit > 0){
            Point2D pHit = new Point2D(0,0);
            pHit.x = cannonBall.point.x + cannonBall.motion.x * tHit;
            pHit.y = cannonBall.point.y + cannonBall.motion.y * tHit;

            if((pHit.x >= line.pointA.x && pHit.x <= line.pointB.x) || (pHit.x >= line.pointB.x && pHit.x <= line.pointA.x)){
                Vector2D reflectedMotion = new Vector2D();

                float lengthOfN = (float) Math.sqrt(n.x * n.x + n.y * n.y);
                n.x = n.x / lengthOfN;
                n.y = n.y / lengthOfN;

                float aDotN = cannonBall.motion.x * n.x + cannonBall.motion.y * n.y;

                reflectedMotion.x = cannonBall.motion.x - 2 * aDotN * n.x;
                reflectedMotion.y = cannonBall.motion.y - 2 * aDotN * n.y;

                cannonBall.motion = reflectedMotion;
            }
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
