package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.MathUtils;

import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by joezombie on 16.9.2014.
 */
public class CannonBallNew {
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;
    public Point2D point;
    public Vector2D motion;
    public boolean visible = false;
    public float radius;

    public CannonBallNew(float radius, int sides, Point2D point, Vector<Point2D> vertexList){
        this.firstIndex = vertexList.size();
        this.vertexCount = sides + 2;
        this.openGLPrimitiveType = GL11.GL_TRIANGLE_FAN;
        this.point = point;
        this.motion = new Vector2D(0,0);
        this.radius = radius;

        float step = (MathUtils.PI * 2) / sides;
        float angle = 0;

        vertexList.add(new Point2D(0.0f, 0.0f));
        for (int i = 0; i <= sides + 1; i++) {
            vertexList.add(new Point2D(MathUtils.sin(angle) * radius, MathUtils.cos(angle) * radius));
            angle += step;
        }

    }

    public void update(float deltaTime){
        this.point.x += this.motion.x * deltaTime;
        this.point.y += this.motion.y * deltaTime;
    }

    public void draw(FloatBuffer floatBuffer){
        if(visible) {
            Gdx.gl11.glColor4f(1f, 0f, 0f, 1f);
            Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, floatBuffer);
            Gdx.gl11.glPushMatrix();
            Gdx.gl11.glTranslatef(point.x, point.y, 0);
            Gdx.gl11.glDrawArrays(openGLPrimitiveType, firstIndex, vertexCount);
            Gdx.gl11.glPopMatrix();
        }
    }

}
