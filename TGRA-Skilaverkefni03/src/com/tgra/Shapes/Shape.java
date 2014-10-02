package com.tgra.Shapes;

import com.tgra.ColorRGB;
import com.tgra.Point3D;

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
}
