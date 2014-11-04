package is.ru.tgra.cube.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import is.ru.tgra.cube.helpers.ColorRGB;
import is.ru.tgra.cube.helpers.CubeLogger;
import is.ru.tgra.cube.helpers.Point3D;
import is.ru.tgra.cube.helpers.Vector3D;

import java.nio.FloatBuffer;

/**
 * Created by Johannes Gunnar Heidarsson on 11.10.2014.
 */
public class GroundCube{
    private static FloatBuffer vertexBuffer;
    private static FloatBuffer texCoordBuffer;
    private static float size;
    private static float radius;
    private static ColorRGB diffuse;
    private static ColorRGB specular;
    private static float shininess;
    private static ColorRGB emission;
    private static Texture texture;
    private static String textureFile = "assets/textures/obsidian.png";
    private Point3D position;
    private boolean moving;
    private Point3D startPosition;
    private Point3D endPosition;
    private Vector3D movement;
    private boolean cornerCube;

    public GroundCube(Point3D position){
        setPosition(position);
    }

    public GroundCube(Point3D startPosition, Point3D endPosition, Vector3D movement){
        this.moving = true;
        this.position = new Point3D(startPosition);
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.movement = movement;
    }

    public boolean isCornerCube() {
        return cornerCube;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Vector3D getMovement(){
        return this.movement;
    }

    public static float getSize() {
        return size;
    }

    public static void setSize(float size) {
        GroundCube.size = size;
        setRadius(size / 2);
    }

    public static float getRadius() {
        return radius;
    }

    public static void setRadius(float radius) {
        GroundCube.radius = radius;
    }

    public static ColorRGB getDiffuse() {
        return diffuse;
    }

    public static void setDiffuse(ColorRGB diffuse) {
        GroundCube.diffuse = diffuse;
    }

    public static ColorRGB getSpecular() {
        return specular;
    }

    public static void setSpecular(ColorRGB specular) {
        GroundCube.specular = specular;
    }

    public static float getShininess() {
        return shininess;
    }

    public static void setShininess(float shininess) {
        GroundCube.shininess = shininess;
    }

    public static ColorRGB getEmission() {
        return emission;
    }

    public static void setEmission(ColorRGB emission) {
        GroundCube.emission = emission;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static void setTexture(Texture texture) {
        GroundCube.texture = texture;
    }

    public boolean isMoving() {
        return moving;
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

        setTexture(new Texture(Gdx.files.internal(textureFile)));

        texCoordBuffer = BufferUtils.newFloatBuffer(48);
        texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
        texCoordBuffer.rewind();
    }

    public static String getTextureFile() {
        return textureFile;
    }

    public static void setTextureFile(String textureFile) {
        GroundCube.textureFile = textureFile;
    }

    public void updateMovement(float deltaTime){
        if(position.lessThan(startPosition) || position.greaterThan(endPosition)){
            movement.reverse();
        }
        position.add(Vector3D.scale(movement, deltaTime));
    }

    public void draw()
    {
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);

        Gdx.gl11.glTranslatef(position.x, position.y, position.z);
        Gdx.gl11.glScalef(getSize(), getSize(), getSize());

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

    public boolean collides(Shape shape){
        float halfSide = (1f * GroundCube.getSize())/2;
        float xNear = position.x - halfSide;
        float zNear = position.z - halfSide;
        float yNear = position.y - halfSide;
        float xFar = position.x + halfSide;
        float zFar = position.z + halfSide;
        float yFar = position.y + halfSide;


        if(shape.getPosition().x + shape.getRadius() >= xNear && shape.getPosition().x - shape.getRadius() <= xFar){
            if(shape.getPosition().z + shape.getRadius() >= zNear && shape.getPosition().z - shape.getRadius() <= zFar){
                if(shape.getPosition().y + shape.getRadius() >= yNear && shape.getPosition().y - shape.getRadius() <= yFar) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collidesTop(Shape shape){
        float top = position.y + radius;
        float shapeBottom = shape.getPosition().y - getRadius();

        if(shapeBottom <= top && shapeBottom > top - 1f){
            return true;
        }
        return false;
    }
}
