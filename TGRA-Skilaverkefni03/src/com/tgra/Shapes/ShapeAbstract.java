package com.tgra.Shapes;

import com.tgra.ColorRGB;
import com.tgra.Point3D;
import com.tgra.Shapes.Shape;

/**
 * Created by Johannes Gunnar Heidarsson on 1.10.2014.
 */
public abstract class ShapeAbstract implements Shape {
    public Point3D position;
    public float size;
    public ColorRGB color;

    @Override
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void setPosition(Point3D position) {
        this.position = position;
    }

    @Override
    public void setColor(ColorRGB color) {
        this.color = color;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    @Override
    public ColorRGB getColor() {
        return color;
    }
}
