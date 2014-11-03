package is.ru.tgra.cube.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
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
    private static FloatBuffer texCoordBuffer;
    private Texture texture;
    private String textureFile = "assets/textures/guy.png";
    private Vector3D jump;
    private boolean dying;
    private boolean dyingDone;
    private float dieStartTime;
    private float dieEndTime;
    private float originalSize;
    private boolean jumping = false;
    private float jumpStartTime;
    private float jumpEndTime;
    private boolean falling = false;
    private float fallStartTime;
    private float fallAccelerationEndTime;
    private Vector3D direction;
    private Point3D lastPosition;
    private float speed;

    public Guy(Point3D position, float size, ColorRGB color){
        setPosition(position);
        setLastPosition(position);
        setSize(size);
        setDiffuse(color);
        setJump(new Vector3D(0, 3.0f, 0));
        setDirection(new Vector3D(1.0f, 0, 0));
        setSpeed(3.0f);
        texture = new Texture(Gdx.files.internal(textureFile));
    }

    public void moveLeft(float deltaTime){
        setLastPosition(position);
        position.add(new Vector3D(
                -(getDirection().x * getSpeed()) * deltaTime,
                -(getDirection().y * getSpeed()) * deltaTime,
                -(getDirection().z * getSpeed()) * deltaTime ));
    }

    public void moveRight(float deltaTime){
        setLastPosition(position);
        position.add(new Vector3D(
                getDirection().x * getSpeed() * deltaTime,
                getDirection().y * getSpeed() * deltaTime,
                getDirection().z * getSpeed() * deltaTime ));
    }

    public void moveBack(){
        position = new Point3D(lastPosition);
    }

    public void setOriginalSize(float originalSize) {
        this.originalSize = originalSize;
    }

    public void setJumpStartTime(float jumpStartTime) {
        this.jumpStartTime = jumpStartTime;
    }

    public Vector3D getJump() {
        return jump;
    }

    public Point3D getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Point3D lastPosition) {
        this.lastPosition = new Point3D(lastPosition);
    }

    public void setJump(Vector3D jump) {
        this.jump = jump;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
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

    public void startJump(float startTime){
        this.jumping = true;
        this.jumpStartTime = startTime;
        this.jumpEndTime = startTime + 0.5f;
    }

    public void updateJump(float runTime){
        if(runTime < jumpEndTime) {
            float t = 1 - (runTime - jumpStartTime) / (jumpEndTime - jumpStartTime);
            position.y += t * 0.3f;
        }
        else {
            this.jumping = false;
        }
        /*
        float jumpTime = runTime - jumpStartTime;
        if(jumpTime >= 0.5f ){
            jumping = false;
        } else {
            position.add(new Vector3D(0, 4.0f * deltaTime, 0));
        }
        */
    }

    public void startFall(float runTime){
        if(!falling) {
            this.falling = true;
            this.fallStartTime = runTime;
            this.fallAccelerationEndTime = runTime + 3f;
        }
    }

    public void stopFall(){
        this.falling = false;
    }

    public void updateFall(float runTime, float deltaTime){
        //position.y -= 15.0f * deltaTime;
        if(runTime < fallAccelerationEndTime) {
            float t = (runTime - fallStartTime) / (fallAccelerationEndTime - fallStartTime);
            position.y -= t * 0.7f;
        }
        else {
            position.y -= 1.0f * deltaTime;
        }
    }

    public void startDying(float runTime){
        this.originalSize = this.size;
        this.dying = true;
        this.dyingDone = false;
        this.dieStartTime = runTime;
        this.dieEndTime = runTime + 1.0f;
    }

    public void updateDying(float runTime){
        if(runTime < dieEndTime){
            float t = 1 - (runTime - dieStartTime) / (dieEndTime - dieStartTime);
            this.size = this.size * t;
        } else {
            this.dyingDone = true;
        }
    }

    public void stopDying(){
        this.dying = false;
        this.size = originalSize;
    }

    public boolean isDyingDone() {
        return dyingDone;
    }

    public float getDieEndTime() {
        return dieEndTime;
    }

    public boolean isDying() {
        return dying;
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

        texCoordBuffer = BufferUtils.newFloatBuffer(48);
        texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
        texCoordBuffer.rewind();
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getJumpStartTime() {
        return jumpStartTime;
    }

    public float getJumpEndTime() {
        return jumpEndTime;
    }

    public float getFallStartTime() {
        return fallStartTime;
    }

    public float getFallAccelerationEndTime() {
        return fallAccelerationEndTime;
    }

    @Override
    public void draw()
    {
        Gdx.gl11.glPushMatrix();

        Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

        Gdx.gl11.glTranslatef(position.x, position.y, position.z);
        Gdx.gl11.glScalef(size, size, size);

        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        texture.bind();  //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

        //float[] materialDiffuse = {color.r, color.g, color.b, 1.0f};
        //Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

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

        Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

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
