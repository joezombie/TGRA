package is.ru.tgra.cube.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import is.ru.tgra.cube.helpers.ColorRGB;
import is.ru.tgra.cube.helpers.Point3D;
import is.ru.tgra.cube.helpers.Vector3D;

import java.nio.FloatBuffer;

/**
 * Created by Johannes Gunnar Heidarsson on 9.10.2014.
 */
public class Guy extends ShapeAbstract {
    private static FloatBuffer vertexBuffer;
    private Vector3D jump;
    private boolean jumping = false;
    private boolean falling = false;
    private float jumpEndY;

    public Guy(Point3D position, float size, ColorRGB color){
        setPosition(position);
        setSize(size);
        setColor(color);
        setJump(new Vector3D(0, 3.0f, 0));
    }

    public Vector3D getJump() {
        return jump;
    }

    public void setJump(Vector3D jump) {
        this.jump = jump;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public float getJumpEndY() {
        return jumpEndY;
    }

    public void setJumpHeight(float height) {
        this.jumpEndY = position.y + height;
    }

    public boolean isJumping(){
        return jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public static void loadVertices()
    {
        vertexBuffer = BufferUtils.newFloatBuffer(72);
        vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
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
        Gdx.gl11.glScalef(size, size, size);

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
        float xNear = position.x - halfSide;
        float zNear = position.z - halfSide;
        float xFar = position.x + halfSide;
        float zFar = position.z + halfSide;

        if(shape.getPosition().x + shape.getRadius() > xNear && shape.getPosition().x - shape.getRadius() < xFar){
            if(shape.getPosition().z + shape.getRadius() > zNear && shape.getPosition().z - shape.getRadius() < zFar){
                return true;
            }
        }
        return false;
    }
}
