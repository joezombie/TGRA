package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by Johannes Gunnar Heidarsson on 15.9.2014.
 */
public class Line {
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;
    public float x;
    public float y;
    public float red = 1f;
    public float green = 1f;
    public float blue = 1f;
    public float alpha = 1f;
    public float xx;
    public float yy;

    private FloatBuffer vertexBuffer;

    public Line(float x, float y, float xx, float yy){
        Vector<Point2D> vertexList = new Vector<Point2D>();
        this.firstIndex = vertexList.size();
        this.vertexCount = 2;
        this.openGLPrimitiveType = GL11.GL_LINES;
        this.x = x;
        this.y = y;
        this.xx = xx;
        this.yy = yy;

        vertexList.add(new Point2D(x, y));
        vertexList.add(new Point2D(xx, yy));

        int floatBufferSize = vertexList.size() * 2;
        vertexBuffer = BufferUtils.newFloatBuffer(floatBufferSize);

        float[] array = new float[floatBufferSize];

        for(int i = 0; i < vertexList.size(); i++){
            array[i*2] = vertexList.get(i).x;
            array[i*2+1] = vertexList.get(i).y;
        }

        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public void draw(){
        Gdx.gl11.glColor4f(.2f, .2f, 1f, 1f);
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, vertexBuffer);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(x, y, 0);
        Gdx.gl11.glLineWidth(20);
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
