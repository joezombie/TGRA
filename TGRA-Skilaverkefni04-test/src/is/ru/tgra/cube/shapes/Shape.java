package is.ru.tgra.cube.shapes;

import is.ru.tgra.cube.helpers.ColorRGB;
import is.ru.tgra.cube.helpers.Point3D;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public interface Shape {
    public void setSize(float size);
    public float getSize();
    public void setPosition(Point3D point);
    public Point3D getPosition();
    public void setColor(ColorRGB color);
    public ColorRGB getColor();
    public void draw();
    public float getRadius();
    public void setRadius(float radius);
    public boolean collides(Shape shape);
    public void setDiffuse(ColorRGB diffuse);
    public ColorRGB getDiffuse();
    public void setSpecular(ColorRGB specular);
    public ColorRGB getSpecular();
    public void setShininess(float shininess);
    public float getShininess();
    public void setEmission(ColorRGB emission);
    public ColorRGB getEmission();
}
