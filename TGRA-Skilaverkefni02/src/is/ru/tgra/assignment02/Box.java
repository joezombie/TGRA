package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by Johannes Gunnar Heidarsson on 15.9.2014.
 */
public class Box{
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;
    public float x;
    public float y;
    public float red = 1f;
    public float green = 1f;
    public float blue = 1f;
    public float alpha = 1f;

    public Box(float width, float height, Vector<Point2D> vertexList, float x, float y){
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

    public void draw(FloatBuffer floatBuffer){
        Gdx.gl11.glColor4f(1f, 1f, 1f, 1f);
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, floatBuffer);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(x, y, 0);
        Gdx.gl11.glDrawArrays(openGLPrimitiveType, firstIndex, vertexCount);
        Gdx.gl11.glPopMatrix();
    }

    public void setColor(float red, float green, float blue, float alpha){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
}
