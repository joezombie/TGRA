package is.ru.tgra.cube.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import is.ru.tgra.cube.helpers.ColorRGB;
import is.ru.tgra.cube.helpers.Point3D;


import java.nio.FloatBuffer;

/**
 * Created by Johannes Gunnar Heidarsson on 28.9.2014.
 */
public class Wall extends ShapeAbstract {
    private static FloatBuffer vertexBuffer;
    private boolean southWall;

    public Wall(Point3D position, float size, boolean southWall, ColorRGB color){
        setPosition(position);
        setSize(size);
        setColor(color);
        this.southWall = southWall;
    }

    public static void loadVertices()
    {
        vertexBuffer = BufferUtils.newFloatBuffer(72);
        vertexBuffer.put(new float[] {
                -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,

                0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

                0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,

                -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,

                -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,

                -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});
        vertexBuffer.rewind();
    }

    @Override
    public void draw()
    {
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

        Gdx.gl11.glTranslatef(position.x, position.y, position.z);
        if(southWall){
            Gdx.gl11.glScalef(size, 2.0f, 0.05f);
        }else {
            Gdx.gl11.glScalef(0.05f, 2.0f, size);
        }


        float[] materialDiffuse = {color.r, color.g, color.b, 1.0f};
        Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

        Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
        Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
        Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
        Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
        Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
        Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);

        Gdx.gl11.glPopMatrix();
    }

    @Override
    public boolean collides(Shape shape){
        float halfSide = (1f * size)/2;
        float xNear;
        float zNear;
        float xFar;
        float zFar;


        if(southWall){
            xNear = position.x - halfSide;
            zNear = position.z - 0.05f;
            xFar = position.x + halfSide;
            zFar = position.z + - 0.05f;
        }else {
            xNear = position.x - - 0.05f;
            zNear = position.z - halfSide;
            xFar = position.x + - 0.05f;
            zFar = position.z + halfSide;
        }


        if(shape.getPosition().x + shape.getRadius() > xNear && shape.getPosition().x - shape.getRadius() < xFar){
            if(shape.getPosition().z + shape.getRadius() > zNear && shape.getPosition().z - shape.getRadius() < zFar){
                return true;
            }
        }
        return false;
    }
}