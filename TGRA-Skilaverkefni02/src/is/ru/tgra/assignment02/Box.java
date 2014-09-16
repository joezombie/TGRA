package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Johannes Gunnar Heidarsson on 15.9.2014.
 */
public class Box{
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;
    public Point2D point;
    public float width;
    public float height;
    public float halfHeight;
    public float halfWidth;

    public List<Line2D> sides;

    public Box(float width, float height, Point2D point, Vector<Point2D> vertexList){
        this.sides = new ArrayList<Line2D>();

        this.halfHeight = height/2;
        this.halfWidth = width/2;
        this.firstIndex = vertexList.size();
        this.vertexCount = 4;
        this.openGLPrimitiveType = GL11.GL_TRIANGLE_STRIP;
        this.point = point;
        this.height = height;
        this.width = width;

        this.sides.add(new Line2D(new Point2D(point.x - halfWidth, point.y - halfHeight),
                                  new Point2D(point.x - halfWidth, point.y + halfHeight)));

        this.sides.add(new Line2D(new Point2D(point.x - halfWidth, point.y + halfHeight),
                                  new Point2D(point.x + halfWidth, point.y + halfHeight)));

        this.sides.add(new Line2D(new Point2D(point.x + halfWidth, point.y + halfHeight),
                                  new Point2D(point.x + halfWidth, point.y - halfHeight)));

        this.sides.add(new Line2D(new Point2D(point.x + halfWidth, point.y - halfHeight),
                                  new Point2D(point.x - halfWidth, point.y - halfHeight)));

        vertexList.add(new Point2D(-width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(-width/2.0f, height/2.0f));
        vertexList.add(new Point2D(width/2.0f, -height/2.0f));
        vertexList.add(new Point2D(width/2.0f, height/2.0f));
    }

    public void draw(FloatBuffer floatBuffer){
        Gdx.gl11.glColor4f(0.5f, 0.1f, 0.8f, 1f);
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, floatBuffer);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(point.x, point.y, 0);
        Gdx.gl11.glDrawArrays(openGLPrimitiveType, firstIndex, vertexCount);
        Gdx.gl11.glPopMatrix();
    }
}
