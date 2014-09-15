package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */
public class Cannon extends ObjectReference{
    public float angle;
    public Cannon(float width, float height, Vector<Point2D> vertexList, float x, float y){
        this.firstIndex = vertexList.size();
        this.vertexCount = 4;
        this.openGLPrimitiveType = GL11.GL_TRIANGLE_STRIP;
        this.x = x;
        this.y = y;

        vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(-width/2.0f, height/2.0f));
        vertexList.add(new Point2D(width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(width/2.0f, height/2.0f));
    }

    @Override
    public void draw(FloatBuffer floatBuffer){
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, floatBuffer);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(x, y, 0);
        Gdx.gl11.glRotatef(angle, 0, 0, 1);
        Gdx.gl11.glDrawArrays(openGLPrimitiveType, firstIndex, vertexCount);
        Gdx.gl11.glPopMatrix();
    }
}
