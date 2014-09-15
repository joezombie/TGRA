package is.ru.tgra.assignment02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */
public abstract class ObjectReference {
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;
    public float x;
    public float y;

    public void draw(FloatBuffer floatBuffer){
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, floatBuffer);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(x, y, 0);
        Gdx.gl11.glDrawArrays(openGLPrimitiveType, firstIndex, vertexCount);
        Gdx.gl11.glPopMatrix();
    }
}
