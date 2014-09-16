package is.ru.tgra.assignment02;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

    int outOfBounds = 0;
    int inToGoal = 0;

    boolean drawingLine = false;
    Point2D linePointA;

    Cannon cannon;
    CannonBallNew cannonBall;
    Goal goal;

    List<Line> lines;
    FloatBuffer vertexBuffer;
    List<Box> boxes;
    FloatBuffer vertexBufferBox;
    Vector<Point2D> vertexListBox;

    @Override
    public void create() {
        this.lines = new ArrayList<Line>();
        lines.add(new Line(new Point2D(0, 40), new Point2D(width/2 - 80, 40)));
        lines.add(new Line(new Point2D(width/2 + 80, 40), new Point2D(width, 40)));
        //lines.add(new Line(new Point2D(50, height - 50), new Point2D(width - 50, height - 50)));

        this.boxes = new ArrayList<Box>();
        this.vertexListBox = new Vector<Point2D>();
        this.vertexBufferBox = getVertexBuffer(vertexListBox);

        Vector<Point2D> vertexList = new Vector<Point2D>();
        this.cannon = new Cannon(35, 150, new Point2D(width/2, 0) ,vertexList);
        this.cannonBall = new CannonBallNew(15, 64, new Point2D(200, 200) , vertexList);
        this.goal = new Goal(80, 256, new Point2D(100, 150), vertexList);

        this.vertexBuffer = getVertexBuffer(vertexList);

        // Enable vertex array.
        Gdx.gl11.glEnableClientState(org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY);

        // Select clear color for the screen.
        Gdx.gl11.glClearColor(.3f, .3f, .3f, 1f);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int i) {
                return false;
            }

            @Override
            public boolean keyUp(int i) {
                return false;
            }

            @Override
            public boolean keyTyped(char c) {
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                onMouseDown(x, y, button);
                return false;
            }

            @Override
            public boolean touchUp(int i, int i2, int i3, int i4) {
                return false;
            }

            @Override
            public boolean touchDragged(int i, int i2, int i3) {
                return false;
            }

            @Override
            public boolean mouseMoved(int i, int i2) {
                return false;
            }

            @Override
            public boolean scrolled(int i) {
                return false;
            }
        });
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

            for (Box box : boxes){
                for (Line2D line : box.sides){
                    collide(cannonBall,line, deltaTime);
                }
            }

            cannonBall.update(deltaTime);



            float distanceToGoal = (float) Math.sqrt(Math.pow(cannonBall.point.x - goal.point.x, 2) + Math.pow(cannonBall.point.y - goal.point.y, 2));

            if(distanceToGoal <= goal.radius - cannonBall.radius ){
                cannonBall.visible = false;
                this.inToGoal += 1;
            }

            if((cannonBall.point.x > width || cannonBall.point.x < 0 || cannonBall.point.y > height || cannonBall.point.y < 0)){
                cannonBall.visible = false;
                this.outOfBounds += 1;
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
                //float cannonBallAngle = (MathUtils.PI / 180) * (cannon.angle + 90);
                this.cannonBall.motion.x = 300.0f * MathUtils.cosDeg(cannon.angle + 90);
                this.cannonBall.motion.y = 300.0f * MathUtils.sinDeg(cannon.angle + 90);

            }
        }
    }

    private void draw() {
        Gdx.gl11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(org.lwjgl.opengl.GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity();
        Gdx.glu.gluOrtho2D(Gdx.gl11, 0, width, 0, height);

        goal.draw(vertexBuffer);
        cannonBall.draw(vertexBuffer);
        cannon.draw(vertexBuffer);


        for(Line line : lines){
            line.draw();
        }

        for(Box box : boxes){
            box.draw(vertexBufferBox);
        }
    }

    private void onMouseDown(int x, int y, int button){
        if(button == Input.Buttons.LEFT){
            if(!cannonBall.visible) {
                lines.add(new Line(new Point2D(x-40, height - y), new Point2D(x+40, height - y)));
                /*
                if (drawingLine) {
                    lines.add(new Line(new Point2D(linePointA), new Point2D(x, height - y)));
                    this.drawingLine = false;
                } else {
                    this.linePointA = new Point2D(x, height - y);
                    this.drawingLine = true;
                }
                */
            }
        } else if(button == Input.Buttons.RIGHT){
            boxes.add(new Box(100, 100, new Point2D(x, height - y), vertexListBox));
            this.vertexBufferBox = getVertexBuffer(vertexListBox);
        }
    }

    private void collide(CannonBallNew cannonBall, Line line, float deltaTime){
        Vector2D n = new Vector2D();
        n.x = -(line.pointB.y - line.pointA.y);
        n.y = line.pointB.x - line.pointA.x;

        float tHit = n.x * (line.pointA.x - cannonBall.point.x) + n.y * (line.pointA.y - cannonBall.point.y)
                     / (n.x * cannonBall.motion.x + n.y * cannonBall.motion.y);

        if(tHit <= deltaTime && tHit > 0){
            Point2D pHit = new Point2D(0,0);
            pHit.x = cannonBall.point.x + cannonBall.motion.x * tHit;
            pHit.y = cannonBall.point.y + cannonBall.motion.y * tHit;


            if(pHit.x >= line.pointA.x && pHit.x <= line.pointB.x || pHit.x >= line.pointB.x && pHit.x <= line.pointA.x){
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

    private void collide(CannonBallNew cannonBall, Box box){
        if(cannonBall.point.x < box.point.x + box.width/2 && cannonBall.point.x > box.point.x - box.width/2){
            if(cannonBall.point.y < box.point.y + box.height/2 && cannonBall.point.y > box.point.y - box.width/2){
                cannonBall.motion.x = -cannonBall.motion.x;
                cannonBall.motion.y = -cannonBall.motion.y;
            }
        }
    }

    private void collide(CannonBallNew cannonBall, Line2D line, float deltaTime){
        Vector2D n = new Vector2D();
        n.x = -(line.pointB.y - line.pointA.y);
        n.y = line.pointB.x - line.pointA.x;

        float tHit = n.x * (line.pointA.x - cannonBall.point.x) + n.y * (line.pointA.y - cannonBall.point.y)
                / (n.x * cannonBall.motion.x + n.y * cannonBall.motion.y);

        if(tHit <= deltaTime && tHit > 0){
            Point2D pHit = new Point2D(0,0);
            pHit.x = cannonBall.point.x + cannonBall.motion.x * tHit;
            pHit.y = cannonBall.point.y + cannonBall.motion.y * tHit;


            if(pHit.x >= line.pointA.x && pHit.x <= line.pointB.x || pHit.x >= line.pointB.x && pHit.x <= line.pointA.x){
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

    private FloatBuffer getVertexBuffer(Vector<Point2D> vertexList){
        int floatBufferSize = vertexList.size() * 2;
        FloatBuffer result = BufferUtils.newFloatBuffer(floatBufferSize);

        float[] array = new float[floatBufferSize];

        for(int i = 0; i < vertexList.size(); i++){
            array[i*2] = vertexList.get(i).x;
            array[i*2+1] = vertexList.get(i).y;
        }

        result.put(array);
        result.rewind();

        return result;
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
